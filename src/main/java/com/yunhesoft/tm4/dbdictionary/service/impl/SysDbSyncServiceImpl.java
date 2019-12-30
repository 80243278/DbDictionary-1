package com.yunhesoft.tm4.dbdictionary.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.yunhesoft.tm4.dbdictionary.entity.domain.ColumnDo;
import com.yunhesoft.tm4.dbdictionary.entity.domain.TableDo;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictColumnDto;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictTableDto;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysModuleVo;
import com.yunhesoft.tm4.dbdictionary.repository.IMssqlUtils;
import com.yunhesoft.tm4.dbdictionary.service.ISysDbSyncService;
import com.yunhesoft.tm4.dbdictionary.service.ISysDictColumnService;
import com.yunhesoft.tm4.dbdictionary.service.ISysDictTableService;
import com.yunhesoft.tm4.dbdictionary.utils.ToolUtils;

/**
 * @author zhang.jt
 */
@Service
@Repository
public class SysDbSyncServiceImpl implements ISysDbSyncService {
	@Autowired
	private IMssqlUtils mssqlUtils;
	@Autowired
	private ISysDictColumnService colService;
	@Autowired
	private ISysDictTableService tableService;

	/**
	 * 同步字典数据到数据库
	 * @return
	 */
	@Override
	public boolean syncDictToDb(SysDictTableDto tableDto, List<SysDictColumnDto> colDtoList) {
		boolean flag = true;

		// 获取数据库中的表名
		Map<String, TableDo> tableMap = mssqlUtils.getAllTables();

		// 获取数据表的字段
		Map<String, ColumnDo> colMap = mssqlUtils.getTableColumns(tableDto.getTableName());

		TableDo tbDo = tableMap.get(tableDto.getTableName());
		// 新建表
		if (tbDo == null) {
			TableDo tbDoNew = new TableDo();
			List<ColumnDo> colDoNewList = new ArrayList<ColumnDo>();
			BeanUtils.copyProperties(tableDto, tbDoNew);
			tbDoNew.setId(tableDto.getTmuid());
			tbDoNew.setTableName(tableDto.getTableName());

			for (SysDictColumnDto colDto : colDtoList) {
				ColumnDo colNewDo = new ColumnDo();
				BeanUtils.copyProperties(colDto, colNewDo);
				colDoNewList.add(colNewDo);
			}
			mssqlUtils.createTable(tbDoNew, colDoNewList);
		}
		// 修改表
		else {
			List<ColumnDo> colDoNewList = new ArrayList<ColumnDo>();
			List<ColumnDo> colDoAlterList = new ArrayList<ColumnDo>();

			for (SysDictColumnDto colDto : colDtoList) {
				ColumnDo colDo = colMap.get(colDto.getColumnName());
				// 新字段
				if (colDo == null) {
					ColumnDo colNewDo = new ColumnDo();
					BeanUtils.copyProperties(colDto, colNewDo);
					colDoNewList.add(colNewDo);
				}
				// 修改字段
				else {
					ColumnDo colAlterDo = new ColumnDo();
					BeanUtils.copyProperties(colDo, colAlterDo);
					BeanUtils.copyProperties(colDto, colAlterDo);
					colDoAlterList.add(colAlterDo);
				}
			}

			mssqlUtils.alterTable(tbDo, colDoNewList, colDoAlterList);
		}

		return flag;
	}

	/**
	 * 同步数据库结构到字典
	 * @return
	 */
	@Override
	public boolean syncDbToDict(SysDictTableDto tableDto) {
		boolean flag = true;

		// 获取数据表的字段
		Map<String, ColumnDo> colMap = mssqlUtils.getTableColumns(tableDto.getTableName());
		List<String> colNameList = new ArrayList<String>(colMap.keySet());
		List<SysDictColumnDto> addColDtoList = new ArrayList<SysDictColumnDto>();
		int sort = 1;
		for (String colName : colNameList) {
			ColumnDo colDo = colMap.get(colName);
			SysDictColumnDto colDto = new SysDictColumnDto();
			BeanUtils.copyProperties(colDo, colDto);
			colDto.setSort(sort);
			boolean primaryKey = false;
			if (colDo.getPkName() != null && !"".equals(colDo.getPkName())) {
				primaryKey = true;
			}
			colDto.setPrimaryKey(primaryKey);
			colDto.setColumnShowName(colDto.getColumnName());
			colDto.setTableId(tableDto.getTmuid());
			colDto.setUsed(true);
			colDto.setDataType(colDo.getDataType());
			addColDtoList.add(colDto);
			sort++;
		}
		// 老数据
		List<SysDictColumnDto> delColDtoList = colService.getSysDictColumnByTableId(tableDto.getTmuid());
		// 保存列数据
		flag = colService.saveSysDictColumn(addColDtoList, delColDtoList, null);

		return flag;
	}

	/**
	 * 获取待同步数据库表数据
	 * @return
	 */
	@Override
	public List<SysDictTableDto> getSyncTables() {
		List<SysDictTableDto> list = new ArrayList<SysDictTableDto>();

		// 获取数据库中的表名
		Map<String, TableDo> tableMap = mssqlUtils.getAllTables();
		List<String> tbMapKeyList = new ArrayList<String>(tableMap.keySet());
		if (tbMapKeyList.size() > 0) {
			// 获取字典表数据
			Map<String, SysDictTableDto> dictTbMap = tableService.getSysDictTableNameMap();

			for (String tableName : tbMapKeyList) {
				TableDo tbDo = tableMap.get(tableName);
				SysDictTableDto tbDto = new SysDictTableDto();
				BeanUtils.copyProperties(tbDo, tbDto);
				tbDto.setTableShowName(tbDto.getTableName());

				SysDictTableDto dictTbDto = dictTbMap.get(tbDto.getTableName());
				if (dictTbDto != null) {
					BeanUtils.copyProperties(dictTbDto, tbDto);
					tbDto.setExisted(true);
				} else {
					tbDto.setExisted(false);
				}
				list.add(tbDto);
			}
		}

		return list;
	}

	/**
	 * 反向同步指定数据库表（数据库 → 字典）
	 * @param module
	 * @param tableDtoList
	 * @return
	 */
	@Override
	public boolean syncDictByTables(SysModuleVo module, List<SysDictTableDto> tableDtoList) {
		boolean flag = true;

		if (tableDtoList == null) {
			return false;
		}

		int tableSort = 1;
		for (SysDictTableDto tableDto : tableDtoList) {
			// 获取旧表数据
			SysDictTableDto oldTableDto = null;
			List<SysDictTableDto> oldTableDtoList = tableService.getSysDictTableByName(tableDto.getTableName());
			if (oldTableDtoList != null && oldTableDtoList.size() > 0) {
				oldTableDto = oldTableDtoList.get(0);
			}
			// 获取旧表字段数据
			List<SysDictColumnDto> oldColDtoList = null;
			if (oldTableDto != null) {
				oldColDtoList = colService.getSysDictColumnByTableId(oldTableDto.getTmuid());
			}
			Map<String, SysDictColumnDto> oldColMap = new LinkedHashMap<String, SysDictColumnDto>();
			if (oldColDtoList != null) {
				for (SysDictColumnDto colDto : oldColDtoList) {
					oldColMap.put(colDto.getColumnName(), colDto);
				}
			}

			// 制作新表数据
			SysDictTableDto newTableDto = new SysDictTableDto();
			newTableDto.setDbConnId(module.getDbConnId());
			newTableDto.setModuleCode(module.getModuleCode());
			newTableDto.setModuleId(module.getTmuid());
			newTableDto.setSort(tableSort);
			newTableDto.setTableName(tableDto.getTableName());
			newTableDto.setTableShowName(tableDto.getTableName());
			newTableDto.setUsed(true);
			newTableDto.setTmuid(ToolUtils.getUuid());
			// 使用旧表数据覆盖，保留原有备注信息
			newTableDto.setRemark(oldTableDto.getRemark());

			List<SysDictTableDto> addTableDtoList = new ArrayList<SysDictTableDto>();
			addTableDtoList.add(newTableDto);
			flag = tableService.saveSysDictTableColumn(addTableDtoList, oldTableDtoList, null);
			tableSort++;

			// 通过底层SQL语句反射获取数据表字段
			Map<String, ColumnDo> colMap = mssqlUtils.getTableColumns(tableDto.getTableName());
			List<String> colNameList = new ArrayList<String>(colMap.keySet());
			List<SysDictColumnDto> addColDtoList = new ArrayList<SysDictColumnDto>();
			int sort = 1;
			for (String colName : colNameList) {
				ColumnDo colDo = colMap.get(colName);
				SysDictColumnDto colDto = new SysDictColumnDto();
				BeanUtils.copyProperties(colDo, colDto);
				colDto.setSort(sort);
				boolean primaryKey = false;
				if (colDo.getPkName() != null && !"".equals(colDo.getPkName())) {
					primaryKey = true;
				}
				colDto.setPrimaryKey(primaryKey);
				colDto.setColumnShowName(colDto.getColumnName());
				colDto.setTableId(newTableDto.getTmuid());
				colDto.setUsed(true);
				colDto.setDataType(colDo.getDataType());
				// 使用旧字段数据覆盖，保留原有备注信息
				SysDictColumnDto oldCol = oldColMap.get(colDto.getColumnName());
				if (oldCol != null) {
					colDto.setRemark(oldCol.getRemark());
				}

				addColDtoList.add(colDto);
				sort++;
			}
			// 保存表字段数据
			flag = colService.saveSysDictColumn(addColDtoList, oldColDtoList, null);
		}

		return flag;
	}
}

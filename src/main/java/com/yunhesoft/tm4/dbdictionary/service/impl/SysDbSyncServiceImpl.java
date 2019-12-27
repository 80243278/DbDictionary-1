package com.yunhesoft.tm4.dbdictionary.service.impl;

import java.util.ArrayList;
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
import com.yunhesoft.tm4.dbdictionary.repository.IMssqlUtils;
import com.yunhesoft.tm4.dbdictionary.service.ISysDbSyncService;
import com.yunhesoft.tm4.dbdictionary.service.ISysDictColumnService;
import com.yunhesoft.tm4.dbdictionary.service.ISysDictTableService;

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
}

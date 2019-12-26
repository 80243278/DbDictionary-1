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

/**
 * @author zhang.jt
 */
@Service
@Repository
public class SysDbSyncServiceImpl implements ISysDbSyncService {
	/*@Autowired
	ApplicationContext applicationContext;*/

	@Autowired
	IMssqlUtils mssqlUtils;

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
			tbDoNew.setId(tableDto.getTmuid());
			tbDoNew.setTableName(tableDto.getTableName());

			for (SysDictColumnDto colDto : colDtoList) {
				ColumnDo colNewDo = new ColumnDo();
				BeanUtils.copyProperties(colDto, colNewDo);
				colNewDo.setIsKey(colDto.getPrimaryKey());
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
					colNewDo.setIsKey(colDto.getPrimaryKey());
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

		return flag;
	}
}

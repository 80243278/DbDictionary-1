package com.yunhesoft.tm4.dbdictionary.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yunhesoft.tm4.dbdictionary.entity.domain.ColumnDo;
import com.yunhesoft.tm4.dbdictionary.entity.domain.TableDo;

/**
 * mssql底层操作接口类
 * @author zhang.jt
 */
@Repository
public interface IMssqlUtils {
	/**
	 * 得到数据库中的表,不包括列
	 * @return
	 */
	public Map<String, TableDo> getAllTables();

	/**
	 * 获取指定表的所有字段
	 * @param tableName
	 * @return
	 */
	public Map<String, ColumnDo> getTableColumns(String tableName);

	/**
	 * 创建表
	 * @param tbDoNew
	 * @param colDoNewList
	 * @return
	 */
	public boolean createTable(TableDo tbDoNew, List<ColumnDo> colDoNewList);

	/**
	 * 修改表
	 * @param tbDo
	 * @param colDoNewList
	 * @param colDoAlterList
	 * @return
	 */
	public boolean alterTable(TableDo tbDo, List<ColumnDo> colDoNewList, List<ColumnDo> colDoAlterList);
}

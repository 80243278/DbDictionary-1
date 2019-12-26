package com.yunhesoft.tm4.dbdictionary.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.yunhesoft.tm4.dbdictionary.entity.domain.ColumnDo;
import com.yunhesoft.tm4.dbdictionary.entity.domain.TableDo;
import com.yunhesoft.tm4.dbdictionary.repository.IMssqlUtils;

/**
 * mssql底层操作类
 * @author zhang.jt
 */
@Repository
public class MssqlUtilsImpl implements IMssqlUtils {
	@Autowired
	ApplicationContext applicationContext;

	/**
	 * 获取当前库中所有表名
	 * @throws SQLException
	 */
	@Override
	public Map<String, TableDo> getAllTables() {
		Map<String, TableDo> tables = new HashMap<String, TableDo>(10);

		try {
			DataSource dataSource = applicationContext.getBean(DataSource.class);
			String sql = "SELECT id,name FROM sysobjects where xtype='U'";
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				tables.put(name, new TableDo(id, name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tables;
	}

	/**
	 * 获取指定表的所有字段
	 * @throws SQLException
	 */
	@Override
	public Map<String, ColumnDo> getTableColumns(String tableName) {
		Map<String, ColumnDo> cols = new HashMap<String, ColumnDo>(10);

		try {
			String sql = "";
			sql += " select a.name as tableName, b.name as colName,b.max_length as size,b.precision,b.scale,b.is_nullable isnull,c.value as remark,d.name keyName,d.type_desc keyDesc,d.is_primary_key isKey";
			sql += " from sys.tables a left join sys.columns b on a.object_id=b.object_id  ";
			sql += " left join sys.extended_properties c on a.object_id=c.major_id  and b.column_id=c.minor_id  ";
			sql += " left join sys.indexes d on a.object_id=d.object_id and d.is_primary_key = 1 and d.index_id=b.column_id";
			sql += " where a.name='" + tableName + "'";
			sql += " order by column_id";

			DataSource dataSource = applicationContext.getBean(DataSource.class);
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ColumnDo colDo = new ColumnDo();
				colDo.setColumnName(rs.getString("colName"));
				colDo.setTableName(rs.getString("tableName"));
				colDo.setSize(rs.getInt("size"));
				colDo.setScale(rs.getInt("scale"));
				try {
					boolean isnull = rs.getBoolean("isnull");
					colDo.setNotNull(!isnull);
				} catch (Exception e) {
					colDo.setNotNull(true);
				}
				colDo.setRemark(rs.getString("remark"));
				colDo.setKeyName(rs.getString("keyName"));
				colDo.setKeyDesc(rs.getString("keyDesc"));
				colDo.setIsKey(rs.getBoolean("isKey"));

				cols.put(colDo.getColumnName(), colDo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cols;
	}

	/**
	 * 创建表
	 * @param tbDoNew
	 * @param colDoNewList
	 * @return
	 */
	@Override
	public boolean createTable(TableDo tbDoNew, List<ColumnDo> colDoNewList) {
		boolean flag = true;

		try {
			List<ColumnDo> keyList = new ArrayList<ColumnDo>();

			String sql = "";

			// 创建表
			sql += "CREATE TABLE " + tbDoNew.getTableName() + " (";

			int i = 0;
			for (ColumnDo colBean : colDoNewList) {
				if (i > 0) {
					sql += ",";
				}
				sql += " " + colBean.getColumnName() + " " + getColumnType(colBean) + " " + getIfNull(colBean);
				// 缓存关键列
				if (colBean.getPrimaryKey().booleanValue()) {
					keyList.add(colBean);
				}

				i++;
			}

			/* [tmuid] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
			 [db_dialect] varchar(200) COLLATE Chinese_PRC_CI_AS  NOT NULL,
			 [db_name] varchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
			 [db_pass_word] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
			 [db_show_name] varchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
			 [db_url] varchar(200) COLLATE Chinese_PRC_CI_AS  NULL,
			 [db_user_name] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
			 [remark] varchar(4000) COLLATE Chinese_PRC_CI_AS  NULL,
			 [sort] int  NULL,
			 [used] bit  NULL*/
			sql += " )";

			DataSource dataSource = applicationContext.getBean(DataSource.class);
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			flag = ps.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 修改表
	 * @param tbDo
	 * @param colDoNewList
	 * @param colDoAlterList
	 * @return
	 */
	@Override
	public boolean alterTable(TableDo tbDo, List<ColumnDo> colDoNewList, List<ColumnDo> colDoAlterList) {
		boolean flag = true;

		return flag;
	}

	/**
	 * 获取字段类型串
	 * @param colBean
	 * @return
	 */
	private String getColumnType(ColumnDo colBean) {
		String typeStr = "";
		String varcharStr = "varchar";
		String decimalStr = "decimal";

		// varchar
		if (varcharStr.equals(colBean.getDataType().toLowerCase())) {
			typeStr += varcharStr + "(" + colBean.getSize() + ")";
		}
		// decimal
		else if (decimalStr.equals(colBean.getDataType().toLowerCase())) {
			typeStr += decimalStr + "(" + colBean.getSize() + ")";
		}
		// other
		else {
			typeStr = colBean.getDataType().toLowerCase();
		}

		return typeStr;
	}

	/**
	 * 获取是否可以为空
	 * @param colBean
	 * @return
	 */
	private String getIfNull(ColumnDo colBean) {
		String ifnull = "";

		if (colBean.getNotNull()) {
			ifnull = "not null";
		} else {
			ifnull = "null";
		}

		return ifnull;
	}

}

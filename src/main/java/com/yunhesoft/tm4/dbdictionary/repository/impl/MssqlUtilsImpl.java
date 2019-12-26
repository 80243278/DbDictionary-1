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
			sql += " select a.name as tableName, b.name as colName,b.max_length as size,b.precision,b.scale,b.is_nullable isnull,c.value as remark,d.constraint_name pkName";
			sql += " from sys.tables a left join sys.columns b on a.object_id=b.object_id ";
			sql += " left join sys.extended_properties c on a.object_id=c.major_id  and b.column_id=c.minor_id";
			sql += " left join information_schema.key_column_usage d on a.name=d.table_name and d.column_name=b.name";
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
				colDo.setPkName(rs.getString("pkName"));

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
			DataSource dataSource = applicationContext.getBean(DataSource.class);
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = null;

			String sql = "";

			// 创建表
			sql += "CREATE TABLE " + tbDoNew.getTableName() + " (";

			int i = 0;
			for (ColumnDo colBean : colDoNewList) {
				if (i > 0) {
					sql += ",";
				}
				sql += colBean.getColumnName() + " " + getColumnType(colBean) + " " + getIfNull(colBean);
				// 缓存关键列
				if (colBean.getPrimaryKey().booleanValue()) {
					keyList.add(colBean);
				}

				i++;
			}
			sql += " )";

			ps = conn.prepareStatement(sql);
			flag = ps.execute(sql);
			if (flag == false) {
				return flag;
			}

			// 添加字段说明
			sql = "";
			for (ColumnDo colBean : colDoNewList) {
				sql += "exec sys.sp_addextendedproperty @name=N'MS_Description', @value=N''" + colBean.getRemark()
						+ "', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'"
						+ tbDoNew.getTableName() + "', @level2type=N'COLUMN',@level2name=N'" + colBean.getColumnName()
						+ "';";
			}
			ps = conn.prepareStatement(sql);
			flag = ps.execute(sql);
			if (flag == false) {
				return flag;
			}

			// 添加主键
			sql = "";
			sql += "alter table " + tbDoNew.getTableName() + " [add constraint PK_" + tbDoNew.getTableName()
					+ "] primary key(";
			int j = 0;
			for (ColumnDo colBean : keyList) {
				if (j > 0) {
					sql += ",";
				}
				sql += colBean.getColumnName();
				j++;
			}
			sql += ")";
			ps = conn.prepareStatement(sql);
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

		try {
			DataSource dataSource = applicationContext.getBean(DataSource.class);
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = null;

			String sql = "";

			// 新增字段
			sql = "";
			for (ColumnDo colBean : colDoNewList) {
				sql += "alter table " + tbDo.getTableName() + " add " + colBean.getColumnName() + " "
						+ getColumnType(colBean) + " " + getIfNull(colBean) + ";";
			}
			ps = conn.prepareStatement(sql);
			flag = ps.execute(sql);

			// 修改字段类型
			sql = "";
			for (ColumnDo colBean : colDoNewList) {
				// 修改有风险，单个执行
				try {
					sql = "alter table " + tbDo.getTableName() + " alter column " + colBean.getColumnName() + " "
							+ getColumnType(colBean) + " " + getIfNull(colBean) + ";";
					ps = conn.prepareStatement(sql);
					flag = ps.execute(sql);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// 检查并修改主键

		} catch (Exception e) {
			e.printStackTrace();
		}

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

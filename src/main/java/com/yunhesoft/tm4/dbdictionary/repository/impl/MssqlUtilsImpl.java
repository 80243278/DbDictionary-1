package com.yunhesoft.tm4.dbdictionary.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
		Map<String, TableDo> tables = new LinkedHashMap<String, TableDo>(10);

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
		Map<String, ColumnDo> cols = new LinkedHashMap<String, ColumnDo>(10);

		try {
			String sql = "";
			sql += " select a.name as tableName, b.name as colName,b.max_length as size,b.precision,b.scale,b.is_nullable isnull,";
			sql += "c.value as remark,d.constraint_name pkName,e.data_type dataType";
			sql += " from sys.tables a left join sys.columns b on a.object_id=b.object_id ";
			sql += " left join sys.extended_properties c on a.object_id=c.major_id  and b.column_id=c.minor_id";
			sql += " left join information_schema.key_column_usage d on a.name=d.table_name and d.column_name=b.name";
			sql += " left join INFORMATION_SCHEMA.columns e on a.name=e.table_name and b.name=e.column_name";
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
				colDo.setDataType(rs.getString("dataType"));

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
			try {
				ps.execute();
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

			// 添加字段说明
			sql = "";
			for (ColumnDo colBean : colDoNewList) {
				sql += "EXEC sp_addextendedproperty 'MS_Description', N'" + colBean.getRemark()
						+ "', 'SCHEMA', N'dbo', 'TABLE', N'" + tbDoNew.getTableName() + "', 'COLUMN', N'"
						+ colBean.getColumnName() + "';";
			}
			ps = conn.prepareStatement(sql);
			try {
				ps.execute();
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

			// 添加主键
			if (keyList.size() > 0) {
				sql = "";
				sql += "alter table " + tbDoNew.getTableName() + " add constraint PK_" + tbDoNew.getTableName()
						+ " primary key(";
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
				try {
					ps.execute();
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
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
			List<ColumnDo> keyList = new ArrayList<ColumnDo>();
			List<ColumnDo> newKeyList = new ArrayList<ColumnDo>();

			String sql = "";

			// 新增字段
			sql = "";
			for (ColumnDo colBean : colDoNewList) {
				sql += "alter table " + tbDo.getTableName() + " add " + colBean.getColumnName() + " "
						+ getColumnType(colBean) + " " + getIfNull(colBean) + ";";

				// 缓存关键列
				boolean ifOldPk = false;
				if (colBean.getPkName() != null && !"".equals(colBean.getPkName())) {
					ifOldPk = true;
				}
				if (colBean.getPrimaryKey().booleanValue() || ifOldPk == true) {
					keyList.add(colBean);
				}
			}
			ps = conn.prepareStatement(sql);
			try {
				ps.execute();
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

			// 修改字段类型
			sql = "";
			for (ColumnDo colBean : colDoAlterList) {
				// 修改有风险，单个执行
				try {
					sql = "alter table " + tbDo.getTableName() + " alter column " + colBean.getColumnName() + " "
							+ getColumnType(colBean) + " " + getIfNull(colBean) + ";";
					ps = conn.prepareStatement(sql);
					try {
						ps.execute();
						flag = true;
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
					// 缓存关键列
					boolean ifOldPk = false;
					if (colBean.getPkName() != null && !"".equals(colBean.getPkName())) {
						ifOldPk = true;
					}
					if (colBean.getPrimaryKey().booleanValue() || ifOldPk == true) {
						keyList.add(colBean);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// 检查并修改主键
			boolean ifUpdKey = false;
			String pkName = "";
			sql = "";
			for (ColumnDo colBean : keyList) {
				// 原为主键列
				if (colBean.getPkName() != null && !"".equals(colBean.getPkName())) {
					pkName = colBean.getPkName();
					// 已取消主键
					if (colBean.getPrimaryKey() == false) {
						ifUpdKey = true;
					}
				}
				// 原为非主键列
				else {
					// 新主键
					if (colBean.getPrimaryKey() == true) {
						ifUpdKey = true;
						newKeyList.add(colBean);
					}
				}
			}
			// 需要更新主键
			if (ifUpdKey == true) {
				// 删除原主键约束
				sql = "";
				sql += "alter table" + tbDo.getTableName() + "drop constraint" + pkName;
				ps = conn.prepareStatement(sql);
				try {
					ps.execute();
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				// 创建新主键约束
				if (newKeyList.size() > 0) {
					sql = "";
					sql += "alter table " + tbDo.getTableName() + " add constraint PK_" + tbDo.getTableName()
							+ " primary key(";
					int i = 0;
					for (ColumnDo key : newKeyList) {
						if (i > 0) {
							sql += ",";
						}
						sql += key.getColumnName();
						i++;
					}
					sql += ")";
					ps = conn.prepareStatement(sql);
					try {
						ps.execute();
						flag = true;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			// 添加字段说明
			sql = "";
			if (colDoNewList.size() > 0) {
				for (ColumnDo colBean : colDoNewList) {
					sql += "EXEC sp_addextendedproperty 'MS_Description', N'" + colBean.getRemark()
							+ "', 'SCHEMA', N'dbo', 'TABLE', N'" + tbDo.getTableName() + "', 'COLUMN', N'"
							+ colBean.getColumnName() + "';";

				}
				ps = conn.prepareStatement(sql);
				try {
					ps.execute();
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// 修改字段说明
			sql = "";
			if (colDoNewList.size() > 0) {
				for (ColumnDo colBean : colDoNewList) {
					sql += "EXEC sp_updateextendedproperty 'MS_Description', N'" + colBean.getRemark()
							+ "', 'SCHEMA', N'dbo', 'TABLE', N'" + tbDo.getTableName() + "', 'COLUMN', N'"
							+ colBean.getColumnName() + "';";
				}
			}
			ps = conn.prepareStatement(sql);
			try {
				ps.execute();
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
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

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
import com.yunhesoft.tm4.dbdictionary.repository.ISqlUtils;

/**
 * mssql底层操作类
 * @author zhang.jt
 */
@Repository("MsSqlUtilsImpl")
public class MsSqlUtilsImpl implements ISqlUtils {
	@Autowired
	ApplicationContext applicationContext;

	/**
	 * 获取当前连接的数据库名称
	 * @return
	 */
	@Override
	public String getCurrentDbName() {
		String dbName = "";

		try {
			DataSource dataSource = applicationContext.getBean(DataSource.class);
			String sql = "Select Name dbName From Master..SysDataBases Where DbId=(Select Dbid From Master..SysProcesses Where Spid = @@spid)";
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dbName = rs.getString("dbName");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dbName;
	}

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

			try {
				ps = conn.prepareStatement(sql);
				ps.execute();
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

			// 添加字段说明
			// 添加字段说明
			flag = addColDesc(conn, tbDoNew, colDoNewList);

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
				try {
					ps = conn.prepareStatement(sql);
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
	public boolean alterTable(TableDo tbDo, List<ColumnDo> colDoNewList, List<ColumnDo> colDoAlterList,
			List<ColumnDo> colDoDelList) {
		boolean flag = true;

		try {
			DataSource dataSource = applicationContext.getBean(DataSource.class);
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = null;
			List<ColumnDo> keyList = new ArrayList<ColumnDo>();
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
			try {
				ps = conn.prepareStatement(sql);
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
					try {
						ps = conn.prepareStatement(sql);
						ps.execute();
						flag = true;
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
					// 缓存关键列
					boolean ifKey = (colBean.getPrimaryKey() != null && colBean.getPrimaryKey().booleanValue())
							|| (colBean.getPkName() != null && !"".equals(colBean.getPkName()));
					if (ifKey) {
						keyList.add(colBean);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 检查并更新主键
			flag = updatePk(conn, tbDo, keyList, colDoDelList);
			// 删除字段
			flag = delCol(conn, tbDo, colDoDelList);
			// 添加字段说明
			flag = addColDesc(conn, tbDo, colDoNewList);
			// 修改字段说明
			flag = alterColDesc(conn, tbDo, colDoAlterList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 删除字段
	 * @param conn
	 * @param tbDo
	 * @param colDoDelList
	 * @return
	 */
	private boolean delCol(Connection conn, TableDo tbDo, List<ColumnDo> colDoDelList) {
		boolean flag = true;
		PreparedStatement ps = null;

		String sql = "";
		for (ColumnDo colBean : colDoDelList) {
			// 删除可能会失败，单个执行
			try {
				sql = "alter table " + tbDo.getTableName() + " drop column " + colBean.getColumnName();
				try {
					ps = conn.prepareStatement(sql);
					ps.execute();
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return flag;
	}

	/**
	 * 删除旧主键，添加新主键（更新表主键）
	 * @param conn
	 * @param tbDo
	 * @param keyList
	 * @param colDoDelList
	 * @return
	 */
	private boolean updatePk(Connection conn, TableDo tbDo, List<ColumnDo> keyList, List<ColumnDo> colDoDelList) {
		boolean flag = true;
		PreparedStatement ps = null;
		boolean ifUpdKey = false;
		String pkName = "";
		String sql = "";
		List<ColumnDo> newKeyList = new ArrayList<ColumnDo>();

		// 检查删除字段列表，如果有主键，需要删除主键依赖
		if (colDoDelList != null && colDoDelList.size() > 0) {
			for (ColumnDo colDo : colDoDelList) {
				// 原为主键列
				if (colDo.getPkName() != null && !"".equals(colDo.getPkName())) {
					ifUpdKey = true;
					pkName = colDo.getPkName();
					break;
				}
			}
		}

		for (ColumnDo colBean : keyList) {
			// 原为主键列
			if (colBean.getPkName() != null && !"".equals(colBean.getPkName())) {
				pkName = colBean.getPkName();
				if (colBean.getPrimaryKey() == null || colBean.getPrimaryKey().booleanValue() == false) {
					// 已取消主键
					ifUpdKey = true;
				} else {
					// 主键未变动
					newKeyList.add(colBean);
				}
			}
			// 原为非主键列
			else {
				// 新主键
				if (colBean.getPrimaryKey() != null && colBean.getPrimaryKey().booleanValue() == true) {
					ifUpdKey = true;
					newKeyList.add(colBean);
				}
			}
		}

		if (ifUpdKey == true) {
			// 删除原主键约束
			if (pkName != null && !"".equals(pkName)) {
				sql = "";
				sql += "alter table " + tbDo.getTableName() + " drop constraint " + pkName;
				try {
					ps = conn.prepareStatement(sql);
					ps.execute();
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
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
				try {
					ps = conn.prepareStatement(sql);
					ps.execute();
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return flag;
	}

	/**
	 * 添加字段描述
	 * @param conn
	 * @param tbDo
	 * @param colDoNewList
	 * @return
	 */
	private boolean addColDesc(Connection conn, TableDo tbDo, List<ColumnDo> colDoNewList) {
		boolean flag = true;

		String sql = "";
		if (colDoNewList.size() > 0) {
			for (ColumnDo colBean : colDoNewList) {
				sql += "EXEC sp_addextendedproperty 'MS_Description', N'" + colBean.getRemark()
						+ "', 'SCHEMA', N'dbo', 'TABLE', N'" + tbDo.getTableName() + "', 'COLUMN', N'"
						+ colBean.getColumnName() + "';";

			}
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.execute();
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
		}

		return flag;
	}

	/**
	 * 修改字段描述
	 * @param conn
	 * @param tbDo
	 * @param colDoAlterList
	 * @return
	 */
	private boolean alterColDesc(Connection conn, TableDo tbDo, List<ColumnDo> colDoAlterList) {
		boolean flag = true;

		String sql = "";
		if (colDoAlterList.size() > 0) {
			for (ColumnDo colBean : colDoAlterList) {
				sql += "EXEC sp_updateextendedproperty 'MS_Description', N'" + colBean.getRemark()
						+ "', 'SCHEMA', N'dbo', 'TABLE', N'" + tbDo.getTableName() + "', 'COLUMN', N'"
						+ colBean.getColumnName() + "';";
			}
		}
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			flag = true;
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

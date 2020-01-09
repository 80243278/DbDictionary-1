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
import com.yunhesoft.tm4.dbdictionary.utils.ToolUtils;

/**
 * mysql底层操作类
 * @author zhang.jt
 */
@Repository("MySqlUtilsImpl")
public class MySqlUtilsImpl implements ISqlUtils {
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
			String sql = "select database();";
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// 只有一列，列名：“database()”
				dbName = rs.getString(1);
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
			String sql = " show tables;";
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// 没有id
				String id = ToolUtils.getUuid();
				// 只有一列，动态列名：“Tables in 数据库名”
				String name = rs.getString(1);
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
			sql += " select 'sys_db_conn' tableName,column_name colName,character_maximum_length size,";
			sql += " numeric_precision 'precision',numeric_scale scale,is_nullable isnull,";
			sql += " COLUMN_COMMENT remark,column_key pkName,data_type dataType";
			sql += " from information_schema.columns";
			sql += " where table_name = '" + tableName + "'";
			sql += " order by ORDINAL_POSITION asc;";

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
			sql += "CREATE TABLE `" + tbDoNew.getTableName() + "` (";

			int i = 0;
			for (ColumnDo colBean : colDoNewList) {
				if (i > 0) {
					sql += ",";
				}
				sql += "`" + colBean.getColumnName() + "` " + getColumnType(colBean) + " " + getIfNull(colBean)
						+ " COMMENT '" + colBean.getRemark() + "'";
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

			// 添加主键
			if (keyList.size() > 0) {
				sql = "";
				sql += "ALTER TABLE `" + tbDoNew.getTableName() + "` ADD PRIMARY KEY (";
				int j = 0;
				for (ColumnDo colBean : keyList) {
					if (j > 0) {
						sql += ",";
					}
					sql += "`" + colBean.getColumnName() + "`";
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
				sql += "ALTER TABLE `" + tbDo.getTableName() + "` ADD COLUMN " + colBean.getColumnName() + " "
						+ getColumnType(colBean) + " " + getIfNull(colBean) + " COMMENT '" + colBean.getRemark() + "';";

				// 缓存关键列
				boolean ifOldPk = false;
				if (colBean.getPkName() != null && !"".equals(colBean.getPkName())) {
					ifOldPk = true;
				}
				if (colBean.getPrimaryKey().booleanValue() || ifOldPk == true) {
					keyList.add(colBean);
				}
				try {
					ps = conn.prepareStatement(sql);
					ps.execute();
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
					flag = false;
				}
			}

			// 修改字段类型
			sql = "";
			for (ColumnDo colBean : colDoAlterList) {
				// 修改有风险，单个执行
				try {
					sql = "ALTER TABLE `" + tbDo.getTableName() + "` MODIFY COLUMN `" + colBean.getColumnName() + "` "
							+ getColumnType(colBean) + " " + getIfNull(colBean) + " COMMENT '" + colBean.getRemark()
							+ "';";
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
				sql += "alter table `" + tbDo.getTableName() + "` DROP PRIMARY KEY;";
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
				sql += "ALTER TABLE `" + tbDo.getTableName() + "` ADD PRIMARY KEY (";
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
			Integer scale = colBean.getScale();
			if (scale == null) {
				scale = 0;
			}
			typeStr += decimalStr + "(" + colBean.getSize() + "," + scale.intValue() + ")";
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

package com.yunhesoft.tm4.dbdictionary.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.yunhesoft.tm4.dbdictionary.service.IPropService;

/**
 * @author zhang.jt
 */
@Service
@Repository("PropServiceImpl")
public class PropServiceImpl implements IPropService {
	/**数据库连接驱动名称*/
	@Value("${spring.datasource.driver-class-name}")
	private String datasourceClassName;

	/**
	 * 获取配置中的数据库驱动连接类型
	 * @return
	 */
	@Override
	public String getPropDbType() {
		String dbType = null;

		if (datasourceClassName != null) {
			if ("com.mysql.cj.jdbc.driver".equals(datasourceClassName.toLowerCase())) {
				// mysql
				// System.out.println("正在连接 Mysql");
				dbType = "mysql";
			} else if ("com.microsoft.sqlserver.jdbc.sqlserverdriver".equals(datasourceClassName.toLowerCase())) {
				// mssql
				// System.out.println("正在连接 Mssql");
				dbType = "mssql";
			} else {
				// TODO
				// oracle 预留
			}
		}

		return dbType;
	}
}

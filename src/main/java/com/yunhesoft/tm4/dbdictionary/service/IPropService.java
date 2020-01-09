package com.yunhesoft.tm4.dbdictionary.service;

import org.springframework.stereotype.Service;

/**
 * 配置文件接口类
 * @author zhang.jt
 */
@Service
public interface IPropService {
	/**
	 * 获取配置中的数据库驱动连接类型
	 * @return
	 */
	public String getPropDbType();
}

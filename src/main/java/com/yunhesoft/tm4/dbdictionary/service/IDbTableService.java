package com.yunhesoft.tm4.dbdictionary.service;

import java.util.List;

import com.yunhesoft.tm4.dbdictionary.entity.dto.DbTableTreeDto;

/**
 * @author: Zhang.jt
 * @Date: 2019-12-23 18:11:22
 * @Description:
 */
public interface IDbTableService {
	/**
	 *
	 * 查询树形数据接口
	 * @return  List<DbTableTreeDto>
	 */
	List<DbTableTreeDto> getTreeData();
}

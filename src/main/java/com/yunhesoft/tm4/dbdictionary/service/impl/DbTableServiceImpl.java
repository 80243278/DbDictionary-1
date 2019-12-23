package com.yunhesoft.tm4.dbdictionary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunhesoft.tm4.dbdictionary.entity.dto.DbTableTreeDto;
import com.yunhesoft.tm4.dbdictionary.mapper.SysDbConnInfoMapper;
import com.yunhesoft.tm4.dbdictionary.service.IDbTableService;

import java.util.List;

/**
 * @author: Zhang.jt
 * @Date: 2019-12-23 18:12:01
 * @Description:
 */
@Service
public class DbTableServiceImpl implements IDbTableService {
	@Autowired
	SysDbConnInfoMapper sysDbConnInfoMapper;

	@Override
	public List<DbTableTreeDto> getTreeData() {
		return null;
	}
}

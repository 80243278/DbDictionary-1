package com.yunhesoft.tm4.dbdictionary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDbConnDto;

import io.swagger.annotations.Api;

/**
 * @author zhang.jt
 */
@Service
@Api(tags = "表连接接口类")
public interface ISysDbConnService {
	/**
	 * 获取表连接数据
	 * @return List<SysDbConnDto>
	 */
	public List<SysDbConnDto> getSysDbConn();

	/**
	 * @category 通过ID获取模块数据
	 * @param tmuid
	 * @return
	 */
	public List<SysDbConnDto> getSysDbConnById(String tmuid);

	/**
	 * 保存数据库链接
	 * @param addDtoList
	 * @param delDtoList
	 * @param updDtoList
	 * @return
	 */
	public boolean saveSysDbConn(List<SysDbConnDto> addDtoList, List<SysDbConnDto> delDtoList,
			List<SysDbConnDto> updDtoList);
}

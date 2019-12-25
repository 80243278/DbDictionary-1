package com.yunhesoft.tm4.dbdictionary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yunhesoft.tm4.dbdictionary.entity.dto.SysModuleDto;

import io.swagger.annotations.Api;

/**
 * @author zhang.jt
 */
@Service
@Api(tags = "字典模块接口类")
public interface ISysModuleService {
	/**
	 * 通过ID获取模块数据
	 * @param tmuid
	 * @return List<SysModuleDto>
	 */
	public List<SysModuleDto> getSysModuleById(String tmuid);

	/**
	 * 通过数据库连接ID获取模块数据
	 * @param connId
	 * @return
	 */
	public List<SysModuleDto> getSysModuleByConnId(String connId);

	/**
	 * 添加模块
	 * @param modDto
	 * @return
	 */
	public boolean addSysModule(SysModuleDto modDto);

	/**
	 * 修改模块
	 * @param modDto
	 * @return
	 */
	public boolean updSysModule(SysModuleDto modDto);

	/**
	 * 删除模块
	 * @param modDto
	 * @return
	 */
	public boolean delSysModule(SysModuleDto modDto);
}

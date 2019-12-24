package com.yunhesoft.tm4.dbdictionary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yunhesoft.tm4.dbdictionary.entity.dto.SysModuleDto;

/**
 * @author zhang.jt
 */
@Service
public interface ISysModuleService {
	/**
	 * @category 通过ID获取模块数据
	 * @param tmuid
	 * @return List<SysModuleDto>
	 */
	public List<SysModuleDto> getSysModuleById(String tmuid);

	/**
	 * @category 添加模块
	 * @param modDto
	 * @return
	 */
	public boolean addSysModule(SysModuleDto modDto);

	/**
	 * @category 修改模块
	 * @param modDto
	 * @return
	 */
	public boolean updSysModule(SysModuleDto modDto);

	/**
	 * @category 删除模块
	 * @param modDto
	 * @return
	 */
	public boolean delSysModule(SysModuleDto modDto);
}

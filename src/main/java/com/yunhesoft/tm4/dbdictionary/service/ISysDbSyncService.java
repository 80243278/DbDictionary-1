package com.yunhesoft.tm4.dbdictionary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictColumnDto;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictTableDto;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysModuleVo;

/**
 * 数据库结构同步接口
 * @author zhang.jt
 */
@Service
public interface ISysDbSyncService {
	/**
	 * 获取当前连接的数据库名称
	 * @return
	 */
	public String getCurrentDbName();
	
	/**
	 * 同步字典数据到数据库
	 * @param tableDto
	 * @param colDtoList
	 * @return
	 */
	public boolean syncDictToDb(SysDictTableDto tableDto, List<SysDictColumnDto> colDtoList);

	/**
	 * 同步数据库结构到字典
	 * @param tableDto
	 * @return
	 */
	public boolean syncDbToDict(SysDictTableDto tableDto);

	/**
	 * 获取待同步数据库表数据
	 * @return
	 */
	public List<SysDictTableDto> getSyncTables();

	/**
	 * 反向同步指定数据库表（数据库 → 字典）
	 * @param module
	 * @param tableDtoList
	 * @return
	 */
	public boolean syncDictByTables(SysModuleVo module, List<SysDictTableDto> tableDtoList);
}

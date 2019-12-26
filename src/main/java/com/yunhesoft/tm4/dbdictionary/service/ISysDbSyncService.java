package com.yunhesoft.tm4.dbdictionary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictColumnDto;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictTableDto;

/**
 * 数据库结构同步接口
 * @author zhang.jt
 */
@Service
public interface ISysDbSyncService {
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
}

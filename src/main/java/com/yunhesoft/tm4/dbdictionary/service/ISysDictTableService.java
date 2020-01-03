package com.yunhesoft.tm4.dbdictionary.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictTableDto;

import io.swagger.annotations.Api;

/**
 * @author zhang.jt
 */
@Service
@Api(tags = "字典表接口类")
public interface ISysDictTableService {
	/**
	 * 通过ID获取表数据
	 * @param tmuid
	 * @return List<SysDictTableDto>
	 */
	public List<SysDictTableDto> getSysDictTableById(String tmuid);

	/**
	 * 通过表名获取表数据
	 * @param tableName
	 * @return
	 */
	public List<SysDictTableDto> getSysDictTableByName(String tableName);
	
	
	/**
	 * 通过表名模糊检索获取表数据
	 * @param tableName
	 * @return
	 */
	public List<SysDictTableDto> getSysDictTableByNameFuzzy(String tableName);

	/**
	 * 通过模块id获取数据库表数据
	 * @param moduleId
	 * @return
	 */
	public List<SysDictTableDto> getSysDictTableByModuleId(String moduleId);

	/**
	 * 获取字典表名数据Map
	 * @return
	 */
	public Map<String, SysDictTableDto> getSysDictTableNameMap();

	/**
	 * 添加表
	 * @param tableDto
	 * @return
	 */
	public SysDictTableDto addSysDictTable(SysDictTableDto tableDto);

	/**
	 * 修改表
	 * @param tableDto
	 * @return
	 */
	public boolean updSysDictTable(SysDictTableDto tableDto);

	/**
	 * 删除表
	 * @param tableDto
	 * @return
	 */
	public boolean delSysDictTable(SysDictTableDto tableDto);

	/**
	 * 保存表列表
	 * @param addDtoList
	 * @param delDtoList
	 * @param updDtoList
	 * @return
	 */
	public boolean saveSysDictTableColumn(List<SysDictTableDto> addDtoList, List<SysDictTableDto> delDtoList,
			List<SysDictTableDto> updDtoList);
}

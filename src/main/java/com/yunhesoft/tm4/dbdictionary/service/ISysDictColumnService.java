package com.yunhesoft.tm4.dbdictionary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictColumnDto;

import io.swagger.annotations.Api;

/**
 * @author zhang.jt
 */
@Service
@Api(tags = "字典表字段接口类")
public interface ISysDictColumnService {
	/**
	 * 通过ID获取表字段数据
	 * @param tmuid
	 * @return List<SysDictColumnDto>
	 */
	public List<SysDictColumnDto> getSysDictColumnById(String tmuid);

	/**
	 * 通过表ID获取表字段数据
	 * @param tableId
	 * @return List<SysDictColumnDto>
	 */
	public List<SysDictColumnDto> getSysDictColumnByTableId(String tableId);
	
	/**
	 * 通过表名获取字段数据列表
	 * @param tableName
	 * @return
	 */
	public List<SysDictColumnDto> getSysDictColumnByTableName(String tableName);

	/**
	 * 添加表字段
	 * @param colDto
	 * @return
	 */
	public boolean addSysDictColumn(SysDictColumnDto colDto);

	/**
	 * 修改表字段
	 * @param colDto
	 * @return
	 */
	public boolean updSysDictColumn(SysDictColumnDto colDto);

	/**
	 * 删除表字段
	 * @param colDto
	 * @return
	 */
	public boolean delSysDictColumn(SysDictColumnDto colDto);

	/**
	 * 保存表字段列表
	 * @param addDtoList
	 * @param delDtoList
	 * @param updDtoList
	 * @return
	 */
	public boolean saveSysDictColumn(List<SysDictColumnDto> addDtoList, List<SysDictColumnDto> delDtoList,
			List<SysDictColumnDto> updDtoList);
}

package com.yunhesoft.tm4.dbdictionary.service;

import java.util.List;

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
	 * 添加表
	 * @param tableDto
	 * @return
	 */
	public boolean addSysDictTable(SysDictTableDto tableDto);

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
}

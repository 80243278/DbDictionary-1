package com.yunhesoft.tm4.dbdictionary.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictColumnDto;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictTableDto;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysModuleDto;
import com.yunhesoft.tm4.dbdictionary.entity.vo.ResponseVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysDictColumnVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysDictTableVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysModuleVo;
import com.yunhesoft.tm4.dbdictionary.service.ISysDictColumnService;
import com.yunhesoft.tm4.dbdictionary.service.ISysDictTableService;
import com.yunhesoft.tm4.dbdictionary.service.ISysModuleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhang.jt
 */
@Api(tags = "数据字典控制器")
@Controller
@RequestMapping("/dict")
public class DbDictController {
	@Autowired
	private ISysModuleService moduleService;
	private ISysDictTableService tableService;
	private ISysDictColumnService columnService;

	@ResponseBody
	@RequestMapping(value = "/addModule", method = { RequestMethod.POST })
	@ApiOperation(value = "添加模块")
	@ApiImplicitParam(name = "module", value = "模块对象", required = true, paramType = "body", dataType = "SysModuleVo")
	public ResponseVo addModule(@RequestBody SysModuleVo module) {
		ResponseVo resp = ResponseVo.ok("添加模块成功");
		SysModuleDto dto = new SysModuleDto();
		BeanUtils.copyProperties(module, dto);
		boolean flag = moduleService.addSysModule(dto);
		if (flag == false) {
			resp = ResponseVo.error("添加模块失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/updModule", method = { RequestMethod.POST })
	@ApiOperation(value = "修改模块")
	@ApiImplicitParam(name = "module", value = "模块对象", required = true, paramType = "body", dataType = "SysModuleVo")
	public ResponseVo updModule(@RequestBody SysModuleVo module) {
		ResponseVo resp = ResponseVo.ok("修改模块成功");
		SysModuleDto dto = new SysModuleDto();
		BeanUtils.copyProperties(module, dto);
		boolean flag = moduleService.updSysModule(dto);
		if (flag == false) {
			resp = ResponseVo.error("修改模块失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/delModule", method = { RequestMethod.POST })
	@ApiOperation(value = "删除模块")
	@ApiImplicitParam(name = "module", value = "模块对象", required = true, paramType = "body", dataType = "SysModuleVo")
	public ResponseVo delModule(@RequestBody SysModuleVo module) {
		ResponseVo resp = ResponseVo.ok("删除模块成功");
		SysModuleDto dto = new SysModuleDto();
		BeanUtils.copyProperties(module, dto);
		boolean flag = moduleService.delSysModule(dto);
		if (flag == false) {
			resp = ResponseVo.error("删除模块失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/addTable", method = { RequestMethod.POST })
	@ApiOperation(value = "添加表")
	@ApiImplicitParam(name = "table", value = "表对象", required = true, paramType = "body", dataType = "SysDictTableVo")
	public ResponseVo addTable(@RequestBody SysDictTableVo table) {
		ResponseVo resp = ResponseVo.ok("添加表成功");
		SysDictTableDto dto = new SysDictTableDto();
		BeanUtils.copyProperties(table, dto);
		boolean flag = tableService.addSysDictTable(dto);
		if (flag == false) {
			resp = ResponseVo.error("添加表失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/updTable", method = { RequestMethod.POST })
	@ApiOperation(value = "修改表")
	@ApiImplicitParam(name = "module", value = "表对象", required = true, paramType = "body", dataType = "SysDictTableVo")
	public ResponseVo updTable(@RequestBody SysDictTableVo module) {
		ResponseVo resp = ResponseVo.ok("修改表成功");
		SysDictTableDto dto = new SysDictTableDto();
		BeanUtils.copyProperties(module, dto);
		boolean flag = tableService.updSysDictTable(dto);
		if (flag == false) {
			resp = ResponseVo.error("修改表失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/delTable", method = { RequestMethod.POST })
	@ApiOperation(value = "删除表")
	@ApiImplicitParam(name = "module", value = "表对象", required = true, paramType = "body", dataType = "SysDictTableVo")
	public ResponseVo delTable(@RequestBody SysDictTableVo module) {
		ResponseVo resp = ResponseVo.ok("删除表成功");
		SysDictTableDto dto = new SysDictTableDto();
		BeanUtils.copyProperties(module, dto);
		boolean flag = tableService.delSysDictTable(dto);
		if (flag == false) {
			resp = ResponseVo.error("删除表失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/addCol", method = { RequestMethod.POST })
	@ApiOperation(value = "添加表字段")
	@ApiImplicitParam(name = "table", value = "表字段对象", required = true, paramType = "body", dataType = "SysDictColumnVo")
	public ResponseVo addCol(@RequestBody SysDictColumnVo table) {
		ResponseVo resp = ResponseVo.ok("添加表字段成功");
		SysDictColumnDto dto = new SysDictColumnDto();
		BeanUtils.copyProperties(table, dto);
		boolean flag = columnService.addSysDictColumn(dto);
		if (flag == false) {
			resp = ResponseVo.error("添加表字段失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/updCol", method = { RequestMethod.POST })
	@ApiOperation(value = "修改表字段")
	@ApiImplicitParam(name = "module", value = "表字段对象", required = true, paramType = "body", dataType = "SysDictColumnVo")
	public ResponseVo updCol(@RequestBody SysDictColumnVo module) {
		ResponseVo resp = ResponseVo.ok("修改表字段成功");
		SysDictColumnDto dto = new SysDictColumnDto();
		BeanUtils.copyProperties(module, dto);
		boolean flag = columnService.updSysDictColumn(dto);
		if (flag == false) {
			resp = ResponseVo.error("修改表字段失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/delCol", method = { RequestMethod.POST })
	@ApiOperation(value = "删除表字段")
	@ApiImplicitParam(name = "module", value = "表字段对象", required = true, paramType = "body", dataType = "SysDictColumnVo")
	public ResponseVo delCol(@RequestBody SysDictColumnVo module) {
		ResponseVo resp = ResponseVo.ok("删除表字段成功");
		SysDictColumnDto dto = new SysDictColumnDto();
		BeanUtils.copyProperties(module, dto);
		boolean flag = columnService.delSysDictColumn(dto);
		if (flag == false) {
			resp = ResponseVo.error("删除表字段失败");
		}
		return resp;
	}
}

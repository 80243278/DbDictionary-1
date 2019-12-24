package com.yunhesoft.tm4.dbdictionary.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunhesoft.tm4.dbdictionary.entity.dto.SysModuleDto;
import com.yunhesoft.tm4.dbdictionary.entity.vo.ResponseVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysModuleVo;
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

	@ResponseBody
	@RequestMapping(value = "/addModule", method = { RequestMethod.POST })
	@ApiOperation(value = "添加模块")
	@ApiImplicitParam(name = "module", value = "模块对象", required = true, paramType = "body", dataType = "SysModuleVo")
	public ResponseVo addModule(@RequestBody SysModuleVo module) {
		ResponseVo resp = ResponseVo.ok("添加模块成功");
		SysModuleDto modDto = new SysModuleDto();
		BeanUtils.copyProperties(module, modDto);
		boolean flag = moduleService.addSysModule(modDto);
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
		SysModuleDto modDto = new SysModuleDto();
		BeanUtils.copyProperties(module, modDto);
		boolean flag = moduleService.updSysModule(modDto);
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
		SysModuleDto modDto = new SysModuleDto();
		BeanUtils.copyProperties(module, modDto);
		boolean flag = moduleService.delSysModule(modDto);
		if (flag == false) {
			resp = ResponseVo.error("删除模块失败");
		}
		return resp;
	}
}

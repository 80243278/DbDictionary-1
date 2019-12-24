package com.yunhesoft.tm4.dbdictionary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunhesoft.tm4.dbdictionary.entity.vo.SysModuleVo;

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
	@ResponseBody
	@RequestMapping(value = "/addModule", method = { RequestMethod.POST })
	@ApiOperation(value = "添加模块")
	@ApiImplicitParam(name = "module", value = "模块对象", required = true, paramType = "body", dataType = "SysModuleVo")
	public SysModuleVo test(@RequestBody SysModuleVo module) {
		SysModuleVo rst = new SysModuleVo();
		// TODO
		return rst;
	}
}

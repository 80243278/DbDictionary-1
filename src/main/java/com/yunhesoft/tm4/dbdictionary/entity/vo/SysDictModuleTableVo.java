package com.yunhesoft.tm4.dbdictionary.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang.jt
 */
@Getter
@Setter
public class SysDictModuleTableVo {
	@ApiModelProperty(value = "数据库模块")
	SysModuleVo moduleVo;
	@ApiModelProperty(value = "数据库表列表")
	List<SysDictTableVo> tableVoList;
}

package com.yunhesoft.tm4.dbdictionary.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据库表字段字典表POJO
 * @author zhang.jt
 */
@Getter
@Setter
@ApiModel(description = "[应答结果] 数据库表相关数据")
public class SysDictDbModuleTableVo {
	@ApiModelProperty(value = "表英文名(表中文名)")
	String value;
	@ApiModelProperty(value = "数据库连接")
	SysDictColumnVo dbVo;
	@ApiModelProperty(value = "数据库模块")
	SysModuleVo moduleVo;
	@ApiModelProperty(value = "数据库表")
	SysDictTableVo tableVo;
}

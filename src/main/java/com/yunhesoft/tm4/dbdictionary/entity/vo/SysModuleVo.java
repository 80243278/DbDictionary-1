package com.yunhesoft.tm4.dbdictionary.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang.jt
 */
@Getter
@Setter
@ApiModel(description = "[请求参数] 模块")
public class SysModuleVo {
	@ApiModelProperty(value = "唯一标识")
	String tmuid;
	@ApiModelProperty(value = "数据库连接id")
	String dbConnId;
	@ApiModelProperty(value = "模块id，不可重复")
	String moduleCode;
	@ApiModelProperty(value = "模块名称")
	String moduleName;
	@ApiModelProperty(value = "模块类型")
	String moduleType;
	@ApiModelProperty(value = "是否使用 1:使用 0:不使用")
	Boolean used;
	@ApiModelProperty(value = "序号")
	Integer sort;
	@ApiModelProperty(value = "备注")
	String remark;
}

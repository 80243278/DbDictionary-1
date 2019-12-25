package com.yunhesoft.tm4.dbdictionary.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据库表字典表POJO
 * @author zhang.jt
 */
@Getter
@Setter
@ApiModel(value = "[请求参数] SysDictTableVo", description = "数据库表")
public class SysDictTableVo {
	@ApiModelProperty(value = "唯一标识")
	private String tmuid;
	@ApiModelProperty(value = "模块记录的tmuid")
	private String moduleId;
	@ApiModelProperty(value = "真实表名")
	private String tableName;
	@ApiModelProperty(value = "显示表名")
	private String tableShowName;
	@ApiModelProperty(value = "模块编码")
	private String moduleCode;
	@ApiModelProperty(value = "数据库连接id")
	private String dbConnId;
	@ApiModelProperty(value = "是否使用 1:使用 0:不使用")
	private Boolean used;
	@ApiModelProperty(value = "序号")
	private Integer sort;
	@ApiModelProperty(value = "备注")
	private String remark;
}

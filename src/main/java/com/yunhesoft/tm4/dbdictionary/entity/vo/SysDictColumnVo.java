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
@ApiModel(description = "[请求参数] 数据库表字段")
public class SysDictColumnVo {
	@ApiModelProperty(value = "唯一标识")
	private String tmuid;
	@ApiModelProperty(value = "真实列名")
	private String columnName;
	@ApiModelProperty(value = "显示列名")
	private String columnShowName;
	@ApiModelProperty(value = "数据库表字典表id")
	private String tableId;
	@ApiModelProperty(value = "数据库表别名")
	private String tableName;
	@ApiModelProperty(value = "数据类型")
	private String dataType;
	@ApiModelProperty(value = "是否为非空字段 1:非空 0:可以为空")
	private Boolean notNull;
	@ApiModelProperty(value = "是否为主键 1:主键 0:非主键")
	private Boolean primaryKey;
	@ApiModelProperty(value = "是否使用 1:使用 0:不使用")
	private Boolean used;
	@ApiModelProperty(value = "序号")
	private Integer sort;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "字段长度")
	private Integer columnLength;
	@ApiModelProperty(value = "小数精度")
	private Integer columnDecimalPlace;
	@ApiModelProperty(value = "操作标识（1:添加，0:修改，-1:删除）")
	private Integer flag;
}

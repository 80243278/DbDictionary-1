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
@ApiModel(description = "[应答结果] 树形节点")
public class SysTreeNodeVo {
	@ApiModelProperty(value = "节点名称")
	String label;
	@ApiModelProperty(value = "节点类型(db:数据库/module:模块/table:表/column:字段)")
	String type;
	@ApiModelProperty(value = "节点id")
	String nodeId;
	@ApiModelProperty(value = "备用字段")
	String dataId;
	@ApiModelProperty(value = "true:叶子节点/false:分类节点")
	Boolean isLeaf;
	@ApiModelProperty(value = "数据库原字段信息")
	Object obj;
}

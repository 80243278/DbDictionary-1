package com.yunhesoft.tm4.dbdictionary.entity.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang.jt
 */
@Getter
@Setter
public class SysTreeNodeVo {
	String label;
	String type;
	String nodeId;
	String dataId;
	Boolean isLeaf;
	/**数据库原字段信息*/
	Object obj;
}

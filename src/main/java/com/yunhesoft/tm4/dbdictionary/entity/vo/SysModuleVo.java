package com.yunhesoft.tm4.dbdictionary.entity.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang.jt
 */
@Getter
@Setter
public class SysModuleVo {
	String tmuid;
	String moduleCode;
	String moduleName;
	String moduleType;
	Boolean used;
	Integer sort;
	String remark;
}

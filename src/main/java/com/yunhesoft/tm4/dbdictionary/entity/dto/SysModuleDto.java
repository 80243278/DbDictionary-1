package com.yunhesoft.tm4.dbdictionary.entity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang.jt
 */
@Getter
@Setter
public class SysModuleDto {
	String tmuid;
	String dbConnId;
	String moduleCode;
	String moduleName;
	String moduleType;
	Boolean used;
	Integer sort;
	String remark;
}

package com.yunhesoft.tm4.dbdictionary.entity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: zhang.jt
 */
@Getter
@Setter
public class SysDbConnDto {
	private String tmuid;
	private String dbName;
	private String dbShowName;
	private String dbUrl;
	private String dbUserName;
	private String dbPassWord;
	private String dbDialect;
	private Boolean used;
	private Integer sort;
	private String remark;
}

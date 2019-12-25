package com.yunhesoft.tm4.dbdictionary.entity.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据库表字典表POJO
 * @author zhang.jt
 */
@Getter
@Setter
public class SysDictTableVo {
	private String tmuid;
	private String tableName;
	private String tableShowName;
	private String moduleCode;
	private String dbConnId;
	private Boolean used;
	private Integer sort;
	private String remark;
}

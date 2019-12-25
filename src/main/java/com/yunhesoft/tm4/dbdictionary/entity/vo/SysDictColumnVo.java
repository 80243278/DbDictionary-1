package com.yunhesoft.tm4.dbdictionary.entity.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据库表字段字典表POJO
 * @author zhang.jt
 */
@Getter
@Setter
public class SysDictColumnVo {
	private String tmuid;
	private String columnName;
	private String columnShowName;
	private String tableId;
	private String dataType;
	private Boolean notNull;
	private Boolean primaryKey;
	private Boolean used;
	private Integer sort;
	private String remark;
}

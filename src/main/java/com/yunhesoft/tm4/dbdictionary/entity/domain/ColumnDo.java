package com.yunhesoft.tm4.dbdictionary.entity.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang.jt
 */
@Getter
@Setter
public class ColumnDo {
	private String tableName;
	private String columnName;
	private String columnShowName;
	private String tableId;
	private String dataType;
	private Boolean notNull;
	private Boolean primaryKey;
	private Boolean used;
	private Integer sort;
	private String remark;
	private Integer size;
	private Integer scale;
	private String keyName;
	private String keyDesc;
	private Boolean isKey;
}

package com.yunhesoft.tm4.dbdictionary.entity.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang.jt
 */
@Getter
@Setter
public class TableDo {
	/** id*/
	private String id;
	/** 包名*/
	private String packName;
	/** 表名*/
	private String tableName;
	/** 中文表名*/
	private String tableNameCh;
	/** 备注*/
	private String description;

	public TableDo() {
	}

	public TableDo(String name) {
		this.tableName = name;
	}

	public TableDo(String id, String tableName) {
		super();
		this.id = id;
		this.tableName = tableName;
	}

}

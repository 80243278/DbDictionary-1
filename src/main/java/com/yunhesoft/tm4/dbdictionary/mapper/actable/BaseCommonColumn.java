package com.yunhesoft.tm4.dbdictionary.mapper.actable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang.jt
 */
@Getter
@Setter
public abstract class BaseCommonColumn {
	/** implements ICommonColumn*/
	/**
	 * 字段类型
	 */
	private String type;

	/**
	 * 字段长度
	 */
	private int length = 0;

	/**
	 *  字段名
	 */
	private String name;
	/**
	 * decimalLength
	 */
	private int decimalLength = 0;

	/**
	 * 是否为可以为null，true是可以，false是不可以，默认为true
	 */
	private boolean nullValue = true;

	/**
	 * 是否是主键，默认false
	 */
	private boolean key = false;

	/**
	 * 是否自动递增，默认false 只有主键才能使用
	 */
	private boolean autoIncrement = false;

	/**
	 * 默认值，默认为“”
	 */
	private String defaultValue = "";
}

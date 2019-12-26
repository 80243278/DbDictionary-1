package com.yunhesoft.tm4.dbdictionary.entity.domain;

/**
 * @author zhang.jt
 */
public class Column {
	/**id*/
	private int id;
	/**表名*/
	private String tableName;
	/**字段名*/
	private String filedName;
	/**字段名*/
	private String filedName2;
	/**类型*/
	private String filedType;
	/**长度*/
	private int filedLength;
	/**精度*/
	private int precision;
	/**默认值*/
	private String deVlaue;
	/**是否有长度*/
	private boolean hasLength;
	/**是否有精度*/
	private boolean hasPrecision;
	/**是否是主键*/
	private boolean isPrimaryKey;
	/**是否可以为空*/
	private boolean isCanNull;
	/**是否是标识*/
	private boolean isIdentity;
	/**字段说明*/
	private String desc;
	/**外键*/
	private String foreignKey;

	public String getDeVlaue() {
		return deVlaue;
	}

	public void setDeVlaue(String deVlaue) {
		this.deVlaue = deVlaue;
	}

	public int getFiledLength() {
		return filedLength;
	}

	public void setFiledLength(int filedLength) {
		this.filedLength = filedLength;
	}

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public String getFiledType() {
		return filedType;
	}

	public void setFiledType(String filedType) {
		this.filedType = filedType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isHasLength() {
		return hasLength;
	}

	public void setHasLength(boolean hasLength) {
		this.hasLength = hasLength;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public boolean isHasPrecision() {
		return hasPrecision;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setHasPrecision(boolean hasPrecision) {
		this.hasPrecision = hasPrecision;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public boolean isCanNull() {
		return isCanNull;
	}

	public boolean isIdentity() {
		return isIdentity;
	}

	public String getDesc() {
		return desc;
	}

	public void setCanNull(boolean isCanNull) {
		this.isCanNull = isCanNull;
	}

	public void setIdentity(boolean isIdentity) {
		this.isIdentity = isIdentity;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}

	public String getFiledName2() {
		return filedName2;
	}

	public void setFiledName2(String filedName2) {
		this.filedName2 = filedName2;
	}

	@Override
	public boolean equals(Object obj) {
		if (!obj.getClass().toString().equals(this.getClass().toString())) {
			return false;
		}
		Column cExcel = (Column) obj;
		// 类型是否相同,为true时，则相同
		boolean boolType = true;
		// 因为从数据库读取出来的列类型全改为小写,所以这里也要改成小写
		if (!this.getFiledType().equals(cExcel.getFiledType().toLowerCase())) {
			boolType = false;
		}

		// 引用修改 //TODO 此处法存在问题
		Boolean boolFk = true;
		if (this.getForeignKey() != null && cExcel.getForeignKey() != null) {
			if (this.getForeignKey().length() > 0 && cExcel.getForeignKey().length() > 0) {
				if (!this.getForeignKey().equals(cExcel.getForeignKey())) {
					boolFk = false;
				}
			}
		}

		// 中文名是否改变
		boolean nameCh = true;
		if (!this.getFiledName2().equals(cExcel.getFiledName2())) {
			nameCh = false;
		}

		// 长度是否相同,为true时，则长度相同
		boolean boolLength = true;
		// 有长度
		if (cExcel.isHasLength()) {
			boolLength = cExcel.getFiledLength() == this.getFiledLength();
		}
		// 是否都有或者没有长度
		else {
			boolLength = cExcel.isHasLength() == this.isHasLength();
		}

		// 精度是否相同,为true时，则精度相同（为true时，则长度相同）
		boolean boolPrecision = true;
		// 有精度
		if (cExcel.isHasPrecision()) {
			boolPrecision = cExcel.getPrecision() == this.getPrecision();
		}
		// 是否都有或者没有精度
		else {
			boolPrecision = cExcel.isHasPrecision() == this.isHasPrecision();
		}
		// 内容一样
		if (boolType && boolLength && boolPrecision && nameCh && boolFk) {
			return true;
		}

		return false;
	}

}

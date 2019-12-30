package com.yunhesoft.tm4.dbdictionary.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author zhang.jt
 * @category 数据库表字段字典表POJO
 */
@Getter
@Setter
@Entity
@Table(name = "sys_dict_column")
@TableName("sys_dict_column")
public class SysDictColumn implements Serializable {
	@TableField(exist = false)
	@Transient
	private static final long serialVersionUID = -4171056764523609168L;
	@Id
	@TableId(type = IdType.ID_WORKER_STR)
	private String tmuid;
	@Column(length = 50)
	private String columnName;
	@Column(length = 200)
	private String columnShowName;
	@Column(length = 50)
	private String tableId;
	@Column(length = 50)
	private String tableName;
	@Column(length = 50)
	private String dataType;
	private Boolean notNull;
	private Boolean primaryKey;
	private Boolean used;
	private Integer sort;
	@Column(length = 4000)
	private String remark;
	private Integer size;
	private Integer scale;
}

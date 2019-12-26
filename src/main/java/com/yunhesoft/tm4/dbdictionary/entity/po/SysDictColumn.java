package com.yunhesoft.tm4.dbdictionary.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhang.jt
 * @category 数据库表字段字典表POJO
 */
@Getter
@Setter
@Entity
@Table(name = "sys_dict_column")
@TableName("sys_dict_column")
public class SysDictColumn {
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

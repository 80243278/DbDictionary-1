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
 * @Description: 数据库表字典表POJO
 */
@Getter
@Setter
@Entity
@Table(name = "sys_dict_table")
@TableName("sys_dict_table")
public class SysDictTable {
	@Id
	@TableId(type = IdType.ID_WORKER_STR)
	private String tmuid;
	@Column(length = 50)
	private String tableName;
	@Column(length = 200)
	private String tableShowName;
	@Column(length = 50)
	private String moduleCode;
	@Column(length = 50)
	private String dbConnId;
	private Boolean used;
	private Integer sort;
	@Column(length = 4000)
	private String remark;
}

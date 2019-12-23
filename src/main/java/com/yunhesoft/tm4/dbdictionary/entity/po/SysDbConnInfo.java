package com.yunhesoft.tm4.dbdictionary.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: zhanglw
 * @Date: 2019/12/22 15:04
 * @Description: 数据库连接表POLRI
 */
@Data
@Table(name = "sys_dbConnInfo")
@TableName("sys_dbConnInfo")
public class SysDbConnInfo {
	@Id
	@TableId(type = IdType.ID_WORKER)
	private String tmuid;
	@Column(length = 50)
	@TableField
	private String dbName;
	@Column(length = 50)
	@TableField
	private String dbShowName;
	@Column(length = 200)
	@TableField
	private String dbUrl;
	@Column(length = 50)
	@TableField
	private String dbConnUserName;
	@Column(length = 50)
	@TableField
	private String dbConnPassWord;
	@Column(length = 200)
	@TableField
	private String dbDialect;
	@Column
	@TableField
	private Integer used;
	@Column(length = 200)
	@TableField
	private Integer sort;
}

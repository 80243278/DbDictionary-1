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
 * @Description: 数据库连接表POJO
 */
@Getter
@Setter
@Entity
@Table(name = "sys_db_conn")
@TableName("sys_db_conn")
public class SysDbConn {
	@Id
	@TableId(type = IdType.ID_WORKER)
	@Column(length = 50)
	private String tmuid;
	@Column(length = 200)
	private String dbName;
	@Column(length = 200)
	private String dbShowName;
	@Column(length = 200)
	private String dbUrl;
	@Column(length = 50)
	private String dbUserName;
	@Column(length = 50)
	private String dbPassWord;
	@Column(length = 200)
	private String dbDialect;
	private Boolean used;
	private Integer sort;
	@Column(length = 4000)
	private String remark;
}

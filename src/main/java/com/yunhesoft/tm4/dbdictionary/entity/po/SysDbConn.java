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
	@TableId(type = IdType.ID_WORKER_STR)
	@Column(length = 50)
	/**唯一标识*/
	private String tmuid;
	@Column(length = 200)
	/**库名*/
	private String dbName;
	@Column(length = 200)
	/**库中文名*/
	private String dbShowName;
	@Column(length = 200)
	/**库连接地址*/
	private String dbUrl;
	@Column(length = 50)
	/**登录账号*/
	private String dbUserName;
	@Column(length = 50)
	/**登录密码*/
	private String dbPassWord;
	@Column(length = 200)
	/**库方言 mysql/mssql/oracle*/
	private String dbDialect;
	/**1：可用，0：不可用*/
	private Boolean used;
	/**序号*/
	private Integer sort;
	@Column(length = 4000)
	/**备注*/
	private String remark;
}

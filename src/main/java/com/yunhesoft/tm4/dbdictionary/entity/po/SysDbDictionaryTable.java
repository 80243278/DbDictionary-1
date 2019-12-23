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
 * @Date: 2019/12/22 15:22
 * @Description: 数据库字典表信息存储PO
 */
@Data
@Table(name = "sys_dbDictionary_table")
@TableName("sys_dbDictionary_table")
public class SysDbDictionaryTable {
	@Id
	@TableId(type = IdType.ID_WORKER)
	private String tmuid;
	@Column(length = 50)
	@TableField
	private String tableName;
	@Column(length = 50)
	@TableField
	private String tableShowName;
	@Column(length = 50)
	@TableField
	private String moduleCode;
	@Column(length = 50)
	@TableField
	private String dbConnTmuid;
	@Column
	@TableField
	private Integer used;
	@Column
	@TableField
	private Integer sort;
	@Column(length = 200)
	@TableField
	private String remark;
}

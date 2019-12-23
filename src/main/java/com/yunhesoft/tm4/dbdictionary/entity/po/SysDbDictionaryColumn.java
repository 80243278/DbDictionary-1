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
 * @Date: 2019/12/22 15:25
 * @Description:
 */
@Data
@Table(name = "sys_dbDictionary_column")
@TableName("sys_dbDictionary_column")
public class SysDbDictionaryColumn {
	@Id
	@TableId(type = IdType.ID_WORKER)
	private String tmuid;
	@Column(length = 50)
	@TableField
	private String columnName;
	@Column(length = 50)
	@TableField
	private String columnShowName;
	@Column(length = 50)
	@TableField
	private String tableTmuid;
	@Column(length = 50)
	@TableField
	private String dataType;
	@Column
	@TableField
	private Integer notNull;
	@Column
	@TableField
	private Integer primaryKey;
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

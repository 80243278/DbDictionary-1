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
public class SysDictColumn /**implements Serializable*/
{
	/**@TableField(exist = false)
	@Transient
	private static final long serialVersionUID = -4171056764523609168L;*/
	@Id
	@TableId(type = IdType.ID_WORKER_STR)
	/**唯一标识*/
	private String tmuid;
	@Column(length = 50)
	/**字段名*/
	private String columnName;
	@Column(length = 200)
	/**字段中文名*/
	private String columnShowName;
	@Column(length = 50)
	/**表id*/
	private String tableId;
	@Column(length = 50)
	/**表名*/
	private String tableName;
	@Column(length = 50)
	/**字段类型*/
	private String dataType;
	/**字段值不为空*/
	private Boolean notNull;
	/**是否为主键*/
	private Boolean primaryKey;
	/**是否可用*/
	private Boolean used;
	/**排序*/
	private Integer sort;
	@Column(length = 4000)
	/**备注*/
	private String remark;
	/**字段值最大长度*/
	private Integer size;
	/**字段值小数位数*/
	private Integer scale;
}

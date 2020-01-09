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
	/**唯一标识*/
	private String tmuid;
	@Column(length = 50)
	/**模块id*/
	private String moduleId;
	@Column(length = 50)
	/**模块名*/
	private String tableName;
	@Column(length = 200)
	/**模块中文名*/
	private String tableShowName;
	@Column(length = 50)
	/**模块编码，预留，后期扩展选择模块*/
	private String moduleCode;
	@Column(length = 50)
	/**库连接id*/
	private String dbConnId;
	/**是否可用*/
	private Boolean used;
	/**序号*/
	private Integer sort;
	@Column(length = 4000)
	/**备注*/
	private String remark;
}

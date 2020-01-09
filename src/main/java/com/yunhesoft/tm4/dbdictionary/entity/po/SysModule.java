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
 * @Description: 模块表POJO
 */
@Getter
@Setter
@Entity
@Table(name = "sys_module")
@TableName("sys_module")
public class SysModule {
	@Id
	@TableId(type = IdType.ID_WORKER_STR)
	/**唯一id*/
	private String tmuid;
	@Column(length = 50)
	/**库连接id*/
	private String dbConnId;
	@TableId(type = IdType.ID_WORKER_STR)
	@Column(length = 50)
	/**模块编码，预留，后期扩展选择模块*/
	private String moduleCode;
	@Column(length = 200)
	/**模块名称*/
	private String moduleName;
	@Column(length = 50)
	/**模块类型*/
	private String moduleType;
	/**是否可用*/
	private Boolean used;
	/**排序*/
	private Integer sort;
	@Column(length = 4000)
	/**备注*/
	private String remark;
}

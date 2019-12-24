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
	@TableId(type = IdType.ID_WORKER)
	private String tmuid;
	@Column(length = 50)
	private String moduleCode;
	@Column(length = 200)
	private String moduleName;
	@Column(length = 50)
	private String moduleType;
	private Boolean used;
	private Integer sort;
	@Column(length = 4000)
	private String remark;
}

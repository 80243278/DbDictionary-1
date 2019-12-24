package com.yunhesoft.tm4.dbdictionary.entity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: zhang.lw
 * @Date: 2019/12/22 17:30
 * @Description: 树形视图对象
 */
@Getter
@Setter
public class SysDictTableDto {
	private String label;
	private String type;
	private String dataId;
	private String nodeId;
	private String name;
}

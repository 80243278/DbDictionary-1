package com.yunhesoft.tm4.dbdictionary.entity.dto;

import lombok.Data;

/**
 * @author: zhanglw
 * @Date: 2019/12/22 17:30
 * @Description: 树形视图对象
 */
@Data
public class DbTableTreeDto {
	private String label;
	private String type;
	private String dataId;
	private String nodeId;
	private String name;
}

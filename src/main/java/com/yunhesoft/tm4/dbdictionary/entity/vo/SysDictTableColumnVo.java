package com.yunhesoft.tm4.dbdictionary.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang.jt
 */
@Getter
@Setter
public class SysDictTableColumnVo {
	@ApiModelProperty(value = "数据库表")
	SysDictTableVo tableVo;
	@ApiModelProperty(value = "数据库表字段列表")
	List<SysDictColumnVo> colVoList;
}

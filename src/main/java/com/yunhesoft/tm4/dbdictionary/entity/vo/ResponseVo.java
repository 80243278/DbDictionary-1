package com.yunhesoft.tm4.dbdictionary.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 普通操作应答
 * 		例如：增、删、改操作返回结果（成功/失败）
 * @author zhang.jt
 */
@Getter
@Setter
@ApiModel(description = "[应答结果] 通用应答结果")
public class ResponseVo {
	@ApiModelProperty(value = "200:成功/500:失败")
	private Integer status;
	@ApiModelProperty(value = "应答提示内容")
	private String msg;
	@ApiModelProperty(value = "应答体对象")
	private Object body;

	private ResponseVo() {

	}

	private ResponseVo(Integer status, String msg, Object body) {
		this.status = status;
		this.msg = msg;
		this.body = body;
	}

	public static ResponseVo ok(String msg, Object body) {
		return new ResponseVo(200, msg, body);
	}

	public static ResponseVo ok(String msg) {
		return new ResponseVo(200, msg, null);
	}

	public static ResponseVo ok(Object body) {
		return new ResponseVo(200, null, body);
	}

	public static ResponseVo error(String msg, Object body) {
		return new ResponseVo(500, msg, body);
	}

	public static ResponseVo error(String msg) {
		return new ResponseVo(500, msg, null);
	}

	public static ResponseVo error(Object body) {
		return new ResponseVo(500, null, body);
	}
}

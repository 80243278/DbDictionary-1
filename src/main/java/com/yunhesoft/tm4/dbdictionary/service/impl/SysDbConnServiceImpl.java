package com.yunhesoft.tm4.dbdictionary.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDbConnDto;
import com.yunhesoft.tm4.dbdictionary.entity.po.SysDbConn;
import com.yunhesoft.tm4.dbdictionary.mapper.SysDbConnMapper;
import com.yunhesoft.tm4.dbdictionary.service.ISysDbConnService;

/**
 * @author zhang.jt
 */
@Service
@Repository("SysDbConnServiceImpl")
public class SysDbConnServiceImpl extends ServiceImpl<SysDbConnMapper, SysDbConn> implements ISysDbConnService {
	/**
	 * 获取表连接数据
	 * @return List<SysDbConnDto>
	 */
	@Override
	public List<SysDbConnDto> getSysDbConn() {
		List<SysDbConnDto> dtoList = new ArrayList<SysDbConnDto>();
		LambdaQueryWrapper<SysDbConn> query = new LambdaQueryWrapper<SysDbConn>();
		query.orderByAsc(SysDbConn::getSort);
		List<SysDbConn> list = this.list(query);

		if (list != null) {
			for (SysDbConn b : list) {
				SysDbConnDto nb = new SysDbConnDto();
				BeanUtils.copyProperties(b, nb);
				dtoList.add(nb);
			}
		}

		return dtoList;
	}
}

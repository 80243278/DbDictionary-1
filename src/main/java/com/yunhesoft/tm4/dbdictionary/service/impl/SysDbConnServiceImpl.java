package com.yunhesoft.tm4.dbdictionary.service.impl;

import java.util.ArrayList;
import java.util.Collection;
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
	 * 获取数据库连接数据
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

	/**
	 * @category 通过ID获取数据库连接数据
	 * @param tmuid
	 * @return
	 */
	@Override
	public List<SysDbConnDto> getSysDbConnById(String tmuid) {
		if (tmuid == null || "".equals(tmuid)) {
			return null;
		}
		LambdaQueryWrapper<SysDbConn> query = new LambdaQueryWrapper<SysDbConn>();
		query.eq(SysDbConn::getTmuid, tmuid);
		query.orderByAsc(SysDbConn::getSort);
		List<SysDbConn> list = this.list(query);
		List<SysDbConnDto> newList = new ArrayList<SysDbConnDto>();

		if (list != null) {
			for (SysDbConn b : list) {
				SysDbConnDto nb = new SysDbConnDto();
				BeanUtils.copyProperties(b, nb);
				newList.add(nb);
			}
		}

		return newList;
	}

	/**
	 * 保存数据库连接数据
	 * @param addDtoList
	 * @param delDtoList
	 * @param updDtoList
	 * @return
	 */
	@Override
	public boolean saveSysDbConn(List<SysDbConnDto> addDtoList, List<SysDbConnDto> delDtoList,
			List<SysDbConnDto> updDtoList) {
		boolean flag = true;

		try {
			// 删除
			if (delDtoList != null) {
				List<String> tmuidList = new ArrayList<String>();
				for (SysDbConnDto colDto : delDtoList) {
					tmuidList.add(colDto.getTmuid());
				}
				if (tmuidList.size() > 0) {
					flag = this.removeByIds(tmuidList);
				}
			}
			// 添加
			if (addDtoList != null) {
				Collection<SysDbConn> collectAdd = new ArrayList<SysDbConn>();
				for (SysDbConnDto colDto : addDtoList) {
					SysDbConn col = new SysDbConn();
					BeanUtils.copyProperties(colDto, col);
					collectAdd.add(col);
				}
				if (collectAdd.size() > 0) {
					flag = this.saveBatch(collectAdd);
				}
			}
			// 更新
			if (updDtoList != null) {
				Collection<SysDbConn> collectUpd = new ArrayList<SysDbConn>();
				for (SysDbConnDto colDto : updDtoList) {
					SysDbConn col = new SysDbConn();
					BeanUtils.copyProperties(colDto, col);
					collectUpd.add(col);
				}
				if (collectUpd.size() > 0) {
					flag = this.updateBatchById(collectUpd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
}

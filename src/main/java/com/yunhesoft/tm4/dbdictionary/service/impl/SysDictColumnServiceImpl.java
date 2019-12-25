package com.yunhesoft.tm4.dbdictionary.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictColumnDto;
import com.yunhesoft.tm4.dbdictionary.entity.po.SysDictColumn;
import com.yunhesoft.tm4.dbdictionary.mapper.SysDictColumnMapper;
import com.yunhesoft.tm4.dbdictionary.service.ISysDictColumnService;

/**
 * @author zhang.jt
 */
@Service
@Repository("SysDictColumnServiceImpl")
public class SysDictColumnServiceImpl extends ServiceImpl<SysDictColumnMapper, SysDictColumn>
		implements ISysDictColumnService {
	/**
	 * @category 通过ID获取表数据
	 * @param tmuid
	 * @return
	 */
	@Override
	public List<SysDictColumnDto> getSysDictColumnById(String tmuid) {
		if (tmuid == null || "".equals(tmuid)) {
			return null;
		}
		LambdaQueryWrapper<SysDictColumn> query = new LambdaQueryWrapper<SysDictColumn>();
		query.eq(SysDictColumn::getTmuid, tmuid);
		query.orderByAsc(SysDictColumn::getSort);
		List<SysDictColumn> list = this.list(query);
		List<SysDictColumnDto> newList = new ArrayList<SysDictColumnDto>();

		if (list != null) {
			for (SysDictColumn b : list) {
				SysDictColumnDto nb = new SysDictColumnDto();
				BeanUtils.copyProperties(b, nb);
				newList.add(nb);
			}
		}

		return newList;
	}

	/**
	 * 通过表ID获取表字段数据
	 * @param tableId
	 * @return List<SysDictColumnDto>
	 */
	@Override
	public List<SysDictColumnDto> getSysDictColumnByTableId(String tableId) {
		if (tableId == null || "".equals(tableId)) {
			return null;
		}
		LambdaQueryWrapper<SysDictColumn> query = new LambdaQueryWrapper<SysDictColumn>();
		query.eq(SysDictColumn::getColumnShowName, tableId);
		query.orderByAsc(SysDictColumn::getSort);
		List<SysDictColumn> list = this.list(query);
		List<SysDictColumnDto> newList = new ArrayList<SysDictColumnDto>();

		if (list != null) {
			for (SysDictColumn b : list) {
				SysDictColumnDto nb = new SysDictColumnDto();
				BeanUtils.copyProperties(b, nb);
				newList.add(nb);
			}
		}

		return newList;
	}

	/**
	 * @category 添加表数据
	 * @param modDto
	 * @return
	 */
	@Override
	public boolean addSysDictColumn(SysDictColumnDto modDto) {
		if (modDto == null || modDto.getTmuid() == null) {
			return false;
		}
		SysDictColumn modNew = new SysDictColumn();
		BeanUtils.copyProperties(modDto, modNew);
		boolean flag = this.save(modNew);
		return flag;
	}

	/**
	 * @category 修改表
	 * @param modDto
	 * @return
	 */
	@Override
	public boolean updSysDictColumn(SysDictColumnDto modDto) {
		if (modDto == null || modDto.getTmuid() == null) {
			return false;
		}
		SysDictColumn modNew = new SysDictColumn();
		BeanUtils.copyProperties(modDto, modNew);
		boolean flag = this.updateById(modNew);
		return flag;
	}

	/**
	 * @category 删除表
	 * @param modDto
	 * @return
	 */
	@Override
	public boolean delSysDictColumn(SysDictColumnDto modDto) {
		if (modDto == null || modDto.getTmuid() == null) {
			return false;
		}
		boolean flag = this.removeById(modDto.getTmuid());
		return flag;
	}
}

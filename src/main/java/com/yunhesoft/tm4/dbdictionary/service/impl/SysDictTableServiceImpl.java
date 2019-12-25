package com.yunhesoft.tm4.dbdictionary.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictTableDto;
import com.yunhesoft.tm4.dbdictionary.entity.po.SysDictTable;
import com.yunhesoft.tm4.dbdictionary.mapper.SysDictTableMapper;
import com.yunhesoft.tm4.dbdictionary.service.ISysDictTableService;

/**
 * @author zhang.jt
 */
@Service
@Repository("SysDictTableServiceImpl")
public class SysDictTableServiceImpl extends ServiceImpl<SysDictTableMapper, SysDictTable>
		implements ISysDictTableService {
	/**
	 * @category 通过ID获取表数据
	 * @param tmuid
	 * @return
	 */
	@Override
	public List<SysDictTableDto> getSysDictTableById(String tmuid) {
		if (tmuid == null || "".equals(tmuid)) {
			return null;
		}
		LambdaQueryWrapper<SysDictTable> query = new LambdaQueryWrapper<SysDictTable>();
		query.eq(SysDictTable::getTmuid, tmuid);
		query.orderByAsc(SysDictTable::getSort);
		List<SysDictTable> list = this.list(query);
		List<SysDictTableDto> newList = new ArrayList<SysDictTableDto>();

		if (list != null) {
			for (SysDictTable b : list) {
				SysDictTableDto nb = new SysDictTableDto();
				BeanUtils.copyProperties(b, nb);
				newList.add(nb);
			}
		}

		return newList;
	}

	/**
	 * 通过模块id获取数据库表数据
	 * @param moduleId
	 * @return
	 */
	@Override
	public List<SysDictTableDto> getSysDictTableByModuleId(String moduleId) {
		if (moduleId == null || "".equals(moduleId)) {
			return null;
		}
		LambdaQueryWrapper<SysDictTable> query = new LambdaQueryWrapper<SysDictTable>();
		query.eq(SysDictTable::getModuleId, moduleId);
		query.orderByAsc(SysDictTable::getSort);
		List<SysDictTable> list = this.list(query);
		List<SysDictTableDto> newList = new ArrayList<SysDictTableDto>();

		if (list != null) {
			for (SysDictTable b : list) {
				SysDictTableDto nb = new SysDictTableDto();
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
	public boolean addSysDictTable(SysDictTableDto modDto) {
		if (modDto == null || modDto.getTmuid() == null) {
			return false;
		}
		SysDictTable modNew = new SysDictTable();
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
	public boolean updSysDictTable(SysDictTableDto modDto) {
		if (modDto == null || modDto.getTmuid() == null) {
			return false;
		}
		SysDictTable modNew = new SysDictTable();
		BeanUtils.copyProperties(modDto, modNew);
		boolean flag = this.save(modNew);
		return flag;
	}

	/**
	 * @category 删除表
	 * @param modDto
	 * @return
	 */
	@Override
	public boolean delSysDictTable(SysDictTableDto modDto) {
		if (modDto == null || modDto.getTmuid() == null) {
			return false;
		}
		boolean flag = this.removeById(modDto.getTmuid());
		return flag;
	}
}

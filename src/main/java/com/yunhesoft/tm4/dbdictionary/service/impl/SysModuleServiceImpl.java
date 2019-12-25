package com.yunhesoft.tm4.dbdictionary.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysModuleDto;
import com.yunhesoft.tm4.dbdictionary.entity.po.SysModule;
import com.yunhesoft.tm4.dbdictionary.mapper.SysModuleMapper;
import com.yunhesoft.tm4.dbdictionary.service.ISysModuleService;
import com.yunhesoft.tm4.dbdictionary.utils.ToolUtils;

/**
 * @author zhang.jt
 */
@Service
@Repository("SysModuleServiceImpl")
public class SysModuleServiceImpl extends ServiceImpl<SysModuleMapper, SysModule> implements ISysModuleService {
	/**
	 * @category 通过ID获取模块数据
	 * @param tmuid
	 * @return
	 */
	@Override
	public List<SysModuleDto> getSysModuleById(String tmuid) {
		if (tmuid == null || "".equals(tmuid)) {
			return null;
		}
		LambdaQueryWrapper<SysModule> query = new LambdaQueryWrapper<SysModule>();
		query.eq(SysModule::getTmuid, tmuid);
		query.orderByAsc(SysModule::getSort);
		List<SysModule> list = this.list(query);
		List<SysModuleDto> newList = new ArrayList<SysModuleDto>();

		if (list != null) {
			for (SysModule b : list) {
				SysModuleDto nb = new SysModuleDto();
				BeanUtils.copyProperties(b, nb);
				newList.add(nb);
			}
		}

		return newList;
	}

	/**
	 * 通过数据库连接ID获取模块数据
	 * @param connId
	 * @return
	 */
	@Override
	public List<SysModuleDto> getSysModuleByConnId(String connId) {
		if (connId == null || "".equals(connId)) {
			return null;
		}
		LambdaQueryWrapper<SysModule> query = new LambdaQueryWrapper<SysModule>();
		query.eq(SysModule::getDbConnId, connId);
		query.orderByAsc(SysModule::getSort);
		List<SysModule> list = this.list(query);
		List<SysModuleDto> newList = new ArrayList<SysModuleDto>();

		if (list != null) {
			for (SysModule b : list) {
				SysModuleDto nb = new SysModuleDto();
				BeanUtils.copyProperties(b, nb);
				newList.add(nb);
			}
		}

		return newList;
	}

	/**
	 * @category 添加模块数据
	 * @param modDto
	 * @return
	 */
	@Override
	public boolean addSysModule(SysModuleDto modDto) {
		if (modDto == null || modDto.getTmuid() == null) {
			return false;
		}
		SysModule modNew = new SysModule();
		BeanUtils.copyProperties(modDto, modNew);
		String uuid = ToolUtils.getUuid();
		modNew.setModuleCode(uuid);
		boolean flag = this.save(modNew);
		return flag;
	}

	/**
	 * @category 修改模块
	 * @param modDto
	 * @return
	 */
	@Override
	public boolean updSysModule(SysModuleDto modDto) {
		if (modDto == null || modDto.getTmuid() == null) {
			return false;
		}
		SysModule modNew = new SysModule();
		BeanUtils.copyProperties(modDto, modNew);
		boolean flag = this.updateById(modNew);
		return flag;
	}

	/**
	 * @category 删除模块
	 * @param modDto
	 * @return
	 */
	@Override
	public boolean delSysModule(SysModuleDto modDto) {
		if (modDto == null || modDto.getTmuid() == null) {
			return false;
		}
		boolean flag = this.removeById(modDto.getTmuid());
		return flag;
	}
}

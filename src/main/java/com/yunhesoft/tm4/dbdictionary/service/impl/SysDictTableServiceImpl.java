package com.yunhesoft.tm4.dbdictionary.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictTableDto;
import com.yunhesoft.tm4.dbdictionary.entity.po.SysDictTable;
import com.yunhesoft.tm4.dbdictionary.mapper.SysDictTableMapper;
import com.yunhesoft.tm4.dbdictionary.service.ISysDictTableService;
import com.yunhesoft.tm4.dbdictionary.utils.ToolUtils;

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
	 * 通过表名获取表数据
	 * @param tmuid
	 * @return List<SysDictTableDto>
	 */
	@Override
	public List<SysDictTableDto> getSysDictTableByName(String tableName) {
		if (tableName == null || "".equals(tableName)) {
			return null;
		}
		LambdaQueryWrapper<SysDictTable> query = new LambdaQueryWrapper<SysDictTable>();
		query.eq(SysDictTable::getTableName, tableName);
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
	 * 通过表名模糊检索获取表数据
	 * @param tableName
	 * @return
	 */
	@Override
	public List<SysDictTableDto> getSysDictTableByNameFuzzy(String tableName) {
		if (tableName == null || "".equals(tableName)) {
			return null;
		}
		LambdaQueryWrapper<SysDictTable> query = new LambdaQueryWrapper<SysDictTable>();
		query.like(SysDictTable::getTableName, tableName);
		query.or();
		query.like(SysDictTable::getTableShowName, tableName);
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
	 * 获取字典表名数据Map
	 * @return
	 */
	@Override
	public Map<String, SysDictTableDto> getSysDictTableNameMap() {
		Map<String, SysDictTableDto> map = new LinkedHashMap<String, SysDictTableDto>();

		LambdaQueryWrapper<SysDictTable> query = new LambdaQueryWrapper<SysDictTable>();
		query.orderByAsc(SysDictTable::getTableName);
		List<SysDictTable> list = this.list(query);

		if (list != null) {
			for (SysDictTable b : list) {
				SysDictTableDto nb = new SysDictTableDto();
				BeanUtils.copyProperties(b, nb);
				map.put(nb.getTableName(), nb);
			}
		}

		return map;
	}

	/**
	 * @category 添加表数据
	 * @param modDto
	 * @return
	 */
	@Override
	public SysDictTableDto addSysDictTable(SysDictTableDto modDto) {
		SysDictTableDto rstDto = null;
		if (modDto == null || modDto.getTmuid() == null) {
			return rstDto;
		}
		SysDictTable modNew = new SysDictTable();
		modDto.setTmuid(ToolUtils.getUuid());
		BeanUtils.copyProperties(modDto, modNew);
		boolean flag = this.save(modNew);
		if (flag == true) {
			rstDto = modDto;
		}

		return rstDto;
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
		boolean flag = this.updateById(modNew);
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

	/**
	 * 保存表列表
	 * @param addDtoList
	 * @param delDtoList
	 * @param updDtoList
	 * @return
	 */
	@Override
	public boolean saveSysDictTableColumn(List<SysDictTableDto> addDtoList, List<SysDictTableDto> delDtoList,
			List<SysDictTableDto> updDtoList) {
		boolean flag = true;

		try {
			// 删除
			if (delDtoList != null) {
				List<String> tmuidList = new ArrayList<String>();
				for (SysDictTableDto colDto : delDtoList) {
					tmuidList.add(colDto.getTmuid());
				}
				if (tmuidList.size() > 0) {
					flag = this.removeByIds(tmuidList);
				}
			}
			// 添加
			if (addDtoList != null) {
				Collection<SysDictTable> collectAdd = new ArrayList<SysDictTable>();
				for (SysDictTableDto colDto : addDtoList) {
					SysDictTable col = new SysDictTable();
					BeanUtils.copyProperties(colDto, col);
					collectAdd.add(col);
				}
				if (collectAdd.size() > 0) {
					flag = this.saveBatch(collectAdd);
				}
			}
			// 更新
			if (updDtoList != null) {
				Collection<SysDictTable> collectUpd = new ArrayList<SysDictTable>();
				for (SysDictTableDto colDto : updDtoList) {
					SysDictTable col = new SysDictTable();
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

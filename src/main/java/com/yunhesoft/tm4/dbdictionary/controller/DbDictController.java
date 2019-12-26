package com.yunhesoft.tm4.dbdictionary.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDbConnDto;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictColumnDto;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysDictTableDto;
import com.yunhesoft.tm4.dbdictionary.entity.dto.SysModuleDto;
import com.yunhesoft.tm4.dbdictionary.entity.vo.ResponseVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysDictColumnVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysDictTableColumnVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysDictTableVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysModuleVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysTreeNodeVo;
import com.yunhesoft.tm4.dbdictionary.service.ISysDbConnService;
import com.yunhesoft.tm4.dbdictionary.service.ISysDbSyncService;
import com.yunhesoft.tm4.dbdictionary.service.ISysDictColumnService;
import com.yunhesoft.tm4.dbdictionary.service.ISysDictTableService;
import com.yunhesoft.tm4.dbdictionary.service.ISysModuleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhang.jt
 */
@Api(tags = "数据字典控制器")
@Controller
@RequestMapping("/dict")
public class DbDictController {
	@Autowired
	private ISysDbConnService connService;
	@Autowired
	private ISysModuleService moduleService;
	@Autowired
	private ISysDictTableService tableService;
	@Autowired
	private ISysDictColumnService columnService;
	@Autowired
	private ISysDbSyncService syncService;

	@Autowired
	ApplicationContext applicationContext;

	@ResponseBody
	@RequestMapping(value = "/addModule", method = { RequestMethod.POST })
	@ApiOperation(value = "添加模块")
	@ApiImplicitParam(name = "module", value = "模块对象", required = true, paramType = "body", dataType = "SysModuleVo")
	public ResponseVo addModule(@RequestBody SysModuleVo module) {
		ResponseVo resp = ResponseVo.ok("添加模块成功");
		SysModuleDto dto = new SysModuleDto();
		BeanUtils.copyProperties(module, dto);
		boolean flag = moduleService.addSysModule(dto);
		if (flag == false) {
			resp = ResponseVo.error("添加模块失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/updModule", method = { RequestMethod.POST })
	@ApiOperation(value = "修改模块")
	@ApiImplicitParam(name = "module", value = "模块对象", required = true, paramType = "body", dataType = "SysModuleVo")
	public ResponseVo updModule(@RequestBody SysModuleVo module) {
		ResponseVo resp = ResponseVo.ok("修改模块成功");
		SysModuleDto dto = new SysModuleDto();
		BeanUtils.copyProperties(module, dto);
		boolean flag = moduleService.updSysModule(dto);
		if (flag == false) {
			resp = ResponseVo.error("修改模块失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/delModule", method = { RequestMethod.POST })
	@ApiOperation(value = "删除模块")
	@ApiImplicitParam(name = "module", value = "模块对象", required = true, paramType = "body", dataType = "SysModuleVo")
	public ResponseVo delModule(@RequestBody SysModuleVo module) {
		ResponseVo resp = ResponseVo.ok("删除模块成功");
		SysModuleDto dto = new SysModuleDto();
		BeanUtils.copyProperties(module, dto);
		boolean flag = moduleService.delSysModule(dto);
		if (flag == false) {
			resp = ResponseVo.error("删除模块失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/addTable", method = { RequestMethod.POST })
	@ApiOperation(value = "添加表")
	@ApiImplicitParam(name = "table", value = "表对象", required = true, paramType = "body", dataType = "SysDictTableVo")
	public ResponseVo addTable(@RequestBody SysDictTableVo table) {
		ResponseVo resp = ResponseVo.ok("添加表成功");
		SysDictTableDto dto = new SysDictTableDto();
		BeanUtils.copyProperties(table, dto);
		SysDictTableDto rstDto = tableService.addSysDictTable(dto);
		if (rstDto == null) {
			resp = ResponseVo.error("添加表失败");
		} else {
			SysDictTableVo rstVo = new SysDictTableVo();
			BeanUtils.copyProperties(rstDto, rstVo);
			resp = ResponseVo.ok("添加表成功", rstVo);
		}

		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/updTable", method = { RequestMethod.POST })
	@ApiOperation(value = "修改表")
	@ApiImplicitParam(name = "table", value = "表对象", required = true, paramType = "body", dataType = "SysDictTableVo")
	public ResponseVo updTable(@RequestBody SysDictTableVo table) {
		ResponseVo resp = ResponseVo.ok("修改表成功");
		SysDictTableDto dto = new SysDictTableDto();
		BeanUtils.copyProperties(table, dto);
		boolean flag = tableService.updSysDictTable(dto);
		if (flag == false) {
			resp = ResponseVo.error("修改表失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/delTable", method = { RequestMethod.POST })
	@ApiOperation(value = "删除表")
	@ApiImplicitParam(name = "table", value = "表对象", required = true, paramType = "body", dataType = "SysDictTableVo")
	public ResponseVo delTable(@RequestBody SysDictTableVo table) {
		ResponseVo resp = ResponseVo.ok("删除表成功");
		SysDictTableDto dto = new SysDictTableDto();
		BeanUtils.copyProperties(table, dto);
		boolean flag = tableService.delSysDictTable(dto);
		if (flag == false) {
			resp = ResponseVo.error("删除表失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/addCol", method = { RequestMethod.POST })
	@ApiOperation(value = "添加表字段")
	@ApiImplicitParam(name = "column", value = "表字段对象", required = true, paramType = "body", dataType = "SysDictColumnVo")
	public ResponseVo addCol(@RequestBody SysDictColumnVo column) {
		ResponseVo resp = ResponseVo.ok("添加表字段成功");
		SysDictColumnDto dto = new SysDictColumnDto();
		BeanUtils.copyProperties(column, dto);
		dto.setSize(column.getColumnLength());
		dto.setScale(column.getColumnDecimalPlace());
		boolean flag = columnService.addSysDictColumn(dto);
		if (flag == false) {
			resp = ResponseVo.error("添加表字段失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/updCol", method = { RequestMethod.POST })
	@ApiOperation(value = "修改表字段")
	@ApiImplicitParam(name = "column", value = "表字段对象", required = true, paramType = "body", dataType = "SysDictColumnVo")
	public ResponseVo updCol(@RequestBody SysDictColumnVo column) {
		ResponseVo resp = ResponseVo.ok("修改表字段成功");
		SysDictColumnDto dto = new SysDictColumnDto();
		BeanUtils.copyProperties(column, dto);
		dto.setSize(column.getColumnLength());
		dto.setScale(column.getColumnDecimalPlace());
		boolean flag = columnService.updSysDictColumn(dto);
		if (flag == false) {
			resp = ResponseVo.error("修改表字段失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/delCol", method = { RequestMethod.POST })
	@ApiOperation(value = "删除表字段")
	@ApiImplicitParam(name = "column", value = "表字段对象", required = true, paramType = "body", dataType = "SysDictColumnVo")
	public ResponseVo delCol(@RequestBody SysDictColumnVo column) {
		ResponseVo resp = ResponseVo.ok("删除表字段成功");
		SysDictColumnDto dto = new SysDictColumnDto();
		BeanUtils.copyProperties(column, dto);
		dto.setSize(column.getColumnLength());
		dto.setScale(column.getColumnDecimalPlace());
		boolean flag = columnService.delSysDictColumn(dto);
		if (flag == false) {
			resp = ResponseVo.error("删除表字段失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/getTree", method = { RequestMethod.POST })
	@ApiOperation(value = "获取树形节点")
	@ApiImplicitParam(name = "node", value = "父节点对象", required = true, paramType = "body", dataType = "SysTreeNodeVo")
	public List<SysTreeNodeVo> getTree(@RequestBody SysTreeNodeVo node) {
		List<SysTreeNodeVo> nodeVoList = new ArrayList<SysTreeNodeVo>();
		if (node == null || node.getType() == null) {
			return nodeVoList;
		}

		String type = node.getType();
		String typeRoot = "root";
		String typeDb = "db";
		String typeModule = "module";
		String typeTable = "table";
		String typeColumn = "column";

		// 查表连接数据
		if (type.equals(typeRoot)) {
			List<SysDbConnDto> dtoList = connService.getSysDbConn();
			if (dtoList != null) {
				for (SysDbConnDto connDto : dtoList) {
					SysTreeNodeVo nodeVo = new SysTreeNodeVo();
					nodeVo.setNodeId(connDto.getTmuid());
					nodeVo.setDataId(connDto.getTmuid());
					nodeVo.setIsLeaf(false);
					nodeVo.setType(typeDb);
					nodeVo.setLabel(connDto.getDbShowName());
					nodeVo.setObj(connDto);
					nodeVoList.add(nodeVo);
				}
			}
		}
		// 查模块数据
		else if (type.equals(typeDb)) {
			List<SysModuleDto> dtoList = moduleService.getSysModuleByConnId(node.getNodeId());
			if (dtoList != null) {
				for (SysModuleDto modDto : dtoList) {
					SysTreeNodeVo nodeVo = new SysTreeNodeVo();
					nodeVo.setNodeId(modDto.getTmuid());
					nodeVo.setDataId(modDto.getTmuid());
					nodeVo.setIsLeaf(false);
					nodeVo.setType(typeModule);
					nodeVo.setLabel(modDto.getModuleName());
					nodeVo.setObj(modDto);
					nodeVoList.add(nodeVo);
				}
			}
		}
		// 查表数据
		else if (type.equals(typeModule)) {
			List<SysDictTableDto> dtoList = tableService.getSysDictTableByModuleId(node.getNodeId());
			if (dtoList != null) {
				for (SysDictTableDto modDto : dtoList) {
					SysTreeNodeVo nodeVo = new SysTreeNodeVo();
					nodeVo.setNodeId(modDto.getTmuid());
					nodeVo.setDataId(modDto.getTmuid());
					nodeVo.setIsLeaf(false);
					nodeVo.setType(typeTable);
					nodeVo.setLabel(modDto.getTableShowName());
					nodeVo.setObj(modDto);
					nodeVoList.add(nodeVo);
				}
			}
		}
		// 查表字段数据
		else if (type.equals(typeTable)) {
			List<SysDictColumnDto> dtoList = columnService.getSysDictColumnByTableId(node.getNodeId());
			if (dtoList != null) {
				for (SysDictColumnDto modDto : dtoList) {
					SysTreeNodeVo nodeVo = new SysTreeNodeVo();
					nodeVo.setNodeId(modDto.getTmuid());
					nodeVo.setDataId(modDto.getTmuid());
					nodeVo.setIsLeaf(false);
					nodeVo.setType(typeColumn);
					nodeVo.setLabel(modDto.getColumnShowName());
					nodeVo.setObj(modDto);
					nodeVoList.add(nodeVo);
				}
			}
		}

		return nodeVoList;
	}

	@ResponseBody
	@RequestMapping(value = "/getCol", method = { RequestMethod.POST })
	@ApiOperation(value = "获取表字段数据")
	@ApiImplicitParam(name = "tableId", value = "表id", required = true)
	public List<SysDictColumnVo> getCol(String tableId) {
		List<SysDictColumnVo> colVoList = new ArrayList<SysDictColumnVo>();
		if (tableId == null) {
			return colVoList;
		}
		List<SysDictColumnDto> dtoList = columnService.getSysDictColumnByTableId(tableId);
		if (dtoList != null) {
			for (SysDictColumnDto colDto : dtoList) {
				SysDictColumnVo colVo = new SysDictColumnVo();
				BeanUtils.copyProperties(colDto, colVo);
				colVo.setColumnLength(colDto.getSize());
				colVo.setColumnDecimalPlace(colDto.getScale());
				colVoList.add(colVo);
			}
		}
		return colVoList;
	}

	@ResponseBody
	@RequestMapping(value = "/syncDb", method = { RequestMethod.POST })
	@ApiOperation(value = "正向同步（字典->数据库）")
	/**@ApiImplicitParam(name = "tableId", value = "表id", required = true)*/
	public ResponseVo syncDictToDb(SysDictTableColumnVo tableAndCol) {
		ResponseVo resp = ResponseVo.ok("同步成功");

		if (tableAndCol == null || tableAndCol.getTableVo() == null || tableAndCol.getColVoList() == null) {
			resp = ResponseVo.error("同步失败");
			return resp;
		}

		SysDictTableDto tableDto = new SysDictTableDto();
		List<SysDictColumnDto> colDtoList = new ArrayList<SysDictColumnDto>();
		BeanUtils.copyProperties(tableAndCol.getTableVo(), tableDto);
		for (SysDictColumnVo voBean : tableAndCol.getColVoList()) {
			SysDictColumnDto colDto = new SysDictColumnDto();
			BeanUtils.copyProperties(voBean, colDto);
			colDtoList.add(colDto);
		}

		boolean flag = syncService.syncDictToDb(tableDto, colDtoList);
		if (flag == false) {
			resp = ResponseVo.error("同步失败");
		}

		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/syncDict", method = { RequestMethod.POST })
	@ApiOperation(value = "反向同步（数据库->字典）")
	/**@ApiImplicitParam(name = "tableId", value = "表id", required = true)*/
	public ResponseVo syncDbToDict(SysDictTableVo tableVo) {
		ResponseVo resp = ResponseVo.ok("同步成功");
		SysDictTableDto tableDto = new SysDictTableDto();
		BeanUtils.copyProperties(tableVo, tableDto);
		boolean flag = syncService.syncDbToDict(tableDto);
		if (flag == false) {
			resp = ResponseVo.error("同步失败");
		}
		return resp;
	}
}

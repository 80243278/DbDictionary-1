package com.yunhesoft.tm4.dbdictionary.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysDictDbModuleTableVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysDictModuleTableVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysDictTableColumnVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysDictTableVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysModuleVo;
import com.yunhesoft.tm4.dbdictionary.entity.vo.SysTreeNodeVo;
import com.yunhesoft.tm4.dbdictionary.service.IPropService;
import com.yunhesoft.tm4.dbdictionary.service.ISysDbConnService;
import com.yunhesoft.tm4.dbdictionary.service.ISysDbSyncService;
import com.yunhesoft.tm4.dbdictionary.service.ISysDictColumnService;
import com.yunhesoft.tm4.dbdictionary.service.ISysDictTableService;
import com.yunhesoft.tm4.dbdictionary.service.ISysModuleService;
import com.yunhesoft.tm4.dbdictionary.utils.ToolUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhang.jt
 */
@Api(tags = "数据库字典控制器")
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
	private IPropService propService;

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

		// 检查表名是否重复
		List<SysDictTableDto> dbTableList = tableService.getSysDictTableByName(dto.getTableName());
		if (dbTableList != null && dbTableList.size() > 0) {
			resp = ResponseVo.error("此表名已存在，请您修改表名");
			return resp;
		}

		// 新增表
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

		// 检查表名是否重复
		List<SysDictTableDto> dbTableList = tableService.getSysDictTableByName(dto.getTableName());
		if (dbTableList != null && dbTableList.size() > 0) {
			for (SysDictTableDto dbTbDto : dbTableList) {
				if (!dto.getTmuid().equals(dbTbDto.getTmuid())) {
					// 非当前修改的表，与其它表名冲突
					resp = ResponseVo.error("此表名已存在，请您修改表名");
					return resp;
				}
			}
		}

		// 修改表
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
	@RequestMapping(value = "/saveCol", method = { RequestMethod.POST })
	@ApiOperation(value = "保存添加表字段数据列表")
	@ApiImplicitParam(name = "columnList", value = "表字段列表对象", required = true, paramType = "body", dataType = "List<SysDictColumnVo>")
	public ResponseVo saveCol(@RequestBody List<SysDictColumnVo> columnList) {
		ResponseVo resp = ResponseVo.ok("保存表字段数据成功");

		if (columnList == null || columnList.size() <= 0) {
			resp = ResponseVo.error("没有需要保存的数据");
		}

		List<SysDictColumnDto> addDtoList = new ArrayList<SysDictColumnDto>();
		List<SysDictColumnDto> delDtoList = new ArrayList<SysDictColumnDto>();
		List<SysDictColumnDto> updDtoList = new ArrayList<SysDictColumnDto>();

		for (SysDictColumnVo colVo : columnList) {
			SysDictColumnDto dto = new SysDictColumnDto();
			BeanUtils.copyProperties(colVo, dto);
			dto.setSize(colVo.getColumnLength());
			dto.setScale(colVo.getColumnDecimalPlace());
			Integer flag = colVo.getFlag();
			if (flag == null) {
				continue;
			}
			// 添加
			if (flag.intValue() == 1) {
				addDtoList.add(dto);
			}
			// 修改
			else if (flag.intValue() == 0) {
				updDtoList.add(dto);
			}
			// 删除
			else if (flag.intValue() == -1) {
				delDtoList.add(dto);
			}
		}

		boolean flag = columnService.saveSysDictColumn(addDtoList, delDtoList, updDtoList);
		if (flag == false) {
			resp = ResponseVo.error("保存表字段数据失败");
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
			if (dtoList != null && dtoList.size() > 0) {
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
			} else {
				// 获取当前连接库名
				String curDbName = syncService.getCurrentDbName();
				// 初始化当前数据库连接节点
				SysDbConnDto connDto = new SysDbConnDto();
				connDto.setTmuid(ToolUtils.getUuid());
				String dbShowName = "当前数据库";
				if (curDbName != null && !"".equals(curDbName)) {
					dbShowName += "(" + curDbName + ")";
				}
				connDto.setDbName(curDbName);
				connDto.setDbShowName(dbShowName);
				connDto.setRemark(dbShowName);
				connDto.setSort(1);
				connDto.setUsed(true);
				connDto.setDbDialect(propService.getPropDbType());
				List<SysDbConnDto> connDtoList = new ArrayList<SysDbConnDto>();
				connDtoList.add(connDto);
				boolean flag = connService.saveSysDbConn(connDtoList, null, null);
				if (flag == true) {
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
					nodeVo.setIsLeaf(true);
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
	@ApiOperation(value = "单表正向同步（字典 → 数据库）")
	@ApiImplicitParam(name = "tableAndCol", value = "表与列", required = true, paramType = "body", dataType = "SysDictTableColumnVo")
	public ResponseVo syncDictToDb(@RequestBody SysDictTableColumnVo tableAndCol) {
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
			colDto.setSize(voBean.getColumnLength());
			colDto.setScale(voBean.getColumnDecimalPlace());
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
	@ApiOperation(value = "单表反向同步（数据库 → 字典）")
	@ApiImplicitParam(name = "table", value = "表", required = true, paramType = "body", dataType = "SysDictTableVo")
	public ResponseVo syncDbToDict(@RequestBody SysDictTableVo table) {
		ResponseVo resp = ResponseVo.ok("同步成功");
		SysDictTableDto tableDto = new SysDictTableDto();
		BeanUtils.copyProperties(table, tableDto);
		boolean flag = syncService.syncDbToDict(tableDto);
		if (flag == false) {
			resp = ResponseVo.error("同步失败");
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/getSyncTables", method = { RequestMethod.POST })
	@ApiOperation(value = "获取所有数据库表")
	/**@ApiImplicitParam(name = "table", value = "表", required = true, paramType = "body", dataType = "SysDictTableVo")*/
	public List<SysDictTableVo> getSyncTables() {
		List<SysDictTableVo> tbVoList = new ArrayList<SysDictTableVo>();
		List<SysDictTableDto> tbDtoList = syncService.getSyncTables();

		if (tbDtoList != null) {
			for (SysDictTableDto tbDto : tbDtoList) {
				SysDictTableVo tbVo = new SysDictTableVo();
				BeanUtils.copyProperties(tbDto, tbVo);
				tbVoList.add(tbVo);
			}
		}

		return tbVoList;
	}

	@ResponseBody
	@RequestMapping(value = "/syncDictByTables", method = { RequestMethod.POST })
	@ApiOperation(value = "多表反向同步（数据库 → 字典）")
	@ApiImplicitParam(name = "tables", value = "数据库表列表", required = true, paramType = "body", dataType = "List<SysDictTableVo>")
	public ResponseVo syncDictByTables(@RequestBody SysDictModuleTableVo moduleTableVo/*List<SysDictTableVo> tables*/) {
		ResponseVo resp = ResponseVo.ok("同步成功");

		if (moduleTableVo == null) {
			resp = ResponseVo.error("同步失败");
			return resp;
		}

		List<SysDictTableDto> dtoList = new ArrayList<SysDictTableDto>();

		List<SysDictTableVo> tables = moduleTableVo.getTableVoList();
		for (SysDictTableVo tbVo : tables) {
			SysDictTableDto dto = new SysDictTableDto();
			BeanUtils.copyProperties(tbVo, dto);
			dtoList.add(dto);
		}

		SysModuleVo module = moduleTableVo.getModuleVo();
		boolean flag = syncService.syncDictByTables(module, dtoList);
		if (flag == false) {
			resp = ResponseVo.error("同步失败");
		}

		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/getTableByName", method = { RequestMethod.POST })
	@ApiOperation(value = "获取表数据（模糊检索表名）")
	@ApiImplicitParam(name = "name", value = "表名", required = true)
	public List<SysDictDbModuleTableVo> getTableByName(String tableName) {
		List<SysDictDbModuleTableVo> list = new ArrayList<SysDictDbModuleTableVo>();

		List<SysDictTableDto> tbDtoList = tableService.getSysDictTableByNameFuzzy(tableName);
		if (tbDtoList != null && tbDtoList.size() > 0) {
			for (SysDictTableDto tbDto : tbDtoList) {
				List<SysDbConnDto> dbconnDtoList = connService.getSysDbConnById(tbDto.getDbConnId());
				List<SysModuleDto> moduleDtoList = moduleService.getSysModuleById(tbDto.getModuleId());
				if (dbconnDtoList != null && dbconnDtoList.size() > 0 && moduleDtoList != null
						&& moduleDtoList.size() > 0) {
					SysDbConnDto dbconnDto = dbconnDtoList.get(0);
					SysModuleDto moduleDto = moduleDtoList.get(0);

					SysDictDbModuleTableVo dbModuleTableVo = new SysDictDbModuleTableVo();
					SysDictColumnVo dbVo = new SysDictColumnVo();
					SysModuleVo moduleVo = new SysModuleVo();
					SysDictTableVo tableVo = new SysDictTableVo();
					BeanUtils.copyProperties(tbDto, tableVo);
					BeanUtils.copyProperties(dbconnDto, dbVo);
					BeanUtils.copyProperties(moduleDto, moduleVo);
					dbModuleTableVo.setDbVo(dbVo);
					dbModuleTableVo.setModuleVo(moduleVo);
					dbModuleTableVo.setTableVo(tableVo);
					// 拼装表名
					String value = tbDto.getTableName();
					if (tbDto.getTableShowName() != null && !"".equals(tbDto.getTableShowName())) {
						value += "(" + tbDto.getTableShowName() + ")";
					}
					dbModuleTableVo.setValue(value);
					list.add(dbModuleTableVo);
				}
			}
		}

		return list;
	}
}

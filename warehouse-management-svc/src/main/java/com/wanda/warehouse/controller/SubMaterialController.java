package com.wanda.warehouse.controller;

import java.util.List;
import java.util.Objects;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.warehouse.domain.Dictionary;
import com.wanda.warehouse.domain.SubMaterialStockVo;
import com.wanda.warehouse.domain.SubMaterialVo;
import com.wanda.warehouse.service.SubMaterialService;
import com.wanda.warehouse.service.SubMaterialStockService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/subMaterial")
@Api(tags = "子物料相关功能")
public class SubMaterialController {
	
	private static Logger log = Logger.getLogger(SubMaterialController.class);
	
	@Autowired SubMaterialService subMaterialService;
	@Autowired SubMaterialStockService subMaterialStockService;
	
	@ApiOperation(value = "查询子物料列表", notes = "分页查询子物料表")
	@GetMapping("/page/{page}/{size}")
	public Page<SubMaterialVo> getSubMaterials(@RequestHeader String userCode, @PathVariable int page, @PathVariable int size) {
		long start = System.currentTimeMillis();
		Page<SubMaterialVo> voPage = null;
		try {
			voPage = subMaterialService.findAllSubMaterials(page, size);
		}catch(Exception e) {
			log.error(userCode +" getSubMaterials("+page+","+size+") error.",e);
		}
		log.info(userCode +" getSubMaterials("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+voPage);
		return voPage;
	}
	
	@ApiOperation(value = "查询子物料列表", notes = "查询子物料表")
	@GetMapping("/list")
	public List<SubMaterialVo> getSubMaterials(@RequestHeader String userCode) {
		long start = System.currentTimeMillis();
		List<SubMaterialVo> voList = null;
		try {
			voList = subMaterialService.findAllSubMaterials();
		}catch(Exception e) {
			log.error(userCode +" getSubMaterials() error.",e);
		}
		log.info(userCode +" getSubMaterials() cost "+(System.currentTimeMillis()-start)+" ms. return "+voList);
		return voList;
	}

	@ApiOperation(value = "查询子物料变更轨迹列表", notes = "分页查询子物料变更轨迹表")
	@GetMapping("/page/{subMaterialCode}/{page}/{size}")
	public Page<SubMaterialStockVo> getSubMaterialStocks(@RequestHeader String userCode, @PathVariable String subMaterialCode, @PathVariable int page, @PathVariable int size) {
		long start = System.currentTimeMillis();
		Page<SubMaterialStockVo> voPage = null;
		try {
			voPage = subMaterialStockService.findAllSubMaterialStocks(page, size);
		}catch(Exception e) {
			log.error(userCode +" getSubMaterialStocks("+page+","+size+") error.",e);
		}
		log.info(userCode +" getSubMaterialStocks("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+voPage);
		return voPage;
	}
	
	@ApiOperation(value = "查询单个子物料详情", notes = "根据id查询子物料表一条记录")
	@GetMapping("/one/{id}")
	public SubMaterialVo getSubMaterial(@RequestHeader String userCode, @PathVariable int id) {
		long start = System.currentTimeMillis();
		SubMaterialVo voSubMaterial = null;
		try {
			voSubMaterial = subMaterialService.findSubMaterialById(id);
		}catch(Exception e) {
			log.error(userCode +" getSubMaterial(id = "+id+") error", e);
		}
		log.info(userCode +" getSubMaterial(id = " + id + ") cost "+(System.currentTimeMillis()-start)+" ms. return "+voSubMaterial);
		return voSubMaterial;
	}
	
	@ApiOperation(value = "新增子物料", notes = "向子物料表插入一条记录")
	@PostMapping("/add")
	public long addSubMaterial(@RequestHeader String userCode,  @RequestBody SubMaterialVo vo) {
		long start = System.currentTimeMillis();
		long id = -1;
		try {
			SubMaterialVo oldVo = subMaterialService.findSubMaterialBySubMaterialCode(vo.getSubMaterialCode());
			if(Objects.nonNull(oldVo)) {
				vo.setId(oldVo.getId());
				vo.setStatus(Dictionary.DATA_STATUS_VALID.getDcode());
				this.subMaterialService.updateSubMaterial(vo, userCode);
				id = vo.getId();
			}else {
				id = subMaterialService.addSubMaterial(vo, userCode);
			}
		
			
		}catch(Exception e) {
			log.error(userCode + " addSubMaterial("+vo+","+userCode+") error.",e);
		}
		log.info(userCode + " addSubMaterial("+vo+","+userCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}
	
	@ApiOperation(value = "修改子物料详情", notes = "修改子物料详情，注意subMaterialCode一旦确定不允许更改，如需更改则先删除现有记录，重新添加")
	@PostMapping("/update")
	public int updateSubMaterial(@RequestHeader String userCode, @RequestBody SubMaterialVo vo) {
		//以下内容在service的一个方法中写，注意使用事务
		//1.如果未修改库存信息，则直接变更子物料对象
		//2.如果修改库存信息，则要向子物料sub_material_stock新增一条记录，然后再更新当前子物料对象
		long start = System.currentTimeMillis();
		int record = -1;
		try {
			//userCode合法性检查
			if(userCode == null || userCode.isEmpty()) {
				throw new Exception("userCode is null userCode is empty.");
			}
			//vo合法性检查
			if(vo == null) {
				throw new Exception("vo is null.");
			}
			if(vo.getId() < 1) {
				throw new Exception("id is less than 1.");
			}
			SubMaterialVo voSubMaterial = subMaterialService.findSubMaterialById(vo.getId());
			if(voSubMaterial.getSubMaterialCode().equals(vo.getSubMaterialCode()) && 
				voSubMaterial.getStatus() == Dictionary.DATA_STATUS_VALID.getDcode()) {

				voSubMaterial.setMaterialCode(vo.getMaterialCode());
				voSubMaterial.setStock(vo.getStock());
				voSubMaterial.setSubMaterialCode(vo.getSubMaterialCode());
				voSubMaterial.setSubMaterialName(vo.getSubMaterialName());
				voSubMaterial.setUnit(vo.getUnit());
				//sub_material_stock新增一条记录
				SubMaterialStockVo voStock = new SubMaterialStockVo(voSubMaterial.getId(), voSubMaterial.getSubMaterialCode(), 
						voSubMaterial.getStock()-vo.getStock(), "0000", userCode, vo.getStock(), 
						Dictionary.DATA_STATUS_VALID.getDcode(), userCode);
				subMaterialStockService.addSubMaterialStock(voStock, userCode);
				record = subMaterialService.updateSubMaterial(voSubMaterial, userCode);	
			}
			else {
				throw new Exception("SubMaterialCode is not the same.");
			}
		}catch(Exception e) {
			log.error(userCode+" updateSubMaterial("+vo+") error:"+e.getMessage(),e);
		}
		log.info(userCode+" updateSubMaterial("+vo+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}
	
	@ApiOperation(value = "删除子物料详情", notes = "删除子物料详情，注意这里只做逻辑删除，不做物理删除，即只更新db里的status信息")
	@PostMapping("/delete/{id}")
	public int removeSubMaterial(@RequestHeader String userCode, @PathVariable long id) {
		long start = System.currentTimeMillis();
		int record = -1;
		SubMaterialVo voSubMaterial = null;
		try {
			if(id < 1) {
				throw new Exception("id is less than 1.");
			}
			voSubMaterial = subMaterialService.findSubMaterialById(id);
			voSubMaterial.setStatus(Dictionary.DATA_STATUS_INVALID.getDcode());
			record = subMaterialService.updateSubMaterial(voSubMaterial, userCode);
		}catch(Exception e) {
			log.error(userCode+" deleteSubMaterial("+id+") error:"+e.getMessage(),e);
		}
		log.info(userCode+" deleteSubMaterial("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}
	
}

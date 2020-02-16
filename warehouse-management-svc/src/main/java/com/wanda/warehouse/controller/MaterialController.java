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
import com.wanda.warehouse.domain.MaterialVo;
import com.wanda.warehouse.service.MaterialService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/material")
@Api(tags = "物料相关功能")
public class MaterialController {
	
	private static Logger log = Logger.getLogger(MaterialController.class);
	
	@Autowired MaterialService materialService;
	
	@ApiOperation(value = "查询物料列表", notes = "分页查询物料表")
	@GetMapping("/page/{page}/{size}")
	public Page<MaterialVo> getMaterials(@RequestHeader String userCode, @PathVariable int page, @PathVariable int size) {
		long start = System.currentTimeMillis();
		Page<MaterialVo> voPage = null;
		try {
			voPage = materialService.findAllMaterials(page, size);
		}catch(Exception e) {
			log.error(userCode +" getMaterials("+page+","+size+") error.",e);
		}
		log.info(userCode +" getMaterials("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+voPage);
		return voPage;
	}
	
	@ApiOperation(value = "查询物料列表", notes = "查询物料表")
	@GetMapping("/list")
	public List<MaterialVo> getMaterials(@RequestHeader String userCode) {
		long start = System.currentTimeMillis();
		List<MaterialVo> voList = null;
		try {
			voList = materialService.findAllMaterials();
		}catch(Exception e) {
			log.error(userCode +" getMaterials() error.",e);
		}
		log.info(userCode +" getMaterials() cost "+(System.currentTimeMillis()-start)+" ms. return "+voList);
		return voList;
	}

	@ApiOperation(value = "查询单个物料详情", notes = "根据id查询物料表一条记录")
	@GetMapping("/one/{id}")
	public MaterialVo getMaterial(@RequestHeader String userCode, @PathVariable int id) {
		long start = System.currentTimeMillis();
		MaterialVo voMaterial = null;
		try {
			voMaterial = materialService.findMaterialById(id);
		}catch(Exception e) {
			log.error(userCode +" getMaterial(id = "+id+") error", e);
		}
		log.info(userCode +" getMaterial(id = " + id + ") cost "+(System.currentTimeMillis()-start)+" ms. return "+voMaterial);
		return voMaterial;
	}
	
	@ApiOperation(value = "新增物料", notes = "向物料表插入一条记录")
	@PostMapping("/add")
	public long addMaterial(@RequestHeader String userCode,  @RequestBody MaterialVo vo) {
		long start = System.currentTimeMillis();
		long id = -1;
		try {
			MaterialVo oldVo = materialService.findMaterialsByMaterialCode(vo.getMaterialCode());
			if(Objects.nonNull(oldVo)) {
				vo.setId(oldVo.getId());
				vo.setStatus(Dictionary.DATA_STATUS_VALID.getDcode());
				this.materialService.updateMaterial(vo, userCode);
				id = vo.getId();
			}else {
				id = materialService.addMaterial(vo, userCode);
			}
		}catch(Exception e) {
			log.error(userCode + " addMaterial("+vo+","+userCode+") error.",e);
		}
		log.info(userCode + " addMaterial("+vo+","+userCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}
	
	@ApiOperation(value = "修改物料详情", notes = "修改物料详情，注意materialCode一旦确定不允许更改，如需更改则先删除现有记录，重新添加")
	@PostMapping("/update")
	public int updateMaterial(@RequestHeader String userCode,  @RequestBody MaterialVo vo) {
		long start = System.currentTimeMillis();
		int record = -1;
		MaterialVo voMaterial = null;
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
			voMaterial = materialService.findMaterialById(vo.getId());
			if(voMaterial.getMaterialCode().equals(vo.getMaterialCode())) {
				
				voMaterial.setMaterialCode(vo.getMaterialCode());
				voMaterial.setMaterialName(vo.getMaterialName());
				record = materialService.updateMaterial(voMaterial, userCode);
			}
			else {
				log.debug(userCode + "updateMaterial error");
			}
		}catch(Exception e) {
			log.error(userCode+" updateMaterial("+vo+") error:"+e.getMessage(),e);
		}
		log.info(userCode+" updateMaterial("+vo+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}
	

	@ApiOperation(value = "删除物料详情", notes = "删除物料详情，注意这里只做逻辑删除，不做物理删除，即只更新db里的status信息")
	@PostMapping("/delete/{id}")
	public int removeMaterial(@RequestHeader String userCode, @PathVariable long id) {
		long start = System.currentTimeMillis();
		int record = -1;
		MaterialVo voMaterial = null;
		try {
			if(id < 1) {
				throw new Exception("id is less than 1.");
			}
			voMaterial = materialService.findMaterialById(id);
			MaterialVo vo = new MaterialVo(id, voMaterial.getMaterialCode(), voMaterial.getMaterialName(), Dictionary.PRODUCT_STATUS_INVALID.getDcode(), voMaterial.getEnteredBy());
			record = materialService.updateMaterial(vo, null);
		}catch(Exception e) {
			log.error(userCode+" deleteMaterial("+id+") error:"+e.getMessage(),e);
		}
		log.info(userCode+" deleteMaterial("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}
	
}

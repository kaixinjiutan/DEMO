package com.wanda.warehouse.controller;

import java.util.List;

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

import com.wanda.warehouse.domain.ProductSubMaterialRatioVo;
import com.wanda.warehouse.domain.HisProductSubMaterialRatioVo;
import com.wanda.warehouse.service.ProductSubMaterialRatioService;
import com.wanda.warehouse.service.HisProductSubMaterialRatioService;
import com.wanda.warehouse.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/productSubMaterialRatio")
@Api(tags = "产品物料配比相关功能")
public class ProductSubMaterialRatioController {
	
	private static Logger log = Logger.getLogger(ProductSubMaterialRatioController.class);
	
	@Autowired ProductSubMaterialRatioService productSubMaterialRatioService;
	@Autowired HisProductSubMaterialRatioService hisProductSubMaterialRatioService;
	@Autowired ProductService productService;
	
	@ApiOperation(value = "分页查询全部产品物料配比列表", notes = "分页查询全部产品物料配比信息")
	@GetMapping("/page/{page}/{size}")
	public Page<ProductSubMaterialRatioVo> getProductSubMaterialRatios(@RequestHeader String userCode, @PathVariable int page, @PathVariable int size) {
		long start = System.currentTimeMillis();
		Page<ProductSubMaterialRatioVo> voPage = null;
		try {
			voPage = productSubMaterialRatioService.findAllProductSubMaterialRatios(page, size);
		}catch(Exception e) {
			log.error(userCode +" getProductSubMaterialRatios("+page+","+size+") error.",e);
		}
		log.info(userCode +" getProductSubMaterialRatios("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+voPage);
		return voPage;
	}
	
	@ApiOperation(value = "查询产品物料配比列表", notes = "查询产品物料配比表，输入是产品编码，输出是产品物料配比列表，用于产品列表中点击详情时，展示产品下属的物料信息")
	@GetMapping("/list/{productCode}")
	public List<ProductSubMaterialRatioVo> getProductSubMaterialRatiosByProductCode(@RequestHeader String userCode, @PathVariable String productCode) {
		long start = System.currentTimeMillis();
		List<ProductSubMaterialRatioVo> voList = null;
		try {
			voList = productSubMaterialRatioService.findProductSubMaterialRatioByProductCode(productCode);
		}catch(Exception e) {
			log.error(userCode +" getProductSubMaterialRatiosByProductCode("+productCode+") error.",e);
		}
		log.info(userCode +" getProductSubMaterialRatiosByProductCode("+productCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+voList);
		return voList;
	}
	
	@ApiOperation(value = "查询历史产品物料配比列表", notes = "分页查询历史产品物料配比表变更轨迹，输入是产品编码，输出是按产品编码，子物料编码，物料编码，enteredDate（逆序）排序的历史产品物料配比信息，用户产品列表中单击详情时，展示产品下属的物料变更轨迹信息")
	@GetMapping("/hisPage/{page}/{size}")
	public Page<HisProductSubMaterialRatioVo> getHisProductSubMaterialRatios(@RequestHeader String userCode, @PathVariable int page, @PathVariable int size) {
		long start = System.currentTimeMillis();
		Page<HisProductSubMaterialRatioVo> voPage = null;
		try {
			voPage = hisProductSubMaterialRatioService.findAllHisProductSubMaterialRatios(page, size);
		}catch(Exception e) {
			log.error(userCode +" getHisProductSubMaterialRatios("+page+","+size+") error.",e);
		}
		log.info(userCode +" getHisProductSubMaterialRatios("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+voPage);
		return voPage;
	}
	
	@ApiOperation(value = "新增产品物料配比", notes = "向产品物料配比表插入一条记录")
	@PostMapping("/add")
	public long addProductSubMaterialRatio(@RequestHeader String userCode,  @RequestBody ProductSubMaterialRatioVo vo) {
		long start = System.currentTimeMillis();
		long id = -1;
		try {
			id = productSubMaterialRatioService.addProductSubMaterialRatio(vo, userCode);
		}catch(Exception e) {
			log.error(userCode + " addProductSubMaterialRatio("+vo+","+userCode+") error.",e);
		}
		log.info(userCode + " addProductSubMaterialRatio("+vo+","+userCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}
	
	@ApiOperation(value = "修改产品物料配比详情", notes = "修改产品物料配比详情，注意1.productSubMaterialRatioCode一旦确定不允许更改，如需更改则先删除现有记录，重新添加 2.先将这条记录插入到历史产品配比表中去，然后更新现有记录，在一个事务中控制，现有记录的lastUpdateDate和历史记录的enteredDate使用同一个时间对象")
	@PostMapping("/update")
	public int updateProductSubMaterialRatio(@RequestHeader String userCode,  @RequestBody ProductSubMaterialRatioVo vo) {
		//在service中定义一个方法，访问两个不同的repository的save方法
		//1.新增历史产品物料配比，操作历史表
		//2.更新当前产品物料配比，操作当前表
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
			HisProductSubMaterialRatioVo hisvo = new HisProductSubMaterialRatioVo(
					0,	vo.getProductCode(), vo.getSubMaterialCode(), vo.getSubMaterialRatio(),
					vo.getEnteredBy(),vo.getStatus());
			hisProductSubMaterialRatioService.addHisProductSubMaterialRatio(hisvo, userCode);
			record = productSubMaterialRatioService.updateProductSubMaterialRatio(vo, userCode);
		}catch(Exception e) {
			log.error(userCode+" updateProductSubMaterialRatio("+vo+") error:"+e.getMessage(),e);
		}
		log.info(userCode+" updateProductSubMaterialRatio("+vo+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}

	@ApiOperation(value = "删除产品物料配比详情", notes = "删除产品物料配比详情，注意先将这条记录插入到历史产品物料配比表，然后在物理删除产品物料配比表的这条记录，在一个事务中进行控制")
	@PostMapping("/delete/{id}")
	public int removeProductSubMaterialRatio(@RequestHeader String userCode, @PathVariable long id) {
		//在service中定义一个方法，访问两个不同的repository的save方法
		//1.新增历史产品物料配比，操作历史表
		//2.删除当前产品物料配比，操作当前表
		long start = System.currentTimeMillis();
		int record = -1;
		try {
			if(id < 1) {
				throw new Exception("id is less than 1.");
			}
			ProductSubMaterialRatioVo vo = productSubMaterialRatioService.findProductSubMaterialRatioById(id);
			HisProductSubMaterialRatioVo hisvo = new HisProductSubMaterialRatioVo(
					0,	vo.getProductCode(), vo.getSubMaterialCode(), vo.getSubMaterialRatio(),
					vo.getEnteredBy(),vo.getStatus());
			hisProductSubMaterialRatioService.addHisProductSubMaterialRatio(hisvo, userCode);
			productSubMaterialRatioService.removeProductSubMaterialRatio(id);
			record = 1;
		}catch(Exception e) {
			log.error(userCode+" deleteProductSubMaterialRatio("+id+") error:"+e.getMessage(),e);
		}
		log.info(userCode+" deleteProductSubMaterialRatio("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}
	
}

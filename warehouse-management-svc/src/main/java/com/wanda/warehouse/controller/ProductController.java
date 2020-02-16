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

import com.wanda.warehouse.domain.ProductFamily;
import com.wanda.warehouse.domain.ProductVo;
import com.wanda.warehouse.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/product")
@Api(tags = "产品相关功能")
public class ProductController {
	
	private static Logger log = Logger.getLogger(ProductController.class);
	
	@Autowired ProductService productService;
	
	@ApiOperation(value = "分页查询所有产品列表", notes = "分页查询产品表，用于维护产品")
	@GetMapping("/page/{page}/{size}")
	public Page<ProductVo> getProducts(@RequestHeader String userCode, @PathVariable int page, @PathVariable int size) {
		long start = System.currentTimeMillis();
		Page<ProductVo> voPage = null;
		try {
			voPage = productService.findAllProducts(page, size);
		}catch(Exception e) {
			log.error(userCode +" getProducts("+page+","+size+") error.",e);
		}
		log.info(userCode +" getProducts("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+voPage);
		return voPage;
	}
	
	@ApiOperation(value = "查询完成配比的产品列表", notes = "查询产品表status是`STATUS_RATIO_END，返回配比完成的产品信息，用于下订单页面")
	@GetMapping("/list")
	public List<ProductVo> getProducts(@RequestHeader String userCode) {
		long start = System.currentTimeMillis();
		List<ProductVo> voProducts = null;
		try {
			voProducts = productService.findAllProducts();
		}catch(Exception e) {
			log.error(userCode +" getProductsStatusRatioEnd(status = STATUS_RATIO_END) error", e);
		}
		log.info(userCode +" getProductsStatusRatioEnd(status = STATUS_RATIO_END) cost "+(System.currentTimeMillis()-start)+" ms. return "+voProducts);
		return voProducts;
	}
	
	@ApiOperation(value = "查询单个产品详情", notes = "根据id查询产品表一条记录")
	@GetMapping("/one/{id}")
	public ProductVo getProduct(@RequestHeader String userCode, @PathVariable int id) {
		long start = System.currentTimeMillis();
		ProductVo voProduct = null;
		try {
			voProduct = productService.findProductById(id);
		}catch(Exception e) {
			log.error(userCode +" getProduct(id = "+id+") error", e);
		}
		log.info(userCode +" getProduct(id = " + id + ") cost "+(System.currentTimeMillis()-start)+" ms. return "+voProduct);
		return voProduct;
	}
	
	@ApiOperation(value = "新增产品", notes = "向产品表插入一条产品记录以及一组子物料信息")
	@PostMapping("/add")
	public ProductFamily addProduct(@RequestHeader String userCode,  @RequestBody ProductFamily vo) {
		long start = System.currentTimeMillis();
		try {
			//userCode合法性检查
			if(userCode == null || userCode.isEmpty()) {
				throw new Exception("userCode is null userCode is empty.");
			}
			if(vo.getProduct() == null) {
				throw new Exception("Product is null.");
			}
			if(vo.getMaterials() == null || vo.getMaterials().isEmpty()) {
				throw new Exception("Materials is null.");
			}
			ProductVo product = this.productService.findProductByCode(vo.getProduct().getProductCode());
			if(product == null) {
				vo = this.productService.addProductFamily(vo, userCode);
			} else {
				throw new Exception("Product is exist.");
			}
		}catch(Exception e) {
			log.error(userCode + " addProduct("+vo+","+userCode+") error.",e);
		}
		log.info(userCode + " addProduct("+vo+","+userCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+vo);
		return vo;
	}
	
	@ApiOperation(value = "修改产品详情", notes = "修改产品详情，注意productCode一旦确定不允许更改，如需更改则先删除现有记录，重新添加")
	@PostMapping("/update")
	public ProductFamily updateProduct(@RequestHeader String userCode,  @RequestBody ProductFamily vo) {
		long start = System.currentTimeMillis();
		try {
			if(userCode == null || userCode.isEmpty()) {
				throw new Exception("userCode is null userCode is empty.");
			}
			if(vo.getProduct() == null) {
				throw new Exception("Product is null.");
			}
			if(vo.getMaterials() == null || vo.getMaterials().isEmpty()) {
				throw new Exception("Materials is null.");
			}
			if(vo.getProduct().getId() < 1) {
				throw new Exception("id is less than 1.");
			}
			vo = productService.updateProductFamily(vo, userCode);
		}catch(Exception e) {
			log.error(userCode+" updateProduct("+vo+") error:"+e.getMessage(),e);
		}
		log.info(userCode+" updateProduct("+vo+") cost "+(System.currentTimeMillis()-start)+" ms. return "+vo);
		return vo;
	}
	

	@ApiOperation(value = "删除产品详情", notes = "删除产品详情，注意这里只做逻辑删除，不做物理删除，即只更新db里的status信息")
	@PostMapping("/delete/{id}")
	public int removeProduct(@RequestHeader String userCode, @PathVariable long id) {
		long start = System.currentTimeMillis();
		int record = -1;
		try {
			if(id < 1) {
				throw new Exception("id is less than 1.");
			}
			this.productService.removeProduct(id, userCode);
			record = 1;
		}catch(Exception e) {
			log.error(userCode+" deleteProduct("+id+") error:"+e.getMessage(),e);
		}
		log.info(userCode+" deleteProduct("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}
}

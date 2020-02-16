package com.wanda.warehouse.service.impl;

import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wanda.warehouse.domain.Dictionary;
import com.wanda.warehouse.domain.ProductFamily;
import com.wanda.warehouse.domain.ProductSubMaterialRatioVo;
import com.wanda.warehouse.domain.ProductVo;
import com.wanda.warehouse.repository.ProductRepository;
import com.wanda.warehouse.repository.entity.Product;
import com.wanda.warehouse.service.ProductService;
import com.wanda.warehouse.service.ProductSubMaterialRatioService;

import javax.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

	private static Logger log = Logger.getLogger(ProductServiceImpl.class);
	
	@Autowired
	ProductRepository productRepo;
	@Autowired 
	ProductSubMaterialRatioService productSubMaterialRatioService;
	
	@Override
	public Page<ProductVo> findAllProducts(int page, int size) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductVo> products = productRepo.findAllStatusRatioEndAsVo(pageable,Dictionary.PRODUCT_STATUS_INVALID.getDcode());
		log.debug("findAllProducts("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+products);
		return products;
	}
	
	@Override
	public ProductVo findProductById(long id) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		ProductVo product = productRepo.findByIdAsVo(id);
		log.debug("findProductById("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+product);
		return product;
	}

	@Override
	public long addProduct(ProductVo vo, String userCode) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		Product product = this.addEntity(vo, userCode);
		product = productRepo.save(product);
		long id = -1;
		if(product != null && product.getId() > 0) {
			id = product.getId();
		}
		log.debug("saveProduct("+product+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}

	@Override
	public int updateProduct(ProductVo vo, String userCode) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		int record = -1;
		//去db查询当前Product是否存在
		Product oldProduct = productRepo.getOne(vo.getId());
		//如果为空，说明不存在，抛出异常
		if(oldProduct != null) {
			oldProduct = this.updateEntity(vo, userCode, oldProduct);
			oldProduct = productRepo.save(oldProduct);
			if(oldProduct != null && oldProduct.getId() > 0) {
				record = 1;
			}
		}
		log.debug("updateProduct("+vo+","+userCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}

	@Transactional
	@Override
	public void removeProduct(long productId,String userCode) {
		long start = System.currentTimeMillis();
		ProductVo product = this.findProductById(productId);
		this.productSubMaterialRatioService.removeProductSubMaterialRatio(product.getProductCode(), product.getProductName(), userCode);
		productRepo.deleteById(productId);
		log.debug("removeProduct("+productId+") cost "+(System.currentTimeMillis()-start)+" ms.");
	}

	@Override
	public List<ProductVo> findAllProducts() {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		List<ProductVo> products = productRepo.findProductsStatusRatioEndAsVo(Dictionary.PRODUCT_STATUS_INVALID.getDcode());
		log.debug("findProductsStatusRatioEnd(status = STATUS_RATIO_END) cost "+(System.currentTimeMillis()-start)+" ms.");
		return products;
	}
	
	@Override
	public ProductVo findProductByCode(String productCode) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		ProductVo product = productRepo.findByProductCodeAsVo(productCode);
		log.debug("findProductByCode(" + productCode + ") cost " + (System.currentTimeMillis() - start) + " ms. return " + product);
		return product;
	}
	
	@Transactional
	@Override
	public ProductFamily addProductFamily(ProductFamily vo,String userCode) {
		long id = this.addProduct(vo.getProduct(), userCode);
		vo.getProduct().setId(id);
		for (ProductSubMaterialRatioVo mvo : vo.getMaterials()) {
			mvo.setProductCode(vo.getProduct().getProductCode());
			long mid = productSubMaterialRatioService.addProductSubMaterialRatio(mvo, userCode);
			mvo.setId(mid);
		}
		return vo;
	}
	
	@Transactional
	@Override
	public ProductFamily updateProductFamily(ProductFamily vo, String userCode) {
		
//		int record = -1;
//		List<ProductSubMaterialRatioVo>BUList = null;
//		ProductVo voBUProduct = this.findProductById(vo.getProduct().getId());
//		BUList = productSubMaterialRatioService.findProductSubMaterialRatioByProductCode(vo.getProduct().getEnteredBy());
		
		this.updateProduct(vo.getProduct(), userCode);
		for (ProductSubMaterialRatioVo mvo : vo.getMaterials()) {
//			record = -1;
//			record = productSubMaterialRatioService.updateProductSubMaterialRatio(mvo, userName);
			productSubMaterialRatioService.updateProductSubMaterialRatio(mvo, userCode);
//			if(record == -1) {break;}
		}
//备份回溯
/*
		if(record == -1) {
			this.updateProduct(voBUProduct, userName);
			for (ProductSubMaterialRatioVo mvo : BUList) {
				record = productSubMaterialRatioService.updateProductSubMaterialRatio(mvo, userName);
			}
		}
*/
		return vo;
	}
	
	private Product addEntity(ProductVo vo,String userCode) {
		Product p = new Product();
		p.setProductCode(vo.getProductCode());
		p.setProductName(vo.getProductName());
		p.setStatus(Dictionary.PRODUCT_STATUS_VALID.getDcode());
		p.setEnteredBy(userCode);
		p.setEnteredDate(new Date());
		return p;
	}

	private Product updateEntity(ProductVo vo, String userCode, Product p) {
		p.setProductCode(vo.getProductCode());
		p.setProductName(vo.getProductName());	
		p.setStatus(vo.getStatus());
		p.setLastUpdateBy(userCode);
		p.setLastUpdateDate(new Date());
		return p;
	}

}

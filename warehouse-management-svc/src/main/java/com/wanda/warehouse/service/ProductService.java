package com.wanda.warehouse.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wanda.warehouse.domain.ProductFamily;
import com.wanda.warehouse.domain.ProductVo;

public interface ProductService {

	Page<ProductVo> findAllProducts(int page, int size);
	
	ProductVo findProductById(long id);
	
	long addProduct(ProductVo vo,String userCode);
	
	int updateProduct(ProductVo vo,String userCode);
	
	List<ProductVo> findAllProducts();
	
	ProductVo findProductByCode(String productCode);
	
	ProductFamily addProductFamily(ProductFamily vo,String userCode);
	
	ProductFamily updateProductFamily(ProductFamily vo,String userCode);

	void removeProduct(long productId, String userCode);
	
}

package com.wanda.warehouse.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wanda.warehouse.domain.ProductSubMaterialRatioVo;

public interface ProductSubMaterialRatioService {

	
	Page<ProductSubMaterialRatioVo> findAllProductSubMaterialRatios(int page, int size);
	
	ProductSubMaterialRatioVo findProductSubMaterialRatioById(long id);
	
	long addProductSubMaterialRatio(ProductSubMaterialRatioVo vo,String Name);
	
	int updateProductSubMaterialRatio(ProductSubMaterialRatioVo vo,String Name);
	
	void removeProductSubMaterialRatio(long id);
	
	List<ProductSubMaterialRatioVo> findProductSubMaterialRatiosByProductCode(String productCode, int page, int size);
	
	List<ProductSubMaterialRatioVo> findProductSubMaterialRatioByProductCode(String productCode);

	void removeProductSubMaterialRatio(String productCode,String productName,String userName);
}

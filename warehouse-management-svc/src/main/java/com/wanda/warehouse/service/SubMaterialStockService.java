package com.wanda.warehouse.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wanda.warehouse.domain.SubMaterialStockVo;

public interface SubMaterialStockService {

	Page<SubMaterialStockVo> findAllSubMaterialStocks(int page, int size);
	
	SubMaterialStockVo findSubMaterialStockById(long id);
	
	long addSubMaterialStock(SubMaterialStockVo vo,String Name);
	
	int updateSubMaterialStock(SubMaterialStockVo vo,String Name);
	
	void removeSubMaterialStock(long id);

	List<SubMaterialStockVo> findAllSubMaterialStocks();
}

package com.wanda.warehouse.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wanda.warehouse.domain.HisProductSubMaterialRatioVo;

public interface HisProductSubMaterialRatioService {

	Page<HisProductSubMaterialRatioVo> findAllHisProductSubMaterialRatios(int page, int size);
	
	HisProductSubMaterialRatioVo findHisProductSubMaterialRatioById(long id);
	
	List<HisProductSubMaterialRatioVo> findHisProductSubMaterialRatiosByProductCode(String productCode);
	
	long addHisProductSubMaterialRatio(HisProductSubMaterialRatioVo vo,String Name);
	
	int updateHisProductSubMaterialRatio(HisProductSubMaterialRatioVo vo,String Name);
	
	void removeHisProductSubMaterialRatio(long id);
}

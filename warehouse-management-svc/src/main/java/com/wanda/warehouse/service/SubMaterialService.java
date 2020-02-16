package com.wanda.warehouse.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.wanda.warehouse.domain.SubMaterialVo;

public interface SubMaterialService {

	Page<SubMaterialVo> findAllSubMaterials(int page, int size);
	
	List<SubMaterialVo> findAllSubMaterials();
	
	SubMaterialVo findSubMaterialById(long id);
	
	SubMaterialVo findSubMaterialBySubMaterialCode(String Submaterialcode);
	
	List<SubMaterialVo> findSubMaterialBySubMaterialCode(Set<String> Submaterialcodes);
	
	String findPurchaserBySubMaterialCode(String Submaterialcode);
	
	long addSubMaterial(SubMaterialVo vo,String userCode);
	
	int updateSubMaterial(SubMaterialVo vo,String userCode);
	
	void removeSubMaterial(long id);
	
	int updateSubMaterialStock(int quantity, String subMaterialCode, String userCode);
	
	
}

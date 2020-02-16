package com.wanda.warehouse.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wanda.warehouse.domain.Dictionary;
import com.wanda.warehouse.domain.SubMaterialVo;
import com.wanda.warehouse.repository.SubMaterialRepository;
import com.wanda.warehouse.repository.entity.SubMaterial;
import com.wanda.warehouse.service.SubMaterialService;

@Service
public class SubMaterialServiceImpl implements SubMaterialService {

	
	private static Logger log = Logger.getLogger(SubMaterialServiceImpl.class);
	
	@Autowired
	SubMaterialRepository subMaterialRepo;
	
	@Override
	public Page<SubMaterialVo> findAllSubMaterials(int page, int size) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		Pageable pageable = PageRequest.of(page, size);
		Page<SubMaterialVo> subMaterials = subMaterialRepo.findAllActiveAsVo(pageable,Dictionary.DATA_STATUS_VALID.getDcode());
		log.debug("findAllSubMaterials("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+subMaterials);
		return subMaterials;
	}
	
	@Override
	public List<SubMaterialVo> findAllSubMaterials() {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		List<SubMaterialVo> subMaterials = subMaterialRepo.findAllActiveAsVo(Dictionary.DATA_STATUS_VALID.getDcode());
		log.debug("findAllSubMaterials() cost "+(System.currentTimeMillis()-start)+" ms. return "+subMaterials);
		return subMaterials;
	}

	@Override
	public SubMaterialVo findSubMaterialById(long id) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		SubMaterialVo subMaterial = subMaterialRepo.findByIdAsVo(id);
		log.debug("findSubMaterialById("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+subMaterial);
		return subMaterial;
	}

	@Override
	public long addSubMaterial(SubMaterialVo vo, String userCode) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		SubMaterial subMaterial = this.addEntity(vo, userCode);
		subMaterial = subMaterialRepo.save(subMaterial);
		long id = -1;
		if(subMaterial != null && subMaterial.getId() > 0) {
			id = subMaterial.getId();
		}
		log.debug("saveSubMaterial("+subMaterial+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}

	@Override
	public int updateSubMaterial(SubMaterialVo vo, String userCode) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		int record = -1;
		//去db查询当前Material是否存在
		SubMaterial oldSubMaterial = subMaterialRepo.getOne(vo.getId());
		//如果为空，说明不存在，抛出异常
		if(oldSubMaterial != null) {
			oldSubMaterial = this.updateEntity(vo, userCode, oldSubMaterial);
			oldSubMaterial = subMaterialRepo.save(oldSubMaterial);
			if(oldSubMaterial != null && oldSubMaterial.getId() > 0) {
				record = 1;
			}
		}
		log.debug("updateSubMaterial("+vo+","+userCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}

	@Override
	public void removeSubMaterial(long id) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		subMaterialRepo.deleteById(id);
		log.debug("removeSubMaterial("+id+") cost "+(System.currentTimeMillis()-start)+" ms.");
	}

	@Override
	public SubMaterialVo findSubMaterialBySubMaterialCode(String Submaterialcode) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		SubMaterialVo subMaterial = subMaterialRepo.findBySubMaterialCodeAsVo(Submaterialcode);
		log.debug("findSubMaterialBySubMaterialCode("+Submaterialcode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+subMaterial);
		return subMaterial;
	}
	
	@Override
	public String findPurchaserBySubMaterialCode(String Submaterialcode) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		SubMaterialVo subMaterial = subMaterialRepo.findBySubMaterialCodeAsVo(Submaterialcode);
		log.debug("findPurchaserBySubMaterialCode("+Submaterialcode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+subMaterial.getEnteredBy());
		return subMaterial.getEnteredBy();
	}
	
	private SubMaterial addEntity(SubMaterialVo vo,String Name) {
		SubMaterial sm = new SubMaterial();
		sm.setSubMaterialCode(vo.getSubMaterialCode());
		sm.setSubMaterialName(vo.getSubMaterialName());
		sm.setMaterialCode(vo.getMaterialCode());
		sm.setStock(vo.getStock());
		sm.setUnit(vo.getUnit());
		sm.setCost(vo.getCost());
		sm.setStatus(Dictionary.DATA_STATUS_VALID.getDcode());
		sm.setEnteredBy(Name);
		sm.setEnteredDate(new Date());
		return sm;
	}

	private SubMaterial updateEntity(SubMaterialVo vo, String Name, SubMaterial sm) {
		sm.setSubMaterialCode(vo.getSubMaterialCode());
		sm.setSubMaterialName(vo.getSubMaterialName());
		sm.setMaterialCode(vo.getMaterialCode());
		sm.setStock(vo.getStock());
		sm.setUnit(vo.getUnit());
		sm.setCost(vo.getCost());
		sm.setStatus(vo.getStatus());
		sm.setLastUpdateBy(Name);
		sm.setLastUpdateDate(new Date());
		return sm;
	}

	@Override
	public List<SubMaterialVo> findSubMaterialBySubMaterialCode(Set<String> submaterialcodes) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		List<SubMaterialVo> subMaterials = subMaterialRepo.findBySubMaterialCodeAsVo(submaterialcodes);
		log.debug("findSubMaterialBySubMaterialCode("+submaterialcodes+") cost "+(System.currentTimeMillis()-start)+" ms. return "+subMaterials);
		return subMaterials;
	}

	@Override
	public int updateSubMaterialStock(int quantity, String subMaterialCode, String userCode) {
		long start = System.currentTimeMillis();
		SubMaterialVo vo = this.subMaterialRepo.findBySubMaterialCodeAsVo(subMaterialCode);
		int record = subMaterialRepo.updateSubMaterialStock(quantity, vo.getSubMaterialCode(), vo.getStock(),userCode);
		log.debug("updateSubMaterialStock("+quantity+","+subMaterialCode+"+"+userCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}

}

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
import com.wanda.warehouse.domain.HisProductSubMaterialRatioVo;
import com.wanda.warehouse.repository.HisProductSubMaterialRatioRepository;
import com.wanda.warehouse.repository.entity.HisProductSubMaterialRatio;
import com.wanda.warehouse.service.HisProductSubMaterialRatioService;

@Service
public class HisProductSubMaterialRatioServiceImpl implements HisProductSubMaterialRatioService {

	private static Logger log = Logger.getLogger(HisProductSubMaterialRatioServiceImpl.class);
	
	@Autowired
	HisProductSubMaterialRatioRepository hisProductSubMaterialRatioRepo;
	
	
	@Override
	public Page<HisProductSubMaterialRatioVo> findAllHisProductSubMaterialRatios(int page, int size) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		Pageable pageable = PageRequest.of(page, size);
		Page<HisProductSubMaterialRatioVo> hisProductSubMaterialRatios = hisProductSubMaterialRatioRepo.findAllActiveAsVo(pageable,Dictionary.DATA_STATUS_VALID.getDcode());
		log.debug("findAllHisProductSubMaterialRatios("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+hisProductSubMaterialRatios);
		return hisProductSubMaterialRatios;
	}

	@Override
	public HisProductSubMaterialRatioVo findHisProductSubMaterialRatioById(long id) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		HisProductSubMaterialRatioVo hisProductSubMaterialRatio = hisProductSubMaterialRatioRepo.findByIdAsVo(id);
		log.debug("findHisProductSubMaterialRatioById("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+hisProductSubMaterialRatio);
		return hisProductSubMaterialRatio;
	}
	
	@Override
	public List<HisProductSubMaterialRatioVo> findHisProductSubMaterialRatiosByProductCode(String productCode) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		List<HisProductSubMaterialRatioVo> vos = hisProductSubMaterialRatioRepo.findByProductCodeAsVo(productCode);
		log.debug("findHisProductSubMaterialRatioById("+productCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+vos);
		return vos;
	}

	@Override
	public long addHisProductSubMaterialRatio(HisProductSubMaterialRatioVo vo, String Name) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		HisProductSubMaterialRatio hisProductSubMaterialRatio = this.addEntity(vo, Name);
		hisProductSubMaterialRatio = hisProductSubMaterialRatioRepo.save(hisProductSubMaterialRatio);
		long id = -1;
		if(hisProductSubMaterialRatio != null && hisProductSubMaterialRatio.getId() > 0) {
			id = hisProductSubMaterialRatio.getId();
		}
		log.debug("saveProductSubMaterialRatio("+hisProductSubMaterialRatio+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}

	@Override
	public int updateHisProductSubMaterialRatio(HisProductSubMaterialRatioVo vo, String Name) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		int record = -1;
		//去db查询当前HisProductSubMaterialRatio是否存在
		HisProductSubMaterialRatio oldHisProductSubMaterialRatio = hisProductSubMaterialRatioRepo.getOne(vo.getId());
		//如果为空，说明不存在，抛出异常
		if(oldHisProductSubMaterialRatio != null) {
			oldHisProductSubMaterialRatio = this.updateEntity(vo, Name, oldHisProductSubMaterialRatio);
			oldHisProductSubMaterialRatio = hisProductSubMaterialRatioRepo.save(oldHisProductSubMaterialRatio);
			if(oldHisProductSubMaterialRatio != null && oldHisProductSubMaterialRatio.getId() > 0) {
				record = 1;
			}
		}
		log.debug("updateHisProductSubMaterialRatio("+vo+","+Name+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}

	@Override
	public void removeHisProductSubMaterialRatio(long id) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		hisProductSubMaterialRatioRepo.deleteById(id);
		log.debug("removeHisProductSubMaterialRatio("+id+") cost "+(System.currentTimeMillis()-start)+" ms.");
	}

	private HisProductSubMaterialRatio addEntity(HisProductSubMaterialRatioVo vo,String Name) {
		HisProductSubMaterialRatio hpsmr = new HisProductSubMaterialRatio();
		hpsmr.setProductCode(vo.getProductCode());
		hpsmr.setSubMaterialCode(vo.getSubMaterialCode());
		hpsmr.setSubMaterialRatio(vo.getSubMaterialRatio());
		hpsmr.setStatus(Dictionary.DATA_STATUS_VALID.getDcode());
		hpsmr.setEnteredBy(Name);
		hpsmr.setEnteredDate(new Date());
		return hpsmr;
	}

	private HisProductSubMaterialRatio updateEntity(HisProductSubMaterialRatioVo vo, String Name, HisProductSubMaterialRatio hpsmr) {
		hpsmr.setProductCode(vo.getProductCode());
		hpsmr.setSubMaterialCode(vo.getSubMaterialCode());
		hpsmr.setSubMaterialRatio(vo.getSubMaterialRatio());
		hpsmr.setLastUpdateBy(Name);
		hpsmr.setLastUpdateDate(new Date());
		return hpsmr;
	}
}

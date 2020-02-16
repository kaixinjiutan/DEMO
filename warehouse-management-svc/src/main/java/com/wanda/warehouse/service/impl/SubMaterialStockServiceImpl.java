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
import com.wanda.warehouse.domain.SubMaterialStockVo;
import com.wanda.warehouse.repository.SubMaterialStockRepository;
import com.wanda.warehouse.repository.entity.SubMaterialStock;
import com.wanda.warehouse.service.SubMaterialStockService;

@Service
public class SubMaterialStockServiceImpl implements SubMaterialStockService {

	private static Logger log = Logger.getLogger(SubMaterialStockServiceImpl.class);
	
	@Autowired
	SubMaterialStockRepository subMaterialStockRepo;
	
	@Override
	public Page<SubMaterialStockVo> findAllSubMaterialStocks(int page, int size) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		Pageable pageable = PageRequest.of(page, size);
		Page<SubMaterialStockVo> subMaterialStocks = subMaterialStockRepo.findAllActiveAsVo(pageable,Dictionary.DATA_STATUS_VALID.getDcode());
		log.debug("findAllSubMaterialStocks("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+subMaterialStocks);
		return subMaterialStocks;
	}
	
	@Override
	public List<SubMaterialStockVo> findAllSubMaterialStocks() {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		List<SubMaterialStockVo> subMaterialStocks = subMaterialStockRepo.findAllActiveAsVo(Dictionary.DATA_STATUS_VALID.getDcode());
		log.debug("findAllSubMaterialStocks() cost "+(System.currentTimeMillis()-start)+" ms. return "+subMaterialStocks);
		return subMaterialStocks;
	}

	@Override
	public SubMaterialStockVo findSubMaterialStockById(long id) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		SubMaterialStockVo subMaterialStock = subMaterialStockRepo.findByIdAsVo(id);
		log.debug("findSubMaterialStockById("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+subMaterialStock);
		return subMaterialStock;
	}

	@Override
	public long addSubMaterialStock(SubMaterialStockVo vo, String Name) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		SubMaterialStock subMaterialStock = this.addEntity(vo, Name);
		subMaterialStock = subMaterialStockRepo.save(subMaterialStock);
		long id = -1;
		if(subMaterialStock != null && subMaterialStock.getId() > 0) {
			id = subMaterialStock.getId();
		}
		log.debug("saveSubMaterialStock("+subMaterialStock+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}

	@Override
	public int updateSubMaterialStock(SubMaterialStockVo vo, String Name) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		int record = -1;
		//去db查询当前SubMaterialStock是否存在
		SubMaterialStock oldSubMaterialStock = subMaterialStockRepo.getOne(vo.getId());
		//如果为空，说明不存在，抛出异常
		if(oldSubMaterialStock != null) {
			oldSubMaterialStock = this.updateEntity(vo, Name, oldSubMaterialStock);
			oldSubMaterialStock = subMaterialStockRepo.save(oldSubMaterialStock);
			if(oldSubMaterialStock != null && oldSubMaterialStock.getId() > 0) {
				record = 1;
			}
		}
		log.debug("updateSubMaterialStock("+vo+","+Name+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}

	@Override
	public void removeSubMaterialStock(long id) {
		// TODO 自动生成的方法存根
		long start = System.currentTimeMillis();
		subMaterialStockRepo.deleteById(id);
		log.debug("removeSubMaterialStock("+id+") cost "+(System.currentTimeMillis()-start)+" ms.");
	}

	private SubMaterialStock addEntity(SubMaterialStockVo vo,String Name) {
		SubMaterialStock sms = new SubMaterialStock();
		sms.setSubMaterialCode(vo.getSubMaterialCode());
		sms.setStockModifyStatus(vo.getStockModifyStatus());
		sms.setOrderNo(vo.getOrderNo());
		sms.setPurchaser(vo.getPurchaser());
		sms.setQuantity(vo.getQuantity());
		sms.setStatus(Dictionary.DATA_STATUS_VALID.getDcode());
		sms.setEnteredBy(Name);
		sms.setEnteredDate(new Date());
		return sms;
	}

	private SubMaterialStock updateEntity(SubMaterialStockVo vo, String Name, SubMaterialStock sms) {
		sms.setSubMaterialCode(vo.getSubMaterialCode());
		sms.setStockModifyStatus(vo.getStockModifyStatus());
		sms.setOrderNo(vo.getOrderNo());
		sms.setPurchaser(vo.getPurchaser());
		sms.setQuantity(vo.getQuantity());
		sms.setLastUpdateBy(Name);
		sms.setLastUpdateDate(new Date());
		return sms;
	}
}

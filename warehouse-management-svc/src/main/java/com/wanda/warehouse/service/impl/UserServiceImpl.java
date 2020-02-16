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
import com.wanda.warehouse.domain.UserVo;
import com.wanda.warehouse.repository.UserRepository;
import com.wanda.warehouse.repository.entity.User;
import com.wanda.warehouse.service.UserService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	private static Logger log = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository userRepo;

	@Override
	public Page<UserVo> findAllUsers(int page, int size) {
		long start = System.currentTimeMillis();
		Pageable pageable = PageRequest.of(page, size);
		Page<UserVo> users = userRepo.findAllActiveAsVo(pageable,Dictionary.DATA_STATUS_VALID.getDcode());
		log.debug("findAllUsers("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+users);
		return users;
	}
	
	@Override
	public List<UserVo> findAllUsers() {
		long start = System.currentTimeMillis();
		List<UserVo> users = userRepo.findAllActiveAsVo(Dictionary.DATA_STATUS_VALID.getDcode());
		log.debug("findAllUsers() cost "+(System.currentTimeMillis()-start)+" ms. return "+users);
		return users;
	}

	@Override
	public UserVo findUserByCode(String userCode) {
		long start = System.currentTimeMillis();
		UserVo user = userRepo.findByCodeAsVo(userCode);
		log.debug("findUserById("+user+") cost "+(System.currentTimeMillis()-start)+" ms. return "+user);
		return user;
	}

	@Override
	public UserVo findUserById(long id) {
		long start = System.currentTimeMillis();
		UserVo user = userRepo.findByIdAsVo(id);
		log.debug("findUserById("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+user);
		return user;
	}
	
	@Override
	public UserVo findUserByCode(String userCode,String password) {
		long start = System.currentTimeMillis();
		UserVo user = userRepo.findByCodePwdAsVo(userCode,password);
		log.debug("findUserById("+user+") cost "+(System.currentTimeMillis()-start)+" ms. return "+user);
		return user;
	}

	@Override
	public long addUser(UserVo vo,String userName) {
		long start = System.currentTimeMillis();
		User user = this.addEntity(vo, userName);
		user = userRepo.save(user);
		long id = -1;
		if(user != null && user.getId() > 0) {
			id = user.getId();
		}
		log.debug("saveUser("+user+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}
	
	@Transactional
	@Override
	public int updateUser(UserVo vo, String userName) {
		long start = System.currentTimeMillis();
		int record = -1;
		//去db查询当前用户是否存在
		User oldUser = userRepo.getOne(vo.getId());
		//如果为空，说明不存在，抛出异常
		if(oldUser != null) {
			oldUser = this.updateEntity(vo, userName, oldUser);
			oldUser = userRepo.save(oldUser);
			if(oldUser != null && oldUser.getId() > 0) {
				record = 1;
			}
		}
		log.debug("updateUser("+vo+","+userName+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}

	@Transactional
	@Override
	public void removeUser(long id) {
		long start = System.currentTimeMillis();
		userRepo.deleteById(id);
		log.debug("removeUser("+id+") cost "+(System.currentTimeMillis()-start)+" ms.");
	}
	
	private User addEntity(UserVo vo,String userName) {
		User u = new User();
		u.setUserCode(vo.getUserCode());
		u.setPassword(vo.getPassword());
		u.setStatus(Dictionary.DATA_STATUS_VALID.getDcode());
		u.setEnteredBy(userName);
		u.setEnteredDate(new Date());
		return u;
	}

	private User updateEntity(UserVo vo, String userName, User u) {
		if(vo.getPassword() != null && !vo.getPassword().isEmpty()) {
			u.setPassword(vo.getPassword());
		}
		if(vo.getStatus() > 0) {
			u.setStatus(vo.getStatus());
		}
		u.setLastUpdateBy(userName);
		u.setLastUpdateDate(new Date());
		return u;
	}

}

package com.wanda.warehouse.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wanda.warehouse.domain.UserVo;

public interface UserService {
	
	Page<UserVo> findAllUsers(int page, int size);
	
	List<UserVo> findAllUsers();
	
	UserVo findUserById(long id);
	
	long addUser(UserVo vo,String userName);
	
	int updateUser(UserVo vo,String userName);
	
	void removeUser(long id);

	UserVo findUserByCode(String userCode, String password);
	
	UserVo findUserByCode(String userCode);

}

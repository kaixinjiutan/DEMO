/*
 * Copyright 2011-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wanda.warehouse.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wanda.warehouse.domain.UserVo;
import com.wanda.warehouse.repository.entity.User;



/**
 * Repository to manage {@link Account} instances.
 *
 * @author Oliver Gierke
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("select new com.wanda.warehouse.domain.UserVo(u.id, u.userCode, u.password, u.status) from User u where status = ?1 order by id desc")
	Page<UserVo> findAllActiveAsVo (Pageable pageable,int status);
	
	@Query("select new com.wanda.warehouse.domain.UserVo(u.id, u.userCode, u.password, u.status) from User u where u.id = ?1")
	UserVo findByIdAsVo(long id);
	
	@Query("select new com.wanda.warehouse.domain.UserVo(u.id, u.userCode, u.password, u.status) from User u where u.userCode = ?1 and u.password = ?2")
	UserVo findByCodePwdAsVo(String userCode,String password);
	
	@Query("select new com.wanda.warehouse.domain.UserVo(u.id, u.userCode, u.password, u.status) from User u where u.userCode = ?1")
	UserVo findByCodeAsVo(String userCode);
	
	@Query("select new com.wanda.warehouse.domain.UserVo(u.id, u.userCode, u.password, u.status) from User u where status = ?1 and userCode <> 'admin' order by id desc")
	List<UserVo> findAllActiveAsVo (int status);
	
}

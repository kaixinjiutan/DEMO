package com.wanda.warehouse.controller;

import java.util.List;
import java.util.Objects;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.warehouse.domain.Dictionary;
import com.wanda.warehouse.domain.UserVo;
import com.wanda.warehouse.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@Api(tags = "用户相关操作")
public class UserController {
	
	private static Logger log = Logger.getLogger(UserController.class);
	
	@Autowired UserService userService;
	
	@ApiOperation(value = "查询当前系统的所有用户", notes = "查询当前系统所有的使用用户")
	@GetMapping("/page/{page}/{size}")
	public Page<UserVo> getUsers(@RequestHeader String userCode, @PathVariable int page, @PathVariable int size) {
		long start = System.currentTimeMillis();
		Page<UserVo> voPage = null;
		try {
			voPage = userService.findAllUsers(page, size);
		}catch(Exception e) {
			log.error(userCode +" getUsers("+page+","+size+") error.",e);
		}
		log.info(userCode +" getUsers("+page+","+size+") cost "+(System.currentTimeMillis()-start)+" ms. return "+voPage);
		return voPage;
	}
	
	@ApiOperation(value = "查询当前系统的所有用户", notes = "查询当前系统所有的使用用户")
	@GetMapping("/list")
	public List<UserVo> getUsers(@RequestHeader String userCode) {
		long start = System.currentTimeMillis();
		List<UserVo> voPage = null;
		try {
			voPage = userService.findAllUsers();
		}catch(Exception e) {
			log.error(userCode +" getUsers() error.",e);
		}
		log.info(userCode +" getUsers() cost "+(System.currentTimeMillis()-start)+" ms. return "+voPage);
		return voPage;
	}
	
	@ApiOperation(value = "新增一个系统用户", notes = "新增一个系统用户，系统用户用于登陆系统管理订单，产品以及物料信息")
	@PostMapping("/add")
	public long addUser(@RequestHeader String userCode, @RequestBody UserVo vo) {
		long start = System.currentTimeMillis();
		long id = -1;
		try {
			//先查询新增的用户是否在DB中已经存在了，如果存在，说明此用户曾经被删除过，此处调用更新方法用新传入的对象更新原来的记录
			UserVo oldVo = userService.findUserByCode(vo.getUserCode());
			if(Objects.nonNull(oldVo)) {
				vo.setId(oldVo.getId());
				vo.setStatus(Dictionary.DATA_STATUS_VALID.getDcode());
				//用户名不变，但是密码使用最新的
				this.userService.updateUser(vo, userCode);
				id = vo.getId();
			}else {
				id = userService.addUser(vo, userCode);
			}
		}catch(Exception e) {
			log.error(userCode + " addUser("+vo+","+userCode+") error.",e);
		}
		log.info(userCode + " addUser("+vo+","+userCode+") cost "+(System.currentTimeMillis()-start)+" ms. return "+id);
		return id;
	}
	
	@ApiOperation(value = "更新一个系统用户", notes = "更新一个系统用户，系统用户用于登陆系统管理订单，产品以及物料信息，注意userCode一旦确定不允许更改，如需更改则先删除现有记录，重新添加")
	@PostMapping("/update")
	public int updateUser(@RequestHeader String userCode, @RequestBody UserVo vo) {
		long start = System.currentTimeMillis();
		int record = -1;
		try {
			//userCode合法性检查
			if(userCode == null || userCode.isEmpty()) {
				throw new Exception("userCode is null userCode is empty.");
			}
			//vo合法性检查
			if(vo == null) {
				throw new Exception("vo is null.");
			}
			if(vo.getId() < 1) {
				throw new Exception("id is less than 1.");
			}
			UserVo voUser = userService.findUserById(vo.getId());
			voUser.setUserCode(vo.getUserCode());
			voUser.setPassword(vo.getPassword());
			record = userService.updateUser(voUser, userCode);
		}catch(Exception e) {
			log.error(userCode+" updateUser("+vo+") error:"+e.getMessage(),e);
		}
		log.info(userCode+" updateUser("+vo+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}
	
	@ApiOperation(value = "逻辑删除一个系统用户", notes = "删除一个系统用户，此处逻辑删除")
	@DeleteMapping("/delete/{id}")
	public int logicDeleteUser(@RequestHeader String userCode, @PathVariable long id) {
		long start = System.currentTimeMillis();
		int record = -1;
		try {
			if(id < 1) {
				throw new Exception("id is less than 1.");
			}
			UserVo vo = this.userService.findUserById(id);
			vo.setStatus(Dictionary.DATA_STATUS_INVALID.getDcode());
			record = userService.updateUser(vo, userCode);
		}catch(Exception e) {
			log.error(userCode+" deleteUser("+id+") error:"+e.getMessage(),e);
		}
		log.info(userCode+" deleteUser("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
		return record;
	}
	
//	@ApiOperation(value = "物理删除一个系统用户", notes = "删除一个系统用户，此处逻辑删除")
//	@DeleteMapping("/delete/{id}")
//	public int deleteUser(@RequestHeader String userCode, @PathVariable long id) {
//		long start = System.currentTimeMillis();
//		int record = -1;
//		try {
//			if(id < 1) {
//				throw new Exception("id is less than 1.");
//			}
//			this.userService.removeUser(id);
//			record = 1;
//		}catch(Exception e) {
//			log.error(userCode+" deleteUser("+id+") error:"+e.getMessage(),e);
//		}
//		log.info(userCode+" deleteUser("+id+") cost "+(System.currentTimeMillis()-start)+" ms. return "+record);
//		return record;
//	}
	
	@ApiOperation(value = "User Login", notes = "User login", response = UserVo.class)
	@PostMapping("/login")
	public UserVo Login(@RequestBody UserVo loginUser){
		long start = System.currentTimeMillis();
		String userCode = loginUser.getUserCode();
		String password = loginUser.getPassword();
		UserVo user = null;
		try {
			user = userService.findUserByCode(userCode, password);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			user = null;
		}
		log.info("login (" + userCode + ") cost " + (System.currentTimeMillis() - start) + " ms, return ["+user+"]!");
		return user;
	}
}

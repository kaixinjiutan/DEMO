package com.wanda.warehouse.domain;

public class UserVo {

	private long id;
	private String userCode;
	private String password;
	private int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserVo [id=" + id + ", userCode=" + userCode + ", password=" + password + ", status=" + status + "]";
	}

	public UserVo(long id, String userCode, String password, int status) {
		super();
		this.id = id;
		this.userCode = userCode;
		this.password = password;
		this.status = status;
	}

}

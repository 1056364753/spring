package com.lb.entity;

import java.util.Date;

public class SysUser {
	
	private int id;
	
    /** 用户账号 */
    private String userName;
    
    /** 用户密码 盐加密*/
    private String password;
    
    /** 盐值 */
    private String salt;
    
    private Date createDateTime;

	/** 用户新密码 base64 加密*/
	private String newPassWord;

	/** 用户旧密码  明文*/
	private String oldPassWord;


	public String getNewPassWord() {
		return newPassWord;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}

	public String getOldPassWord() {
		return oldPassWord;
	}

	public void setOldPassWord(String oldPassWord) {
		this.oldPassWord = oldPassWord;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "SysUser [id=" + id + ", userName=" + userName + ", password=" + password + ", salt=" + salt + "]";
	}

	public SysUser() {
		super();
		// TODO Auto-generated constructor stub
	}




    
    

}

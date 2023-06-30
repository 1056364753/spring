package com.lb.service.impl;

import java.util.Date;

import com.lb.common.UUID;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lb.entity.SysUser;
import com.lb.mapper.SysUserMapper;
import com.lb.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService{

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Override
	public SysUser selectUserByUserName(String username,String password) {
		
		// TODO Auto-generated method stub
		return sysUserMapper.selectUserByUserName(username,password);
	}

	@Override
	public int resign(SysUser user) {
		
		SecureRandomNumberGenerator a = new SecureRandomNumberGenerator();
		String salt = ByteSource.Util.bytes(a.nextBytes().toHex()).toHex();
		String newpass = new SimpleHash("SHA-1",user.getPassword(), salt, 2).toHex();
		//base64加密
		byte[] base64Encrypt = UUID.base64Encrypt(user.getPassword());
		String toHexString = HexUtils.toHexString(base64Encrypt);

		user.setNewPassWord(toHexString);
		user.setOldPassWord(user.getPassword());
		user.setSalt(salt);
		user.setPassword(newpass);
		user.setCreateDateTime(new Date());

		return sysUserMapper.resign(user);
	}

	@Override
	public SysUser selectUserByUserNames(String name) {
		return sysUserMapper.selectUserByUserNames(name);
	}

}

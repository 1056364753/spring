package com.lb.service;

import org.springframework.stereotype.Service;

import com.lb.entity.SysUser;

@Service
public interface SysUserService {

	SysUser selectUserByUserName(String username,String password);

	int resign(SysUser sysUser);

	SysUser selectUserByUserNames(String name);
}

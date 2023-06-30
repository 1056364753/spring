package com.lb.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.lb.entity.SysUser;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper {

	SysUser selectUserByUserName(@Param("username") String username, @Param("password")String password);

	int resign(SysUser user);

	SysUser selectUserByUserNames(String name);
}

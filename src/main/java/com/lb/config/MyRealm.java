package com.lb.config;


import com.lb.common.UUID;
import com.lb.service.impl.SysUserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.lb.entity.SysUser;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm
 *
 */
public class MyRealm extends AuthorizingRealm{
	

	@Autowired
	private SysUserServiceImpl userServices;
	
	/**
	 * 权限认证--登录
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String name=(String)token.getPrincipal();//用户名  UsernamePasswordTokenr的第一个参数  name
		UsernamePasswordToken token1 = (UsernamePasswordToken) token;
		String password=String.valueOf(token1.getPassword());//密码

		//base64加密
		byte[] base64Encrypt = UUID.base64Encrypt(password);
		String toHexString = HexUtils.toHexString(base64Encrypt);

		SysUser user=userServices.selectUserByUserName(name,toHexString);
		if(user!=null){
			AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(
					user.getUserName(),	 //拿到用户名 ,登录账号
					user.getPassword(),   //拿到数据库的密码
					ByteSource.Util.bytes(user.getSalt()), //盐加密
					getName()
					);
			System.out.println(ByteSource.Util.bytes(user.getSalt()));
			return authcInfo;
		}else{
			return null;				
		}
	}



	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}
}

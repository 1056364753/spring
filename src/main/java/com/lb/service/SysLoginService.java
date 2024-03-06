package com.lb.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lb.common.RedisCache;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysLoginService
{



    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private TokenService tokenService;

    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     * @throws Exception 
     */
    public Map<String, Object> login(String username, String password, String code, String uuid) throws Exception
    {
    	Map<String, Object> result = new HashMap<String, Object>();
    	
        // 验证码校验
        validateCaptcha(username, code, uuid);
        
//        // 用户验证
//        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
//            authentication = authenticationManager
//                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            
            UsernamePasswordToken token=new UsernamePasswordToken(username,password);
			//获取 subject
			Subject subject=SecurityUtils.getSubject();
			//执行登陆  shiro的登陆
			subject.login(token);
			//执行登陆  shiro的登陆
        }catch (UnknownAccountException e) {
        	result.put("code", 9999);
			result.put("success", false);
			result.put("msg","用户名不存在");
			return result;
		}catch (IncorrectCredentialsException e) {
			result.put("code", 8888);
			result.put("success", false);
			result.put("msg","密码错误");
			return result;
		}
     // 生成令牌
        String token = tokenService.createToken();
        result.put("code",200);
        result.put("msg", "登录成功！");
        result.put("token", token);
        // 生成token
        return result;
    }

    /**
     * 校验验证码
     * 
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     * @throws Exception 
     */
    public void validateCaptcha(String username, String code, String uuid) throws Exception
    {
        String verifyKey = "Code:" + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        if (captcha == null || !code.equalsIgnoreCase(captcha)){
            System.out.println("yes");
            throw new Exception("校验验证码错误！!!");
        }else {
            System.out.println("校验验证码成功！123213");
            redisCache.deleteObject(verifyKey);
        }
    }


}

package com.lb.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Producer;
import com.lb.common.Base64;
import com.lb.common.IdUtils;
import com.lb.common.RedisCache;
import com.lb.entity.LoginBody;
import com.lb.entity.RuoYiConfig;
import com.lb.entity.SysUser;
import com.lb.service.SysLoginService;
import com.lb.service.SysUserService;

/**
 * 验证码操作处理
 * 
 * @author ruoyi
 */
@RestController
public class CaptchaController
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private SysLoginService loginService;
    
    @Autowired
    private SysUserService userService;
    
    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public Map<String, Object> getCode(HttpServletResponse response) throws IOException
    {
    	Map<String, Object> ajax = new HashMap<String, Object>();

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = "Code:"+uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = RuoYiConfig.getCaptchaType();
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, 2, TimeUnit.MINUTES);
        String configValue =redisCache.getCacheObject(verifyKey);
        System.out.println(configValue);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
        	ajax.put("code", 9999);
        	ajax.put("msg", e.getMessage());
            return ajax;
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }
    
    
    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginBody loginBody)
    {
    	Map<String, Object> ajax = new HashMap<String, Object>();
		try {
			ajax = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
			        loginBody.getUuid());
		} catch (Exception e) {
			ajax.put("code", 9999);
	    	ajax.put("msg", "异常报错！");
	    	return ajax;
		}
        return ajax;
    }
    
    /**
     * 注册方法
     * 
     * @param sysUser 注册信息
     * @return 结果
     */
    @PostMapping("/resign")
    public Map<String, Object> resign(@RequestBody SysUser sysUser)
    {
    	Map<String, Object> ajax = new HashMap<String, Object>();
        // 生成令牌

		try {
			userService.resign(sysUser);
		} catch (Exception e) {
			ajax.put("code", 9999);
	    	ajax.put("msg", e.getMessage());
	    	return ajax;
		}
		ajax.put("code", 200);
    	ajax.put("msg", "注册成功！");
        return ajax;
    }
    
    /**
	 * 注销
	 *  /user/logout
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public Map<String, Object> logout()throws Exception{
		Map<String, Object> ajax = new HashMap<String, Object>();
		SecurityUtils.getSubject().logout(); //shiro的退出
		ajax.put("code", 200);
    	ajax.put("msg", "退出登录！");
        return ajax;
	}

    /**
     * 注销
     *  /user/logout http://localhost:8087/checkWeather?city=Beijing
     * @throws Exception
     */
    @RequestMapping("/checkWeather")
    public Map<String, Object> checkWeather(String city)throws Exception{
        Map<String, Object> ajax = new HashMap<String, Object>();
        String apiKey = "bed05f065beed344046407b73c4855c7";
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            System.out.println(response.toString());
            ajax.put("code", 200);
            ajax.put("msg", response.toString());
            // Parse the JSON response and extract the weather information
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ajax;
    }
}

package com.lb.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.lb.common.ResultCode;

/**
 * @author bin
 *	说明：
 */
@Service
public class LogService {

    
	/**
	 * @param name
	 * @param 文件下载
	 * @throws Exception
	 */
	public void logDownload(String name, HttpServletResponse response) throws Exception {
        File file = new File("logs" + File.separator + name);
		try {
		    if (!file.exists()) {
		        throw new Exception(name + "文件不存在");
		    }
		} catch (Exception e) {
		}

        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + name);

        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(file);
                      BufferedInputStream bis = new BufferedInputStream(fis)) {

            OutputStream os = response.getOutputStream();

            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        }
    }
    
    
    /**
     * @param file
     * @return 单文件上传服务器
     * @throws Exception
     */
    public ResultCode logUpload(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new Exception("未选择需上传的日志文件");
        }
        System.out.println(file.getOriginalFilename());

        
        String filePath = new File("logs_app").getAbsolutePath();
        File fileUpload = new File(filePath);
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }

        fileUpload = new File(filePath, file.getOriginalFilename());
        if (fileUpload.exists()) {
            throw new Exception("上传的日志文件已存在");
        }

        try {
            file.transferTo(fileUpload);

            return ResultCode.SUCCESS;
        } catch (IOException e) {
            throw new Exception("上传日志文件到服务器失败：" + e.toString());
        }
    }
    
    /**
     * @param request
     * @return 多文件上传服务器
     * @throws Exception
     */
    public ResultCode logUploads(HttpServletRequest request) throws Exception {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");

        for (MultipartFile file : files) {
            logUpload(file);
        }

        return ResultCode.SUCCESS;
    }
}
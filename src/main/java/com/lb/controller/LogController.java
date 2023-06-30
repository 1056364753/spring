package com.lb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lb.common.ResultCode;
import com.lb.service.FtpService;
import com.lb.service.LogService;

import cn.hutool.core.io.FileUtil;

@RestController
@Scope("prototype")
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 下载日志接口
     *
     * @param name
     * @param response
     * @throws Exception
     */
    @GetMapping(value = "/download/{name}")
    public void logDownload(@PathVariable String name, HttpServletResponse response) throws Exception {
        logService.logDownload(name, response);
    }
    
    /**
     * 上传日志接口
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/upload")
    public ResultCode logUpload(@RequestParam("file") MultipartFile file) throws Exception {
        return logService.logUpload(file);
    }
    
    /**
     * 批量上传日志接口
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/uploads")
    public ResultCode logUploads(HttpServletRequest request) throws Exception {
        return logService.logUploads(request);
    }
    
    @Autowired
    private FtpService ftpClientService;

    @PostMapping(value = "/test")
    public boolean test1(){
        try {
            InputStream inputStream = new FileInputStream(new File("G:\\亚信帐管\\SONAR计划\\广研CRM产品\\CMC产品研发编码规范--禁止性案例库v1.0（更新了规则）.docx"));
            ftpClientService.uploadFile(inputStream,"10.docx","a/");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
		return true;
    }
    
    @PostMapping(value = "/test1")
    public boolean test2() throws IOException{
        try {
            InputStream inputStream = ftpClientService.downloadFile("10.docx");
            FileUtil.writeFromStream(inputStream, "E:\\110.docx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
		return true;
    }
    
    /**
     * 批量上传接口
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/ftp")
    public List<Map> Upload(HttpServletRequest request) throws Exception {       
         return ftpClientService.Upload(request);
    }
    
    /**
     * 单文件上传接口
     *
     * @param request
     * @return http://localhost:8087/log-upload.html
     * @throws Exception
     */
    @RequestMapping(value = "/ftp1",method = RequestMethod.POST)
    public Map Upload1( MultipartFile file) throws Exception {      
         return ftpClientService.Upload1(file);
    }
    
    /**
     * 文件下载接口
     *
     * @param request
     * @return  http://localhost:8087/log/ftp2?savePath=E:\
     * @throws Exception
     */
    @PostMapping(value = "/ftp2")
    public String downLoad(String savePath) throws Exception {      
         return ftpClientService.downLoad(savePath);
    }
}
package com.lb.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface FtpService {
	
	 public String readFileToBase64(String remoteFileName,String remoteDir);
	    
	 public void download(String remoteFileName,String localFileName,String remoteDir);
	    
	 public boolean uploadFile(InputStream inputStream, String originName, String remoteDir);

	public InputStream downloadFile(String filename) throws IOException;
	
	public List<Map> Upload(HttpServletRequest request) throws Exception;
	
	public Map Upload1(MultipartFile file) throws Exception;

	public String downLoad(String savePath);

}

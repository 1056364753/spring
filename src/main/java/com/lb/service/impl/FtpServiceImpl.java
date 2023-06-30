package com.lb.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lb.entity.ApplicationEntity;
import com.lb.service.FtpService;

@Service
public class FtpServiceImpl implements FtpService {
	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
    ApplicationEntity applicationEntity = new ApplicationEntity();
	
    FTPClient ftpClient = new FTPClient();

     
    @Override
    public String readFileToBase64(String remoteFileName,String remoteDir) {
        if (ftpClient == null){
            return null;
        }

        String base64 = "";
        InputStream inputStream = null;

        try {
            ftpClient.changeWorkingDirectory(remoteDir);
            FTPFile[] ftpFiles = ftpClient.listFiles(remoteDir);
            Boolean flag = false;
            //遍历当前目录下的文件，判断要读取的文件是否在当前目录下
            for (FTPFile ftpFile:ftpFiles){
                if (ftpFile.getName().equals(remoteFileName)){
                    flag = true;
                }
            }

            if (!flag){
                LOGGER.error("directory：{}下没有 {}",remoteDir,remoteFileName);
                return null;
            }
            //获取待读文件输入流
            inputStream = ftpClient.retrieveFileStream(remoteDir+remoteFileName);

            //inputStream.available() 获取返回在不阻塞的情况下能读取的字节数，正常情况是文件的大小
            byte[] bytes = new byte[inputStream.available()];

            inputStream.read(bytes);//将文件数据读到字节数组中
//            BASE64Encoder base64Encoder = new BASE64Encoder();
//            base64 = base64Encoder.encode(bytes);//将字节数组转成base64字符串
            LOGGER.info("read file {} success",remoteFileName);
            ftpClient.logout();
        } catch (IOException e) {
            LOGGER.error("read file fail ----->>>{}",e.getCause());
            return null;
        }finally {
            if (ftpClient.isConnected()){
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    LOGGER.error("disconnect fail ------->>>{}",e.getCause());
                }
            }

            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("inputStream close fail -------- {}",e.getCause());
                }
            }

        }

        return base64;
    }

    @Override
    public void download(String remoteFileName, String localFileName,String remoteDir) {

    }

    /**
     *  上传文件
     * @param inputStream 待上传文件的输入流
     * @param originName 文件保存时的名字
     * @param remoteDir 文件要存放的目录
     */
    @Override
    public boolean uploadFile(InputStream inputStream, String originName, String remoteDir){
        if (ftpClient == null){
            return false;
        }
        try {
        	ftpClient1();//建立连接
        	ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件
            ftpClient.changeWorkingDirectory(remoteDir);//进入到文件保存的目录
            Boolean isSuccess = ftpClient.storeFile(originName,inputStream);//保存文件
            LOGGER.info("{}---》上传成功！",originName);
            ftpClient.logout();
            return true;
        } catch (IOException e) {
            LOGGER.error("{}---》上传失败！",originName);
            return false;
        }finally {
            if (ftpClient.isConnected()){
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    LOGGER.error("disconnect fail ------->>>{}",e.getCause());
                }
            }
        }
    }
    
    public InputStream downloadFile(String filename)
            throws IOException {
        InputStream in = null;
        try {

            // 建立连接
        	ftpClient1();
            ftpClient.enterLocalPassiveMode();
            // 设置传输二进制文件
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            int reply = ftpClient.getReplyCode();
            ftpClient.changeWorkingDirectory("a\\");

            // ftp文件获取文件
            in = ftpClient.retrieveFileStream(filename);

        } catch (FTPConnectionClosedException e) {
            System.out.println("ftp连接被关闭！");
            throw e;
        } catch (Exception e) {
            System.out.println("ERR : upload file " + filename + " from ftp : failed!");

        }
        return in;
    }
    
    private void ftpClient1(){
    	if (ftpClient.isConnected()){
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                LOGGER.error("disconnect fail ------->>>{}",e.getCause());
            }
        }
      ftpClient.setConnectTimeout(1000*30);//设置连接超时时间
//      ftpClient.setControlEncoding("UTF-8");//设置ftp字符集
      ftpClient.enterLocalPassiveMode();//设置被动模式，文件传输端口设置
      try {
          ftpClient.setDefaultPort(applicationEntity.getFtpPort());
          ftpClient.connect(applicationEntity.getFtpHost(),applicationEntity.getFtpPort());
          ftpClient.login(applicationEntity.getFtpUsername(),applicationEntity.getFtpPassword());
          int replyCode = ftpClient.getReplyCode();
          if (!FTPReply.isPositiveCompletion(replyCode)){
              ftpClient.disconnect();
              LOGGER.error("未连接到FTP，用户名或密码错误!");
          }else {
        	  LOGGER.info("FTP连接成功!");
          }
      } catch (SocketException socketException) {
    	  LOGGER.error("FTP的IP地址可能错误，请正确配置!");
      } catch (IOException ioException) {
          ioException.printStackTrace();
          LOGGER.error("FTP的端口错误,请正确配置!");
      }
  }

	@Override
	public List<Map> Upload(HttpServletRequest request) throws Exception {
		List<Map> list = new ArrayList<Map>();
		Map map = new HashMap();
       try {
    	List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        ftpClient1();//建立连接
        String b = "GBK";
        ftpClient.changeWorkingDirectory(applicationEntity.getFtpFilepath());//进入到文件保存的目录
        if(FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
     	   b = "UTF-8";
        }
        ftpClient.setControlEncoding(b);//设置ftp字符集

    	ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件
        ftpClient.changeWorkingDirectory(applicationEntity.getFtpFilepath());//进入到文件保存的目录
        for (MultipartFile file : files) {
        	map = logUpload(file);
        	list.add(map);
        }
       }catch (Exception e) {
    	   LOGGER.error(e.getMessage());
    	   return list;
       }finally {
           if (ftpClient.isConnected()){
               try {
                   ftpClient.logout();
                   ftpClient.disconnect();
               } catch (IOException e) {
                   LOGGER.error("disconnect fail ------->>>{}",e.getCause());
               }
           }
	}
       
        return list	;
    }
	
    /**
     * @param file
     * @return 单文件上传服务器
     * @throws Exception
     */
    public Map logUpload(MultipartFile file) throws Exception {
    	Map map = new HashMap();
        if (file == null || file.isEmpty()) {
            throw new Exception("未选择需上传的文件");
        }else if (ftpClient == null){
        	throw new Exception("未连接ftp服务");
        }
        try {
        	Boolean isSuccess = ftpClient.storeFile(new String(file.getOriginalFilename().getBytes("GBK"),"ISO-8859-1"),file.getInputStream());//保存文件
//        	Boolean isSuccess = ftpClient.storeFile(new String(a.getBytes("GBK"),"iso-8859-1"),file.getInputStream());//保存文件
        	LOGGER.info("{}---》上传成功！",file.getOriginalFilename());
        	map.put("code",200);
        	map.put("message",file.getOriginalFilename()+"--》上传成功！");
        } catch (IOException e) {
            LOGGER.error("{}---》上传失败！",file.getOriginalFilename()+e.getMessage());
            map.put("code",9999);
            map.put("message",file.getOriginalFilename()+"--》上传失败！原因："+e.getMessage());
        }
		return map;
    }
    
	@Override
	public Map Upload1(MultipartFile file) throws Exception {
		Map map = new HashMap();
	       try {
	           ftpClient1();//建立连接
	           String b = "GBK";
	           ftpClient.changeWorkingDirectory(applicationEntity.getFtpFilepath());//进入到文件保存的目录
	           if(FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
	        	   b = "UTF-8";
	           }
	           ftpClient.setControlEncoding(b);//设置ftp字符集
	       		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件
	       		map =logUpload(file);
	          }catch (Exception e) {
	       	   LOGGER.error(e.getMessage());
	          }finally {
	              if (ftpClient.isConnected()){
	                  try {
	                      ftpClient.logout();
	                      ftpClient.disconnect();
	                  } catch (IOException e) {
	                      LOGGER.error("disconnect fail ------->>>{}",e.getCause());
	                  }
	              }
	   	}
	          
	           return map;
	       }

//	@Override
//	public String downLoad(String savePath) {
//		 try {
//	           ftpClient1();//建立连接
//	           String b = "GBK";
//	           ftpClient.changeWorkingDirectory(applicationEntity.getFtpFilepath());//进入到文件保存的目录
//	           if(FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
//	        	   b = "UTF-8";
//	           }
//	           ftpClient.setControlEncoding(b);//设置ftp字符集
//		       ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件
//		       String[] fs = ftpClient.listNames();
//	           // 判断该目录下是否有文件
//	           if (fs == null || fs.length == 0) {
//	                return "该路径下没有文件！";
//	            }
//	            for (String ff : fs) {
//	                String ftpName = new String(ff.getBytes("GBK"), "ISO-8859-1");
//	                File file = new File(savePath + '/' + ftpName);
//	                try (OutputStream os = new FileOutputStream(file)) {
//	                    ftpClient.retrieveFile(ff, os);
//	                } catch (Exception e) {
//	                	throw new Exception(e.getMessage());
//	                }
//	            }
//	          }catch (Exception e) {
//	       	   LOGGER.error(e.getMessage());
//	       	   return e.getMessage();
//	          }finally {
//	              if (ftpClient.isConnected()){
//	                  try {
//	                      ftpClient.logout();
//	                      ftpClient.disconnect();
//	                  } catch (IOException e) {
//	                      LOGGER.error("disconnect fail ------->>>{}",e.getCause());
//	                  }
//	              }
//	   	}
//	          
//	           return "文件下载成功！！";
//	       
//	}
	
	/**
	 * 从FTP路径下载全部文件
	 */
	@Override
	public String downLoad(String savePath) {
		 try {
	           ftpClient1();//建立连接
	           String b = "GBK";
	           ftpClient.changeWorkingDirectory(applicationEntity.getFtpFilepath());//进入到文件保存的目录
	           if(FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
	        	   b = "UTF-8";
	           }
	           ftpClient.setControlEncoding(b);//设置ftp字符集
		       ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件
		       FTPFile[] files = ftpClient.listFiles();
		       for(FTPFile file1:files) {
		    	   String ff = file1.getName();
	                File file = new File(savePath + '/' + ff);
	                try (OutputStream os = new FileOutputStream(file)) {
	                    ftpClient.retrieveFile(ff, os);
	                    LOGGER.info("下载文件："+file1.getName()+"成功!");
	                } catch (Exception e) {
	                	throw new Exception(e.getMessage());
	                }
		       }		      
	          }catch (Exception e) {
	       	   LOGGER.error(e.getMessage());
	       	   return e.getMessage();
	          }finally {
	              if (ftpClient.isConnected()){
	                  try {
	                      ftpClient.logout();
	                      ftpClient.disconnect();
	                  } catch (IOException e) {
	                      LOGGER.error("disconnect fail ------->>>{}",e.getCause());
	                  }
	              }
	   	}
	          
	           return "文件下载成功！！";
	       
	}
}
    
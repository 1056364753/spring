package com.lb.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class ApplicationEntity {

    /**
     * ftp站点
     */
    @Value("${ftp.host}")
    private String ftpHost;
    /**
     * ftp端口号
     */
    @Value("${ftp.port}")
    private int ftpPort;
    /**
     * ftp访问用户名
     */
    @Value("${ftp.username}")
    private String ftpUsername;
    /**
     * ftp访问密码
     */
    @Value("${ftp.password}")
    private String ftpPassword;
    /**
     * ftp访问文件路径
     */
    @Value("${ftp.filepath}")
    private String ftpFilepath;
    /**
     * ftp提供的http方式访问地址
     */
    @Value("${ftp.web.host}")
    private String ftpWebHost;
    /**
     * ftp提供的http方式访问的端口号
     */
    @Value("${ftp.web.port}")
    private String ftpWebPort;

    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public int getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(int ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUsername() {
        return ftpUsername;
    }

    public void setFtpUsername(String ftpUsername) {
        this.ftpUsername = ftpUsername;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpFilepath() {
        return ftpFilepath;
    }

    public void setFtpFilepath(String ftpFilepath) {
        this.ftpFilepath = ftpFilepath;
    }

    public String getFtpWebHost() {
        return ftpWebHost;
    }

    public void setFtpWebHost(String ftpWebHost) {
        this.ftpWebHost = ftpWebHost;
    }

    public String getFtpWebPort() {
        return ftpWebPort;
    }

    public void setFtpWebPort(String ftpWebPort) {
        this.ftpWebPort = ftpWebPort;
    }
}
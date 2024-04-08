package com.lb.csv;///**
// * @author Bobby Li
// * @version 1.0
// * @date 2022/11/14 10:52
// */
//public class SftpUtil {
//
//    private static Session session = null;
//    /**
//     * Channel
//     */
//    private static ChannelSftp channel = null;
//
//
//    private static BsnUriConfiguration bsnUriConfiguration = SpringBeanFactory.getBean("bsnUriConfiguration");
//    /**
//     * 登陆SFTP服务器
//     *
//     * @return boolean
//     */
//    public static boolean login() {
//
//        try {
//            JSch jsch = new JSch();
//            session = jsch.getSession(bsnUriConfiguration.getFtp().getUsername(),bsnUriConfiguration.getFtp().getHost(), bsnUriConfiguration.getFtp().getPort());
//            if (bsnUriConfiguration.getFtp().getPassword() != null) {
//                session.setPassword(bsnUriConfiguration.getFtp().getPassword());
//            }
//            Properties config = new Properties();
//            config.put("StrictHostKeyChecking", "no");
//            session.setConfig(config);
//            session.setTimeout(bsnUriConfiguration.getFtp().getTimeout());
//            session.connect();
//            log.debug("sftp session connected");
//
//            log.debug("opening channel");
//            channel = (ChannelSftp) session.openChannel("sftp");
//            channel.connect();
//
//            log.debug("connected successfully");
//            return true;
//        } catch (JSchException e) {
//            log.error("sftp login failed", e);
//            return false;
//        }
//    }
//
//    /**
//     * 登出sftp
//     */
//    public static void logout() {
//        if (channel != null) {
//            channel.quit();
//            channel.disconnect();
//        }
//        if (session != null) {
//            session.disconnect();
//        }
//        log.debug("logout successfully");
//    }
//
//    /**
//     * 单个文件上传 (sftp目录不存在则创建后上传)
//     * @param remoteFileName 远程文件名
//     * @return
//     */
//    public  String uploadFile(String remoteFileName, OutputStream outputStream) {
//        ByteArrayInputStream in = null;
//        try {
//            String remoteDir = bsnUriConfiguration.getFtp().getUploadDir();
//            //目录不存在则创建
//            createDir(remoteDir);
//            String remoteFilePath =  remoteDir + "/" + remoteFileName;
//            //将outputStream转换成FileInputStream
//            in = ConvertUtil.parse(outputStream);
//            channel.put(in, remoteFilePath);
//            return remoteFilePath;
//        } catch (Exception e) {
//            log.error( e + "");
//            throw new ApplicationException(I18nDbUtils.getI18nValue("ftp.problem"));
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
//
//    /**
//     * 单个文件上传 (sftp目录不存在则创建后上传)
//     *
//     * @param remoteFileName 远程文件名
//     * @param in
//     * @return
//     */
//    public boolean uploadFile(InputStream in, String remoteFileName) {
//        try {
//            //目录不存在则创建后上传
//            createDir(bsnUriConfiguration.getFtp().getUploadDir());
//            channel.put(in, remoteFileName);
//            return true;
//        } catch (SftpException e) {
//            e.printStackTrace();
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 批量上传 (sftp目录不存在则创建后上传)
//     *
//     * @param localPath
//     * @param isDel      是否上传完成后删除 true-删除 false-不删除
//     * @return
//     */
//    public static boolean batchUploadFile( String localPath, boolean isDel) {
//        try {
//            File file = new File(localPath);
//            File[] files = file.listFiles();
//            for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
//                if (files[i].isFile()
//                        && !files[i].getName().contains("bak")) {
//                    synchronized (localPath) {
//                        /*if (uploadFile(files[i].getName(),
//                                localPath, files[i].getName()) && isDel) {
//                            deleteFile(localPath + files[i].getName());
//                        }*/
//                    }
//                }
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            channel.disconnect();
//        }
//        return false;
//    }
//
//
//    /**
//     * 单个文件下载 (应用目录不存在则创建后下载)
//     *
//     * @param remotePath
//     * @param remoteFileName
//     * @param localPath
//     * @param localFileName
//     * @return
//     */
//    public boolean downloadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
//        try {
//            channel.cd(remotePath);
//            File file = new File(localPath + localFileName);
//            mkdirs(localPath + localFileName);
//            channel.get(remoteFileName, new FileOutputStream(file));
//            return true;
//        } catch (FileNotFoundException | SftpException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    /**
//     * 判断目录存不存在，不存在则创建
//     *
//     * @param createpath
//     * @return
//     */
//    public static boolean createDir(String createpath) {
//        try {
//            if (isDirExist(createpath)) {
//                channel.cd(createpath);
//                log.info(createpath);
//                return true;
//            }
//            String[] pathArry = createpath.split("/");
//            StringBuilder filePath = new StringBuilder("/");
//            for (String path : pathArry) {
//                if ("".equals(path)) {
//                    continue;
//                }
//                filePath.append(path).append("/");
//                createpath = filePath.toString();
//                if (isDirExist(createpath)) {
//                    channel.cd(createpath);
//                } else {
//                    channel.mkdir(createpath);
//                    channel.cd(createpath);
//                }
//            }
//            channel.cd(createpath);
//            return true;
//        } catch (SftpException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    /**
//     * 判断目录是否存在
//     *
//     * @param directory
//     * @return
//     */
//    public static boolean isDirExist(String directory) {
//        boolean isDirExistFlag = false;
//        try {
//            SftpATTRS sftpATTRS = channel.lstat(directory);
//            isDirExistFlag = true;
//            return sftpATTRS.isDir();
//        } catch (Exception e) {
//            if ("no such file".equals(e.getMessage().toLowerCase())) {
//                isDirExistFlag = false;
//            }
//        }
//        return isDirExistFlag;
//    }
//
//    /**
//     * 删除应用服务器文件(用于应用服务器上传SFTP文件服务器完成后)
//     *
//     * @param filePath
//     * @return
//     */
//    public static boolean deleteFile(String filePath) {
//        File file = new File(filePath);
//        if (!file.exists()) {
//            return false;
//        }
//        if (!file.isFile()) {
//            return false;
//        }
//        return file.delete();
//    }
//
//
//    /**
//     * 创建目录
//     *
//     * @param path
//     */
//    public static void mkdirs(String path) {
//        File f = new File(path);
//        String fs = f.getParent();
//        f = new File(fs);
//        if (!f.exists()) {
//            f.mkdirs();
//        }
//    }
//}
//
//**
//
//        ## 调用方式：
//
//        **
//private void uploadFileFtp(List<DxnChainCode> ccList) throws ApplicationException{
//        //登陆SFTP服务器
//        boolean login = SftpUtil.login();
//        if(!login){
//        throw new ApplicationException(I18nDbUtils.getI18nValue("ftp.problem"));
//        }
//        //上传后的文件路径
//        new SftpUtil().uploadFile(dxnChainCode.getId() + ".zip", outputStream);
//        //退出sftp
//        SftpUtil.logout();
//        }
//        ————————————————
//        版权声明：本文为CSDN博主「FLYINGONE2」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/qq_39994174/article/details/123789013
//}

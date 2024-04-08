package com.lb.csv;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Bobby Li
 * @version 1.0
 * @date 2022/11/11 15:00
 */
public class ZipUtils {


    private static final int BUFFER = 2048;

    private static final int TRANS_BUFFER = 10240;

    private ZipUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+08:00'");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("YYYY");
        Date b = simpleDateFormat1.parse("23");
        String c = simpleDateFormat1.format(b);
        System.out.println(c);
        String date1 = simpleDateFormat1.format(new Date());
        System.out.println(date1.substring(0,2) + "23");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        long fileName = Long.parseLong("1597121520000");
        String fileName1 =  simpleDateFormat.format(fileName);
        String date = simpleDateFormat1.format(new Date());
        System.out.println(date);

    }
    /**
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件并存放到zipFilePath路径下
     *
     * @param sourceFilePath 待压缩的文件路径
     * @param zipFilePath    压缩后存放路径
     * @param fileName       压缩后文件的名称
     * @return
     */
    public static File fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            sourceFile.mkdir();
        }
        File zipFile = new File(zipFilePath + File.separator + fileName + ".zip");
        if (zipFile.exists()) {
//            LOG.info("{}目录下存在名字为:{}.zip打包文件", zipFilePath, fileName);
        } else {
            File[] sourceFiles = sourceFile.listFiles();
            if (null == sourceFiles || sourceFiles.length < 1) {
//                LOG.info("待压缩的文件目录：{}里面不存在文件，无需压缩.", sourceFilePath);
            } else {
                try (
                        FileOutputStream fos = new FileOutputStream(zipFile);
                        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos))
                ) {
                    byte[] bytes = new byte[TRANS_BUFFER];
                    loopCreateZip(sourceFiles, zos, bytes);
                    flag = true;
                } catch (Exception e) {
                }
            }
        }
        return zipFile;
    }

    private static void loopCreateZip(File[] sourceFiles, ZipOutputStream zos, byte[] bytes) throws IOException {
        for (int i = 0; i < sourceFiles.length; i++) {
            System.out.println(sourceFiles[i].getName());
            // 创建ZIP实体，并添加进压缩包
            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
            zos.putNextEntry(zipEntry);
            // 读取待压缩的文件并写进压缩包里
            try (
                    FileInputStream fis = new FileInputStream(sourceFiles[i]);
                    BufferedInputStream bis = new BufferedInputStream(fis, TRANS_BUFFER)
            ) {
                int read = 0;
                while ((read = bis.read(bytes, 0, TRANS_BUFFER)) != -1) {
                    zos.write(bytes, 0, read);
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 读取zip包中的文本文件以及文件内容
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static boolean readZipFile(String filePath) {
        File sourceFile = new File(filePath);
        if (!sourceFile.exists()) {
            return false;
        }
        try (
                FileInputStream fis = new FileInputStream(sourceFile);
                ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                // write the files to the disk
                write(entry, zis);
            }
        } catch (Exception e) {
        }
        return true;
    }

    private static void write(ZipEntry entry, ZipInputStream zis) {
        int count;
        byte[] data = new byte[BUFFER];
        try (
                BufferedOutputStream dest = new BufferedOutputStream(new FileOutputStream(entry.getName()), BUFFER)
        ) {
            while ((count = zis.read(data, 0, BUFFER)) != -1) {
                dest.write(data, 0, count);
            }
            dest.flush();
        } catch (Exception e) {
        }
    }

    /**
     * 对zip文件进行解压
     *
     * @param sourcePath 解压文件路径
     * @param targetDir  解压目标地址
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<File> unzip(String sourcePath, String targetDir) {
        List<File> files = new ArrayList<>();
        File targetDirFile = new File(targetDir);
        if (!Files.exists(targetDirFile.toPath())) {
            targetDirFile.mkdir();
        }
        File file = new File(sourcePath);
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file, Charset.forName("GBK"));
            ZipEntry entry;
            File entryFile;
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zipFile.entries();
            while (entries.hasMoreElements()) {
                entry = entries.nextElement();
                if (entry.isDirectory()) {
                    return null;
                }
                entryFile = new File(targetDir + File.separator + entry.getName());
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                     BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry))
                ) {
                    int length;
                    while ((length = bis.read(buffer, 0, bufferSize)) != -1) {
                        bos.write(buffer, 0, length);
                    }
                    bos.flush();
                    files.add(entryFile);
                } catch (Exception e) {
                    return null;
                }
            }
            return files;
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (IOException e) {
            }
        }
    }

}

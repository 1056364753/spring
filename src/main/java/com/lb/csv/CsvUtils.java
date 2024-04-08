package com.lb.csv;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Bobby Li
 * @version 1.0
 * @date 2022/11/11 10:31
 */
public class CsvUtils {

    private CsvUtils() {
    }

    /**
     * 读取CSV文件内容
     *
     * @param file 传入的CSV文件
     * @return 读取的数据集合
     */
    public static List<String[]> readCsv(File file, boolean isNoRepeatData) {
        try (
                DataInputStream in = new DataInputStream(new FileInputStream(file));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GBK"))
        ) {
            //将第一行表头先读出,后面line读入的全部为数据内容
            String firstLine = reader.readLine();
            String line;
            List<String[]> data = new ArrayList<>();
            Set<String> lines = new HashSet<>();
            while ((line = reader.readLine()) != null) {
                //未设置重复过滤  或者 设置重复过滤且未重复
                //limit -1 防止无数据读取报角标越界异常
                String[] item = line.split(",", -1);
                if (isNoRepeatData) {
                    if (!lines.contains(line)) {
                        data.add(item);
                        lines.add(line);
                    }
                } else {
                    data.add(item);
                }
            }
            return data;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 将CSV格式数据写入指定CSV文件
     *
     * @param filePath CSV文件写入的全路径
     * @param heads    要导入的数据表头
     * @Param fileName 导出时浏览器中展示的文件名
     * @Param response HttpServlet响应exportCsv(filePath, Heads, head,Body,heads,Trailer,trailer);
     */
    public static void exportCsv(String filePath, List<String> head, List<Object> heads, List<String> secondHeads, List<String> body, List<bean> bodys, List<String> trailer,List<Object> trailers) throws IOException {
//        if (StringUtils.isEmpty(filePath) || heads.isEmpty()) {
//            return;
//        }
        long start = System.currentTimeMillis();
        File tempFile = new File("123.CSV");
            tempFile.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "GBK"))) {
            StringBuilder stringBuilder = new StringBuilder();
            head.forEach(value ->
                    stringBuilder.append(value).append(',')
            );
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            writer.write(stringBuilder.toString());
            writer.newLine();
            stringBuilder.delete(0, stringBuilder.length());
            heads.forEach(value -> stringBuilder.append(value).append(','));
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            writer.write(stringBuilder.toString());
            writer.newLine();
            stringBuilder.delete(0, stringBuilder.length());
            secondHeads.forEach(value ->
                    stringBuilder.append(value).append(',')
            );
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            writer.write(stringBuilder.toString());
            writer.newLine();
            //写内容
            for(Object obj : bodys) {
                //利用反射获取所有字段
                Field[] fields = obj.getClass().getDeclaredFields();
                for (String property : body) {
                    for(int i =0;i<=fields.length;i++){

                    }
                    for (Field field : fields) {
                        //设置字段可见性
                        field.setAccessible(true);
                        if (property.equalsIgnoreCase(field.getName())) {
                            stringBuilder.append(field.get(obj)).append(',');
                            writer.write(String.valueOf(field.get(obj)));
                            if(!"accessKey".equalsIgnoreCase(field.getName())){
                                writer.write(",");
                            }
//                        System.out.println(field.get(obj));
                            continue;
                        }
                    }
                }
                //写完一行换行
                writer.write("\r\n");
            }
            stringBuilder.delete(0, stringBuilder.length());
            trailer.forEach(value ->
                    stringBuilder.append(value).append(',')
            );
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            writer.write(stringBuilder.toString());
            writer.newLine();

            stringBuilder.delete(0, stringBuilder.length());
            trailers.forEach(value ->
                    stringBuilder.append(value).append(',')
            );
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            writer.write(stringBuilder.toString());
            writer.flush();
        } catch (Exception e) {
        }
    }


    /**
     * 将本地文件写入响应流
     *
     * @return
     */
//    public static boolean responseCsv(HttpServletResponse response, String fileName, String filePath) {
//        File file = new File(filePath);
//        if (!file.exists()) {
//            return false;
//        }
//        try (InputStream in = new FileInputStream(file);
//             OutputStream out = response.getOutputStream()
//        ) {
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//            IOUtils.copy(in, out);
//        } catch (IOException e) {
//        } finally {
//            delByPath(filePath);
//        }
//        return true;
//    }


    public static boolean delByPath(String path) {
        File file = new File(path);
        return deleteDir(file);
    }


    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children == null) {
                return true;
            }
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        //设置可执行权限   设置可执行权限    设置可写权限
        if (dir.setExecutable(true) && dir.setReadable(true) && dir.setWritable(true)) {
        } else {
        }
        try {
            Files.delete(dir.toPath());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}

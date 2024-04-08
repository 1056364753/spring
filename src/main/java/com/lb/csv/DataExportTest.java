package com.lb.csv;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Bobby Li
 * @version 1.0
 * @date 2022/11/11 10:30
 */
public class DataExportTest {

    /**
     * CSV导出表头信息
     */
    private static final List<String> Heads = Arrays.asList("Record type", "File name", "File create date", "File create time");
    private static final List<String> Body = Arrays.asList("recordType", "userAccessID", "accessDate", "accessTime", "system", "functionScreen", "accessKeyType", "accessKey");
    private static final List<String> SecondHeads = Arrays.asList("Record type", "User Access ID", "Access Date", "Access Time", "System", "Function Screen", "Access Key Type", "Access Key");
    private static final List<String> Trailer = Arrays.asList("Record type", "Record Count");



    public static void main(String[] args) {
        DataExportTest dataExportTest = new DataExportTest();
        dataExportTest.export();
    }
    public String export() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        List<Object> head = new ArrayList<>();
        List<Object> trailer = new ArrayList<>();
        List<bean> heads = new ArrayList<bean>();
        bean bean = new bean();
        bean bean2 = new bean();

        head.add(0);
        head.add("teat.CSV");
        head.add(dateFormat.format(new Date()));
        head.add(System.currentTimeMillis());

        bean.setRecordType("1");
        bean.setUserAccessID("1009876");
        bean.setAccessDate("20220616");
        bean.setAccessTime("12242");
        bean.setSystem("A");
        bean.setFunctionScreen("b");
        bean.setAccessKey("C");
        bean.setAccessKeyType("D");

        bean2.setRecordType("1");
        bean2.setUserAccessID("1009876");
        bean2.setAccessDate("20220616");
        bean2.setAccessTime("12242");
        bean2.setSystem("A");
        bean2.setFunctionScreen("b");
        bean2.setAccessKey("C");
        bean2.setAccessKeyType("D");
        heads.add(bean);
        heads.add(bean2);

        trailer.add(7);
        trailer.add(heads.size());
        //设置临时路径
        String localTempPath = "D:\\temp";
        String filePath = localTempPath + File.separator + "testTemplate" + ".csv";
        try {
            CsvUtils.exportCsv(filePath, Heads, head,SecondHeads,Body,heads,Trailer,trailer);
//            CsvUtils.responseCsv(response, "导出测试" + ".csv", filePath);
            return "导出成功";
        } catch (Exception e) {
            return "导出失败";
        }

    }
}

package com.lb.csv;

/**
 * @author Bobby Li
 * @version 1.0
 * @date 2022/11/11 11:24
 */
public class bean {
    private String recordType;
    private String userAccessID;
    private String accessDate;
    private String accessTime;
    private String system;
    private String functionScreen;
    private String accessKeyType;
    private String accessKey;

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getUserAccessID() {
        return userAccessID;
    }

    public void setUserAccessID(String userAccessID) {
        this.userAccessID = userAccessID;
    }

    public String getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(String accessDate) {
        this.accessDate = accessDate;
    }

    public String getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(String accessTime) {
        this.accessTime = accessTime;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getFunctionScreen() {
        return functionScreen;
    }

    public void setFunctionScreen(String functionScreen) {
        this.functionScreen = functionScreen;
    }

    public String getAccessKeyType() {
        return accessKeyType;
    }

    public void setAccessKeyType(String accessKeyType) {
        this.accessKeyType = accessKeyType;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
}

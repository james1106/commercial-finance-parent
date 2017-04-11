package com.xiangan.platform.chainserver.common.domain;

import java.util.ArrayList;

/**
 * 文件数据请求参数
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 11:55
 * @Version 1.0
 * @Copyright
 */
public class FileDataRequest extends BaseEntity {
    private static final long serialVersionUID = -1346759473985496215L;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件URL
     */
    private String url;

    /**
     * 文件sha256值
     */
    private String sha256;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件数据
     * <p>
     * Base64编码字符串
     * </p>
     */
    private String data;

    /**
     * 文件是否加密
     */
    private boolean isEncrypted;

    /**
     * 文件可解密应用ID
     */
    private ArrayList<String> appIDs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public void setEncrypted(boolean encrypted) {
        isEncrypted = encrypted;
    }

    public ArrayList<String> getAppIDs() {
        return appIDs;
    }

    public void setAppIDs(ArrayList<String> appIDs) {
        this.appIDs = appIDs;
    }
}

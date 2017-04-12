package com.xiangan.platform.chainserver.common.domain;

import com.xiangna.www.protos.common.Common;

/**
 * API请求参数基类
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 10:35
 * @Version 1.0
 * @Copyright
 */
public class BaseRequest extends BaseEntity {


    private static final long serialVersionUID = -5503037758008198540L;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用ID对应的KEY
     */
    private String appkey;

    /**
     * 请求服务版本
     */
    private String version;

    /**
     * 请求来源
     */
    private String source;

    /**
     * 请求来源IP
     */
    private String sourceIP;

    private Common.OperateInfo operateInfo;

    public Common.OperateInfo getOperateInfo() {
        return operateInfo;
    }

    public void setOperateInfo(Common.OperateInfo operateInfo) {
        this.operateInfo = operateInfo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }
}

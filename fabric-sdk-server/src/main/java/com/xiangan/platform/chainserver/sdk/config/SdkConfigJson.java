package com.xiangan.platform.chainserver.sdk.config;

import com.xiangan.platform.chainserver.common.domain.BaseEntity;

import java.util.List;

/**
 * sdk配置
 * <p>
 * 映射sdk-config.json 配置数据
 * </p>
 *
 * @Creater liuzhudong
 * @Date 2017/4/1 09:48
 * @Version 1.0
 * @Copyright
 */
public class SdkConfigJson extends BaseEntity {

    private static final long serialVersionUID = -8627433969373505768L;

    private CommonConfig config;

    private List<OrdererConfig> orderers;

    private List<OrgConfig> orgs;

    public CommonConfig getConfig() {
        return config;
    }

    public void setConfig(CommonConfig config) {
        this.config = config;
    }

    public List<OrdererConfig> getOrderers() {
        return orderers;
    }

    public void setOrderers(List<OrdererConfig> orderers) {
        this.orderers = orderers;
    }

    public List<OrgConfig> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<OrgConfig> orgs) {
        this.orgs = orgs;
    }

    /**
     * 统用配置
     */
    public static class CommonConfig extends BaseEntity {

        private static final long serialVersionUID = 9085148062691258900L;

        /**
         * chiancode 部署等待时间(ms)
         */
        private int deployWaitTime;

        /**
         * 交易请求等待时间(ms)
         */
        private int transactionWaitTime;

        public int getDeployWaitTime() {
            return deployWaitTime;
        }

        public void setDeployWaitTime(int deployWaitTime) {
            this.deployWaitTime = deployWaitTime;
        }

        public int getTransactionWaitTime() {
            return transactionWaitTime;
        }

        public void setTransactionWaitTime(int transactionWaitTime) {
            this.transactionWaitTime = transactionWaitTime;
        }
    }

    /**
     * orderer节点配置
     */
    public static class OrdererConfig extends BaseEntity {

        private static final long serialVersionUID = 8225900944061697472L;

        /**
         * 服务名称
         */
        private String server_hostname;

        /**
         * 地址
         */
        private String url;

        /**
         * tls ca证书
         */
        private String tls_cacerts;

        public String getServer_hostname() {
            return server_hostname;
        }

        public void setServer_hostname(String server_hostname) {
            this.server_hostname = server_hostname;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTls_cacerts() {
            return tls_cacerts;
        }

        public void setTls_cacerts(String tls_cacerts) {
            this.tls_cacerts = tls_cacerts;
        }
    }

    /**
     * org config
     */
    public static class OrgConfig extends BaseEntity {

        private static final long serialVersionUID = -8987461758087073924L;

        private String name;

        private String mspid;

        private String ca;

        private List<PeerConfig> peers;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMspid() {
            return mspid;
        }

        public void setMspid(String mspid) {
            this.mspid = mspid;
        }

        public String getCa() {
            return ca;
        }

        public void setCa(String ca) {
            this.ca = ca;
        }

        public List<PeerConfig> getPeers() {
            return peers;
        }

        public void setPeers(List<PeerConfig> peers) {
            this.peers = peers;
        }
    }

    /**
     * peer config
     */
    public static class PeerConfig extends BaseEntity {

        private static final long serialVersionUID = -6311390858476138280L;

        private String requests;

        private String events;

        private String server_hostname;

        private String tls_cacerts;

        public String getRequests() {
            return requests;
        }

        public void setRequests(String requests) {
            this.requests = requests;
        }

        public String getEvents() {
            return events;
        }

        public void setEvents(String events) {
            this.events = events;
        }

        public String getServer_hostname() {
            return server_hostname;
        }

        public void setServer_hostname(String server_hostname) {
            this.server_hostname = server_hostname;
        }

        public String getTls_cacerts() {
            return tls_cacerts;
        }

        public void setTls_cacerts(String tls_cacerts) {
            this.tls_cacerts = tls_cacerts;
        }
    }


}



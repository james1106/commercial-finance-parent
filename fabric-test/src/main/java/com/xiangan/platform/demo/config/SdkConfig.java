package com.xiangan.platform.demo.config;

import org.hyperledger.fabric.sdk.Peer;

import java.util.List;

/**
 * sdk配置
 *
 * @Creater liuzhudong
 * @Date 2017/4/1 09:48
 * @Version 1.0
 * @Copyright
 */
public class SdkConfig {

    private List<OrdererConfig> ordererConfigs;
    private List<OrgConfig> orgConfigs;

    public List<OrdererConfig> getOrdererConfigs() {
        return ordererConfigs;
    }

    public void setOrdererConfigs(List<OrdererConfig> ordererConfigs) {
        this.ordererConfigs = ordererConfigs;
    }

    public List<OrgConfig> getOrgConfigs() {
        return orgConfigs;
    }

    public void setOrgConfigs(List<OrgConfig> orgConfigs) {
        this.orgConfigs = orgConfigs;
    }

    /**
     * orderer config
     */
    public static class OrdererConfig {
        private String name;
        private String url;
        private String tlsCacerts;

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

        public String getTlsCacerts() {
            return tlsCacerts;
        }

        public void setTlsCacerts(String tlsCacerts) {
            this.tlsCacerts = tlsCacerts;
        }
    }

    /**
     * org config
     */
    public static class OrgConfig {

        private String name;
        private String mspid;
        private String caServerUrl;
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

        public String getCaServerUrl() {
            return caServerUrl;
        }

        public void setCaServerUrl(String caServerUrl) {
            this.caServerUrl = caServerUrl;
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
    public static class PeerConfig {

        private String name;
        private String url;
        private String events;
        private String tlsCacerts;

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

        public String getEvents() {
            return events;
        }

        public void setEvents(String events) {
            this.events = events;
        }

        public String getTlsCacerts() {
            return tlsCacerts;
        }

        public void setTlsCacerts(String tlsCacerts) {
            this.tlsCacerts = tlsCacerts;
        }
    }


}



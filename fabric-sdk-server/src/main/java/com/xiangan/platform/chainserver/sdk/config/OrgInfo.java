package com.xiangan.platform.chainserver.sdk.config;

import com.xiangan.platform.chainserver.common.domain.BaseEntity;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.Peer;

import java.util.List;

/**
 * 基于ca的组织划分
 *
 * @Creater liuzhudong
 * @Date 2017/4/14 15:44
 * @Version 1.0
 * @Copyright
 */
public class OrgInfo extends BaseEntity {

    private static final long serialVersionUID = -6718255196187128872L;

    private String name;

    private String mspid;

    private String ca;

    private List<Peer> peers;

    private List<EventHub> eventHubs;

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

    public List<Peer> getPeers() {
        return peers;
    }

    public void setPeers(List<Peer> peers) {
        this.peers = peers;
    }

    public List<EventHub> getEventHubs() {
        return eventHubs;
    }

    public void setEventHubs(List<EventHub> eventHubs) {
        this.eventHubs = eventHubs;
    }
}

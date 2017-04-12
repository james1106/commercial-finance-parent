package com.xiangan.platform.chainserver.common.entity.user;

import com.xiangan.platform.chainserver.common.domain.BaseEntity;

import java.util.List;

/**
 * 用户信息
 *
 * @Creater liuzhudong
 * @Date 2017/4/11 14:25
 * @Version 1.0
 * @Copyright
 */
public class UserInfo extends BaseEntity {
    private static final long serialVersionUID = 4939307247737199380L;

    /**
     * 用户ca账户信息
     */
    private UserAccount userAccount;

    /**
     * 用户的peer节点信息
     */
    private List<PeerConfig> peers;

    /**
     * 用户配置的orderer节点信息
     */
    private List<OrdererConfig> orderers;

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public List<PeerConfig> getPeers() {
        return peers;
    }

    public void setPeers(List<PeerConfig> peers) {
        this.peers = peers;
    }

    public List<OrdererConfig> getOrderers() {
        return orderers;
    }

    public void setOrderers(List<OrdererConfig> orderers) {
        this.orderers = orderers;
    }
}

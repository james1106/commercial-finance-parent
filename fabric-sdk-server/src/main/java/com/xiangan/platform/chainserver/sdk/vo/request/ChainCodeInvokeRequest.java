package com.xiangan.platform.chainserver.sdk.vo.request;

import com.xiangan.platform.chainserver.common.domain.BaseEntity;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.User;

import java.util.List;

/**
 * chaincode invoke 操作参数
 *
 * @Creater liuzhudong
 * @Date 2017/4/11 12:03
 * @Version 1.0
 * @Copyright
 */
public class ChainCodeInvokeRequest extends BaseEntity {
    private static final long serialVersionUID = -2709311724392741280L;

    // ChainCodeID 参数
    private String chainCodeName;

    private String chainCodePath;

    private String chainCodeVersion;

    // 设置操作用户
    private User user;

    // chain 名称
    private String chainName;

//    List<Orderer> orderers,
//    List<Peer> peers,
//    List<EventHub> eventHubs


}

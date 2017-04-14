package com.xiangan.platform.chainserver.sdk;

import com.google.protobuf.ByteString;
import com.xiangan.platform.chainserver.sdk.config.SDKConfigFactory;
import com.xiangan.platform.chainserver.sdk.exception.PeersResponseNotSameException;
import com.xiangna.www.protos.common.Common;
import org.hyperledger.fabric.sdk.Chain;
import org.hyperledger.fabric.sdk.ChainCodeID;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Fabric SDK 操作模板服务
 *
 * @Creater liuzhudong
 * @Date 2017/4/14 11:58
 * @Version 1.0
 * @Copyright
 */
@Service
public class ChainTemplate {

    private final Logger logger = LoggerFactory.getLogger(ChainTemplate.class);

    private final ChainService chainService;

    private final ChainCodeService chainCodeService;

    private final SDKConfigFactory configFactory;

    @Autowired
    public ChainTemplate(ChainService chainService,
                         ChainCodeService chainCodeService,
                         SDKConfigFactory configFactory) {
        this.chainService = chainService;
        this.chainCodeService = chainCodeService;
        this.configFactory = configFactory;
    }

    public HFClient setClient(User user) throws InvalidArgumentException, MalformedURLException {
        HFClient client = configFactory.getClient();

        HFCAClient memberServices = configFactory.getCaService(user.getMSPID());
        client.setMemberServices(memberServices);

        // 设置操作用户
        client.setUserContext(user);
        return client;
    }

    public Chain getChain(String chainName, String mspid, HFClient client) throws TransactionException,
            ProposalException, InvalidArgumentException {

        Chain chain = chainService.getChain(chainName, configFactory.getOrderers(), configFactory.getPeers(mspid),
                configFactory.getEventHubs(mspid), client);
        chain.setDeployWaitTime(configFactory.getDeployWaitTime());
        chain.setTransactionWaitTime(configFactory.getTransactionWaitTime());
        return chain;
    }

    public ByteString getResult(List<Common.ChainCodeResponse> responses) throws PeersResponseNotSameException {
        Common.ChainCodeResponse result = null;
        for (Common.ChainCodeResponse response : responses) {
            if (result == null) {
                result = response;
                continue;
            }
            if (result.equals(response)) {
                continue;
            }
            throw new PeersResponseNotSameException();
        }
        return result == null ? null : result.getResult();
    }

    /**
     * getChainCodeId
     *
     * @param chainCodeName
     * @param chainCodePath
     * @param chainCodeVersion
     * @return
     */
    public ChainCodeID getChainCodeId(String chainCodeName,
                                      String chainCodePath,
                                      String chainCodeVersion) {

        return chainCodeService.getChainCodeId(chainCodeName, chainCodePath, chainCodeVersion);
    }

    /**
     * invoke
     *
     * @param chainName
     * @param user
     * @param chainCodeID
     * @param args
     * @return
     * @throws Exception
     */
    public ByteString invoke(String chainName, User user, ChainCodeID chainCodeID, List<Object> args) throws Exception {
        String mspid = user.getMSPID();
        HFClient client = setClient(user);
        Chain chain = getChain(chainName, mspid, client);
        return getResult(chainCodeService.invoke(chainCodeID, args, client, chain));
    }

    /**
     * query
     *
     * @param chainName
     * @param user
     * @param chainCodeID
     * @param args
     * @return
     * @throws Exception
     */
    public ByteString query(String chainName, User user, ChainCodeID chainCodeID, List<Object> args) throws Exception {
        String mspid = user.getMSPID();
        HFClient client = setClient(user);
        Chain chain = getChain(chainName, mspid, client);
        return getResult(chainCodeService.query(chainCodeID, args, client, chain));
    }
}

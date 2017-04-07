package com.xiangan.platform.demo;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Timestamp;
import com.xiangna.www.protos.common.Common.*;
import com.xiangna.www.protos.common.Common.ChainCodeResponse;
import com.xiangna.www.protos.common.Common.OperateInfo;
import com.xiangna.www.protos.contract.Contract.FinancingContract;
import com.xiangna.www.protos.contract.ContractRequest.ContractExcuteRequest;
import com.xiangna.www.protos.contract.ContractRequest.ContractQueryRequest;
import org.hyperledger.fabric.sdk.Chain;
import org.hyperledger.fabric.sdk.ChainCodeID;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 合约cc测试
 *
 * @Creater liuzhudong
 * @Date 2017/4/6 14:29
 * @Version 1.0
 * @Copyright
 */
public class ContractChaincodeTest extends ChainCodeBaseTest {

    ChainCodeID getChainCodeID() {
//        String chainCodeName = "contract_cc_gosa";
        String chainCodeName = "contract_cc_go";
        String chainCodePath = "github.com/xncc/contract_cc";
        String chainCodeVersion = "1";

        return demo.getChainCodeId(chainCodeName, chainCodePath, chainCodeVersion);
    }

    @Test
    public void install() throws Exception {
        HFClient client = getClient();
        setUserContext(demo.getUser("admin", "adminpw", orgConfig.getMspid(),
                client.getMemberServices()), client);

        Chain chain = getDefaultChain(client);

        File chainCodeRootSource = new File("demo");
        ProposalResponseResult result = demo.install(chainCodeRootSource, getChainCodeID(), client, chain);
        Assert.assertNotNull("install fail", result);
        Assert.assertTrue("install fail", result.isSuccess());


    }

    @Test
    public void instantiate() throws Exception {
        HFClient client = getClient();
        setUserContext(demo.getUser("admin", "adminpw", orgConfig.getMspid(),
                client.getMemberServices()), client);

        Chain chain = getDefaultChain(client);
        logger.info("get chain OK.............");
        chain.setDeployWaitTime(120000);
        chain.setTransactionWaitTime(120000);

        File policyFile = new File("demo/e2e-2Orgs/channel/chaincodeendorsementpolicy.yaml");

        ArrayList<String> args = new ArrayList<>();
        args.add("init");

        ProposalResponseResult result = demo.instantiate(policyFile, args, getChainCodeID(), client, chain);
        Assert.assertNotNull("instantiate fail", result);
        Assert.assertTrue("instantiate fail", result.isSuccess());

    }

    @Test
    public void query() throws Exception {
        HFClient client = getClient();
        setUserContext(demo.getUser("admin", "adminpw", orgConfig.getMspid(),
                client.getMemberServices()), client);

        Chain chain = getDefaultChain(client);
        logger.info("get chain OK.............");
        chain.setDeployWaitTime(120000);
        chain.setTransactionWaitTime(120000);

        ContractQueryRequest request = ContractQueryRequest.newBuilder()
                .setNo("xxas")
                .build();

        ArrayList<Object> bytes = new ArrayList<>(3);
        bytes.add("query");
        bytes.add(request);

        List<Peer> peerList = new ArrayList<>(1);
//        peerList.add(chain.getPeers().iterator().next());
        peerList.add(demo.getPeer(client, "peer0", "grpc://localhost:7051"));
//        peerList.add(demo.getPeer(client, "peer2", "grpc://localhost:8051"));

        ProposalResponseResult result = demo.query(getChainCodeID(), bytes, client, chain);
        logger.info("query OK...............");
        Assert.assertNotNull("instantiate fail", result);

        logger.info("------------------------------------------------------------------");
        for (ProposalResponse proposalResponse : result.getSuccessful()) {
            logger.info("======================== peer: " + proposalResponse.getPeer().getName() + " =================");
            if (proposalResponse.isVerified() && proposalResponse.getStatus() == ProposalResponse.Status.SUCCESS) {
                ByteString bs = proposalResponse.getProposalResponse().getResponse().getPayload();
                ChainCodeResponse response = ChainCodeResponse.parseFrom(bs);
                System.out.println(response.getCode());
                System.out.println(response.getMessage());
                FinancingContract order = FinancingContract.parseFrom(response.getResult());
                System.out.println(order.getOrderNo());
                System.out.println(order.getOperateInfo().toString());
            }
        }

    }

    @Test
    public void init() throws Exception {
        HFClient client = getClient();
        setUserContext(demo.getUser("admin", "adminpw", orgConfig.getMspid(),
                client.getMemberServices()), client);

        Chain chain = getDefaultChain(client);
        logger.info("get chain OK.............");
        chain.setDeployWaitTime(120000);
        chain.setTransactionWaitTime(120000);

        long now = new Date().getTime() / 1000;

        AppVo creater = AppVo.newBuilder()
                .setAppId("xxasfaafaf")
                .setAppName("xxxx供应商")
                .setType("供应商")
                .build();

        OperateInfo operateInfo = OperateInfo.newBuilder()
                .setCreater(creater)
                .setCreaterIp("10.10.2.1")
                .setCreateTime(Timestamp.newBuilder().setSeconds(now).build())
                .build();

        FinancingContract order = FinancingContract.newBuilder()
                .setOrderNo("xxas")
                .setOperateInfo(operateInfo)
                .build();

        ContractExcuteRequest request = ContractExcuteRequest.newBuilder()
//                .setNo("xxas")
                .setAction("init")
                .setContractData(order.toByteString())
                .build();

        ArrayList<Object> bytes = new ArrayList<>(3);
        bytes.add("excute");
        bytes.add(request);

        ProposalResponseResult result = demo.invoke(getChainCodeID(), bytes, client, chain);
        logger.info("invoke OK...............");
        Assert.assertNotNull("invoke fail", result);

        outResult(result.getSuccessful());
        outResult(result.getFailed());


    }

    void outResult(Collection<ProposalResponse> results) throws InvalidProtocolBufferException {
        logger.info("------------------------------------------------------------------");
        for (ProposalResponse proposalResponse : results) {
            logger.info("======================== peer: " + proposalResponse.getPeer().getName() + " =================");
            if (proposalResponse.isVerified() && proposalResponse.getStatus() == ProposalResponse.Status.SUCCESS) {
                ByteString bs = proposalResponse.getProposalResponse().getResponse().getPayload();
                ChainCodeResponse response = ChainCodeResponse.parseFrom(bs);
                System.out.println(response.getCode());
                System.out.println(response.getMessage());
            }
        }
    }
}

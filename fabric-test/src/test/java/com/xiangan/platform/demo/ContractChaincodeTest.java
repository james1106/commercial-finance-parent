package com.xiangan.platform.demo;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xiangna.www.protos.common.Common.ChainCodeResponse;
import com.xiangna.www.protos.contract.ContractRequest.ContractQueryRequest;
import org.hyperledger.fabric.sdk.Chain;
import org.hyperledger.fabric.sdk.ChainCodeID;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

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

        File policyFile = new File("demo/e2e-2Orgs/channel/members_from_org1_or_2.policy");

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

        ProposalResponseResult result = demo.query(getChainCodeID(), bytes, client, chain);
        logger.info("query OK...............");
        Assert.assertNotNull("instantiate fail", result);

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

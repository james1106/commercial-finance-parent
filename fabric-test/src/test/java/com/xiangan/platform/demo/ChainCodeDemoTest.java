package com.xiangan.platform.demo;

import com.github.example.protos.order.Order.OrderExample;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import com.xiangan.platform.demo.config.SdkConfig;
import com.xiangan.platform.demo.config.SdkConfig.OrdererConfig;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.Chain;
import org.hyperledger.fabric.sdk.ChainCodeID;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.ChainNotFoundException;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoPrimitives;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * chain code demo test
 *
 * @Creater liuzhudong
 * @Date 2017/3/31 13:36
 * @Version 1.0
 * @Copyright
 */
public class ChainCodeDemoTest {
    Logger logger = Logger.getLogger(ChainCodeDemoTest.class);
    ChainCodeDemo demo = new ChainCodeDemo();
    SdkConfig config;
    SdkConfig.OrgConfig orgConfig;


    @Before
    public void setUp() throws Exception {
        String configJson = "{\t\n" +
                "\t\"ordererConfigs\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"url\": \"grpc://localhost:7050\",\n" +
                "\t\t\t\"name\": \"orderer0\",\n" +
                "\t\t\t\"tlsCacerts\": \"../../fixtures/tls/orderer/ca-cert.pem\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"orgConfigs\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"peerOrg1\",\n" +
                "\t\t\t\"mspid\": \"Org1MSP\",\n" +
                "\t\t\t\"caServerUrl\": \"http://localhost:7054\",\n" +
                "\t\t\t\"peers\": [\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"url\": \"grpc://localhost:7051\",\n" +
                "\t\t\t\t\t\"events\": \"grpc://localhost:7053\",\n" +
                "\t\t\t\t\t\"name\": \"peer0\",\n" +
                "\t\t\t\t\t\"tlsCacerts\": \"../../fixtures/tls/peers/peer0/ca-cert.pem\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"url\": \"grpc://localhost:7056\",\n" +
                "\t\t\t\t\t\"events\": \"grpc://localhost:7058\",\n" +
                "\t\t\t\t\t\"name\": \"peer1\",\n" +
                "\t\t\t\t\t\"tlsCacerts\": \"../../fixtures/tls/peers/peer1/ca-cert.pem\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t]\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"peerOrg2\",\n" +
                "\t\t\t\"mspid\": \"Org2MSP\",\n" +
                "\t\t\t\"caServerUrl\": \"http://localhost:8054\",\n" +
                "\t\t\t\"peers\": [\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"url\": \"grpc://localhost:8051\",\n" +
                "\t\t\t\t\t\"events\": \"grpc://localhost:8053\",\n" +
                "\t\t\t\t\t\"name\": \"peer2\",\n" +
                "\t\t\t\t\t\"tlsCacerts\": \"../../fixtures/tls/peers/peer2/ca-cert.pem\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"url\": \"grpc://localhost:8056\",\n" +
                "\t\t\t\t\t\"events\": \"grpc://localhost:8058\",\n" +
                "\t\t\t\t\t\"name\": \"peer3\",\n" +
                "\t\t\t\t\t\"tlsCacerts\": \"../../fixtures/tls/peers/peer3/ca-cert.pem\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t]\n" +
                "\t\n" +
                "}";

        Gson gson = new Gson();
        config = gson.fromJson(configJson, SdkConfig.class);
        orgConfig = config.getOrgConfigs().iterator().next();
        System.out.println("config init OK.................");

    }

    @Test
    public void userTest() throws Exception {

        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
        logger.info("client init OK ................");

        HFCAClient caClient = new HFCAClient(orgConfig.getCaServerUrl(), null);
        logger.info("ca service init OK ............");
        client.setMemberServices(caClient);

        String mspid = orgConfig.getMspid();
        String userName = "admin";
        String userPasswd = "adminpw";


//        User admin = demo.getUser(userName, userPasswd, mspid, caClient);
//        System.out.println(admin);

        userName = "wkxxsaas6";
//        String affiliation = "org1";
//        userPasswd = demo.registerUser(userName, affiliation, admin, caClient);
//        System.out.println(userPasswd);

        userPasswd = "gzyDWrzMLmzW";
        User user1 = demo.getUser(userName, userPasswd, mspid, caClient);

        System.out.println(user1);

//        User user2 = new CaUser(userName);
//        user2.

//        caClient.reenroll(user1);
//        System.out.println(user1);

//        Thread.sleep(5000L);
//
//        caClient.revoke(admin, user1.getEnrollment(), 1);
//        System.out.println(user1);
//
//        Thread.sleep(5000L);
//        caClient.reenroll(user1);
//        System.out.println(user1);


    }

    HFClient getClient() throws CryptoException, InvalidArgumentException, MalformedURLException, EnrollmentException {
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
        logger.info("client init OK ................");

        HFCAClient caClient = new HFCAClient(orgConfig.getCaServerUrl(), null);
        client.setMemberServices(caClient);
        logger.info("ca service init OK ............");

        String userName = "wkxxsaas6";
        String userPasswd = "gzyDWrzMLmzW";

        User user = demo.getUser(userName, userPasswd, orgConfig.getMspid(), caClient);
        out("user info:\n %s \n========================", user);
        client.setUserContext(user);
        logger.info("set user context OK .............");

        return client;
    }


    @Test
    public void initChainTest() throws Exception {
        HFClient client = getClient();

        String name = "foo";
        File txFile = new File("demo/e2e-2Orgs/channel/foo.tx");
        List<Orderer> orderers = new ArrayList<>();
        for (OrdererConfig ordererConfig : config.getOrdererConfigs()) {
            orderers.add(demo.getOrderer(client, ordererConfig.getName(), ordererConfig.getUrl()));
        }

        List<Peer> peers = new ArrayList<>();
        List<EventHub> eventHubs = new ArrayList<>();
        for (SdkConfig.PeerConfig peerConfig : orgConfig.getPeers()) {
            peers.add(demo.getPeer(client, peerConfig.getName(), peerConfig.getUrl()));
            eventHubs.add(demo.getEventHub(client, peerConfig.getName(), peerConfig.getEvents()));
        }

        Chain chain = demo.initChain(name, txFile, orderers, peers, eventHubs, client);
        Assert.assertNotNull("init chain fail ", chain);

    }

    @Test
    public void getChainTest() throws Exception {
        HFClient client = getClient();

        String name = "bar";
        List<Orderer> orderers = new ArrayList<>();
        for (OrdererConfig ordererConfig : config.getOrdererConfigs()) {
            orderers.add(demo.getOrderer(client, ordererConfig.getName(), ordererConfig.getUrl()));
        }

        List<Peer> peers = new ArrayList<>();
        List<EventHub> eventHubs = new ArrayList<>();
        for (SdkConfig.PeerConfig peerConfig : orgConfig.getPeers()) {
            peers.add(demo.getPeer(client, peerConfig.getName(), peerConfig.getUrl()));
            eventHubs.add(demo.getEventHub(client, peerConfig.getName(), peerConfig.getEvents()));
        }

        Chain chain = null;
        try {

            chain = demo.getChain(name, orderers, peers, eventHubs, client);
        } catch (TransactionException e) {
            if (e instanceof ChainNotFoundException) {
                logger.info("NOT FOUND INIT CHAIN .............");
                File txFile = new File("demo/e2e-2Orgs/channel/bar.tx");
                chain = demo.initChain(name, txFile, orderers, peers, eventHubs, client);
            } else {
                throw e;
            }

        }
        Assert.assertNotNull("get chain fail ", chain);

    }

    Chain getChain(HFClient client) throws InvalidArgumentException, TransactionException, IOException, ProposalException {
        String name = "foo";

        List<Orderer> orderers = new ArrayList<>();
        for (OrdererConfig ordererConfig : config.getOrdererConfigs()) {
            orderers.add(demo.getOrderer(client, ordererConfig.getName(), ordererConfig.getUrl()));
        }

        List<Peer> peers = new ArrayList<>();
        List<EventHub> eventHubs = new ArrayList<>();
        for (SdkConfig.PeerConfig peerConfig : orgConfig.getPeers()) {
            peers.add(demo.getPeer(client, peerConfig.getName(), peerConfig.getUrl()));
            eventHubs.add(demo.getEventHub(client, peerConfig.getName(), peerConfig.getEvents()));
        }

        Chain chain;
        try {
            chain = demo.getChain(name, orderers, peers, eventHubs, client);
        } catch (TransactionException e) {
            if (e instanceof ChainNotFoundException) {
                File txFile = new File("demo/e2e-2Orgs/channel/foo.tx");
                chain = demo.initChain(name, txFile, orderers, peers, eventHubs, client);
            } else {
                throw e;
            }

        }
        return chain;
    }

    ChainCodeID getChainCodeID() {
        String chainCodeName = "example_cc";
        String chainCodePath = "github.com/example_cc";
        String chainCodeVersion = "1.0";

        return demo.getChainCodeId(chainCodeName, chainCodePath, chainCodeVersion);
    }

    @Test
    public void installTest() throws Exception {
        HFClient client = getClient();
        Chain chain = getChain(client);

        File chainCodeRootSource = new File("demo");
        ProposalResponseResult result = demo.install(chainCodeRootSource, getChainCodeID(), client, chain);
        Assert.assertNotNull("install fail", result);
        Assert.assertTrue("install fail", result.isSuccess());
    }

    //    @Test
    public void joinChainTest() throws Exception {
        HFClient client = getClient();
        Chain chain = getChain(client);

        for (Peer peer : chain.getPeers()) {
            chain.joinPeer(peer);
        }

        logger.info("peers join chain OK...............");
    }

    @Test
    public void instantiateTest() throws Exception {
        HFClient client = getClient();
        Chain chain = getChain(client);
        logger.info("get chain OK.............");

        File policyFile = new File("demo/e2e-2Orgs/channel/members_from_org1_or_2.policy");

        ArrayList<String> args = new ArrayList<>();
        args.add("init");
        args.add("sss");

        chain.setDeployWaitTime(120000);
//        chain.setInvokeWaitTime(120000);

        ProposalResponseResult result = demo.instantiate(policyFile, args, getChainCodeID(), client, chain);
        Assert.assertNotNull("instantiate fail", result);
        Assert.assertTrue("instantiate fail", result.isSuccess());

    }

    @Test
    public void invokeTest() throws Exception {
        HFClient client = getClient();
        Chain chain = getChain(client);
        logger.info("get chain OK.............");

        long times = new Date().getTime() / 1000;
        OrderExample order = OrderExample.newBuilder()
                .setNo("11122ed")
                .setDays(1232)
                .setMoney(12342211111L)
                .setTime(times)
                .setTimestamp(Timestamp.newBuilder().setSeconds(times).build())
                .build();

        ArrayList<Object> bytes = new ArrayList<>(3);
        bytes.add("init");
        bytes.add(order.getNo());
        bytes.add(order);

        for (byte b : order.toByteArray()) {
            System.out.print(b + " ,");
        }
        System.out.println("\n========================");

        chain.setTransactionWaitTime(120000);

        ProposalResponseResult result = demo.invoke(getChainCodeID(), bytes, client, chain);
        Assert.assertNotNull("instantiate fail", result);
        Assert.assertTrue("instantiate fail", result.isSuccess());

    }

    @Test
    public void queryTest() throws Exception {

        HFClient client = getClient();
        Chain chain = getChain(client);
        logger.info("get chain OK.............");

        long times = new Date().getTime() / 1000;
        OrderExample order = OrderExample.newBuilder()
                .setNo("11122ed")
                .setDays(1232)
                .setMoney(12342211111L)
                .setTime(times)
                .setTimestamp(Timestamp.newBuilder().setSeconds(times).build())
                .build();

        for (byte b : order.toByteArray()) {
            System.out.print(b + " ,");
        }
        System.out.println("\n========================");

        ArrayList<Object> bytes = new ArrayList<>(3);
        bytes.add("query");
        bytes.add(order.getNo());

        ProposalResponseResult result = demo.query(getChainCodeID(), bytes, client, chain);
        Assert.assertNotNull("instantiate fail", result);
        Assert.assertTrue("instantiate fail", result.isSuccess());
        logger.info("query OK...............");

        for (ProposalResponse proposalResponse : result.getSuccessful()) {
            logger.info("======================== peer: " + proposalResponse.getPeer().getName() + " =================");
            if (proposalResponse.isVerified() && proposalResponse.getStatus() == ProposalResponse.Status.SUCCESS) {
                ByteString bs = proposalResponse.getProposalResponse().getResponse().getPayload();
                for (byte b : bs.toByteArray()) {
                    System.out.print(b + " ,");
                }
                System.out.println("\n========================");
                OrderExample orderExample = OrderExample.parseFrom(bs);
                System.out.println(orderExample.toString());
            }
        }

    }

    @Test
    public void chainCodeTest() throws Exception {
        HFClient client = getClient();
        Chain chain = getChain(client);

        chain.setDeployWaitTime(120000);
        chain.setTransactionWaitTime(120000);

        ProposalResponseResult result;

//        out("install............");
//        File chainCodeRootSource = new File("demo");
//        result = demo.install(chainCodeRootSource, getChainCodeID(), client, chain);
//        Assert.assertNotNull("install fail", result);
//        Assert.assertTrue("install fail", result.isSuccess());
//        for (ProposalResponse response : result.getSuccessful()) {
//            out("Successful install proposal response Txid: %s from peer %s", response.getTransactionID(),
//                    response.getPeer().getName());
//        }
//        logger.info("install OK...............");


        File policyFile = new File("demo/e2e-2Orgs/channel/members_from_org1_or_2.policy");

        ArrayList<String> args = new ArrayList<>();
        args.add("init");
        args.add("sss");


        result = demo.instantiate(policyFile, args, getChainCodeID(), client, chain);
        Assert.assertNotNull("instantiate fail", result);
        Assert.assertTrue("instantiate fail", result.isSuccess());
        for (ProposalResponse response : result.getSuccessful()) {
            out("Successful instantiate proposal response Txid: %s from peer %s", response.getTransactionID(),
                    response.getPeer().getName());
        }
        for (ProposalResponse response : result.getFailed()) {
            out("Fail instantiate proposal response Txid: %s from peer %s", response.getTransactionID(),
                    response.getPeer().getName());
        }
        logger.info("instantiate OK...............");

        long times = new Date().getTime() / 1000;
        OrderExample order = OrderExample.newBuilder()
                .setNo("11122ed")
                .setDays(1232)
                .setMoney(12342211111L)
                .setTime(times)
                .setTimestamp(Timestamp.newBuilder().setSeconds(times).build())
                .build();

        ArrayList<Object> bytes = new ArrayList<>(3);
        bytes.add("init");
        bytes.add(order.getNo());
        bytes.add(order);

        for (byte b : order.toByteArray()) {
            System.out.print(b + " ,");
        }
        System.out.println("\n========================");


//        client.setUserContext(demo.getUser());
        result = demo.invoke(getChainCodeID(), bytes, client, chain);
        logger.info("invoke OK...............");
        Assert.assertNotNull("instantiate fail", result);
        Assert.assertTrue("instantiate fail", result.isSuccess());

        ArrayList<Object> queryArgs = new ArrayList<>(3);
        queryArgs.add("query");
        queryArgs.add(order.getNo());

        result = demo.query(getChainCodeID(), queryArgs, client, chain);
        Assert.assertNotNull("instantiate fail", result);
        Assert.assertTrue("instantiate fail", result.isSuccess());
        logger.info("query OK...............");

        for (ProposalResponse proposalResponse : result.getSuccessful()) {
            logger.info("======================== peer: " + proposalResponse.getPeer().getName() + " =================");
            if (proposalResponse.isVerified() && proposalResponse.getStatus() == ProposalResponse.Status.SUCCESS) {
                ByteString bs = proposalResponse.getProposalResponse().getResponse().getPayload();
                for (byte b : bs.toByteArray()) {
                    System.out.print(b + " ,");
                }
                System.out.println("\n========================");
                OrderExample orderExample = OrderExample.parseFrom(bs);
                System.out.println(orderExample.toString());
            }
        }

    }

    void out(String format, Object... args) {
        logger.info(String.format(format, args));
    }

}
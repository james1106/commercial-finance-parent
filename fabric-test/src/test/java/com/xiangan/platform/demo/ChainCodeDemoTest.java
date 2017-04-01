package com.xiangan.platform.demo;

import com.github.example.protos.order.Order.OrderExample;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import com.xiangan.platform.demo.config.SdkConfig;
import com.xiangan.platform.demo.config.SdkConfig.OrdererConfig;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.*;
import org.hyperledger.fabric.sdk.security.CryptoPrimitives;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

import static org.junit.Assert.fail;

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


        User admin = demo.getUser(userName, userPasswd, mspid, caClient);
        System.out.println(admin);

        userName = "wkxxsaas";
        String affiliation = "org1";
        userPasswd = demo.registerUser(userName, affiliation, admin, caClient);
        System.out.println(userPasswd);

//        userPasswd = "ZysMlHRrlaAv";
        User user1 = demo.getUser(userName, userPasswd, mspid, caClient);
        System.out.println(user1);


    }

    HFClient getClient() throws CryptoException, InvalidArgumentException, MalformedURLException, EnrollmentException {
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
        logger.info("client init OK ................");

        HFCAClient caClient = new HFCAClient(orgConfig.getCaServerUrl(), null);
        client.setMemberServices(caClient);
        logger.info("ca service init OK ............");

        client.setUserContext(demo.getUser("admin", "adminpw", orgConfig.getMspid(), caClient));
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

        chain.setInvokeWaitTime(120000);

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
        chain.setInvokeWaitTime(120000);

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

    //    @Test
//    public void initChainTest() throws Exception {
//        HFClient client = HFClient.createNewInstance();
//        client.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
//        logger.info("client init OK ................");
//
//        HFCAClient caClient = new HFCAClient(testConfig.get("ca_url"), null);
//        client.setMemberServices(caClient);
//        logger.info("ca service init OK ............");
//
//        client.setUserContext(getUser("admin", "adminpw", testConfig.get("org_mspid"), caClient));
//        logger.info("set user[admin] OK..............");
//
//        File file = new File("src/test/fixture/sdkintegration/e2e-2Orgs/channel/foo.tx");
//        initChain("foo", file, client);
//        logger.info("init chain test OK...............");
//    }
//
//    @Test
//    public void joinChainTest() throws Exception {
//        HFClient client = HFClient.createNewInstance();
//        client.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
//        logger.info("client init OK ................");
//
//        HFCAClient caClient = new HFCAClient(testConfig.get("ca_url"), null);
//        client.setMemberServices(caClient);
//        logger.info("ca service init OK ............");
//
//        client.setUserContext(getUser("admin", "adminpw", testConfig.get("org_mspid"), caClient));
//        logger.info("set user[admin] OK..............");
//
//        Chain chain = getChain("foo", client);
//        logger.info("get chain OK...............");
//
//        Peer peer = getPeer(client);
//        chain.joinPeer(peer);
//        logger.info("peer0 join chain OK .................");
//    }
//
//    @Test
//    public void installChaincodeTest() throws Exception {
//        HFClient client = HFClient.createNewInstance();
//        client.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
//        logger.info("client init OK ................");
//
//        HFCAClient caClient = new HFCAClient(testConfig.get("ca_url"), null);
//        client.setMemberServices(caClient);
//        logger.info("ca service init OK ............");
//
//        client.setUserContext(getUser("admin", "adminpw", testConfig.get("org_mspid"), caClient));
//        logger.info("set user[admin] OK..............");
//
//        Chain chain = getChain("foo", client);
//        logger.info("get chain OK...............");
//
////        File protoFile = new File("/opt/gopath");
////        install(protoFile, client, chain);
////        logger.info("chaincode install OK.............");
//
////        File chainCodeFile = new File("/opt/gopath");
//        File chainCodeFile = new File("src/test/fixture");
//        install(chainCodeFile, client, chain);
//        logger.info("chaincode install OK.............");
//    }
//
//    @Test
//    public void instantiateChaincoddeTest() throws Exception {
//        HFClient client = HFClient.createNewInstance();
//        client.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
//        logger.info("client init OK ................");
//
//        HFCAClient caClient = new HFCAClient(testConfig.get("ca_url"), null);
//        client.setMemberServices(caClient);
//        logger.info("ca service init OK ............");
//
//        client.setUserContext(getUser("admin", "adminpw", testConfig.get("org_mspid"), caClient));
//        logger.info("set user[admin] OK..............");
//
//        Chain chain = getChain("foo", client);
//        logger.info("get chain OK...............");
//
//        File policyFile = new File("src/test/fixture/sdkintegration/e2e-2Orgs/channel/members_from_org1_or_2.policy");
//
//        OrderExample order = getOrderData();
////        order.toByteArray()
//
//        String[] args = new String[2];
//        args[0] = order.getNo();
//        args[1] = "sssss";
////        args[1] = order.toByteString().toStringUtf8();
////        logger.info("arg data:\n" + args[1]);
//
////        ByteArrayOutputStream bos = new ByteArrayOutputStream(5000);
////        order.writeTo(bos);
//
////        ArrayList<byte[]> byteArgs = new ArrayList<>(2);
////        byteArgs.add(order.getNo().getBytes("UTF-8"));
////        byteArgs.add(order.toByteArray());
//
//        instantiate(policyFile, client, chain, args);
//
////        bos.close();
//        logger.info("chaincode instantiate OK.............");
//
//    }
//
//    @Test
//    public void deployTest() throws Exception {
//        HFClient client = HFClient.createNewInstance();
//        client.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
//        logger.info("client init OK ................");
//
//        HFCAClient caClient = new HFCAClient(testConfig.get("ca_url"), null);
//        client.setMemberServices(caClient);
//        logger.info("ca service init OK ............");
//
//        client.setUserContext(getUser("admin", "adminpw", testConfig.get("org_mspid"), caClient));
//        logger.info("set user[admin] OK..............");
//
//        File file = new File("src/test/fixture/sdkintegration/e2e-2Orgs/channel/foo.tx");
//        Chain chain = initChain("foo", file, client);
//        logger.info("init chain test OK...............");
//
//        Peer peer = getPeer(client);
//        chain.joinPeer(peer);
//        logger.info("peer0 join chain OK .................");
//
//        Thread.sleep(1000L);
//
//        File chainCodeFile = new File("src/test/fixture");
//        install(chainCodeFile, client, chain);
//        logger.info("chaincode install OK.............");
//
//        Thread.sleep(1000L);
//
//        File policyFile = new File("src/test/fixture/sdkintegration/e2e-2Orgs/channel/members_from_org1_or_2.policy");
//
//        OrderExample order = getOrderData();
//
//        String[] args = new String[2];
//        args[0] = order.getNo();
//        args[1] = "sssss";
//
//        instantiate(policyFile, client, chain, args);
//
//    }
//
//    @Test
//    public void invokeChaincodeTest() throws Exception {
//        HFClient client = HFClient.createNewInstance();
//        client.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
//        logger.info("client init OK ................");
//
//        HFCAClient caClient = new HFCAClient(testConfig.get("ca_url"), null);
//        client.setMemberServices(caClient);
//        logger.info("ca service init OK ............");
//
//        client.setUserContext(getUser("admin", "adminpw", testConfig.get("org_mspid"), caClient));
//        logger.info("set user[admin] OK..............");
//
//        Chain chain = getChain("foo", client);
//        logger.info("get chain OK...............");
//        OrderExample order = getOrderData();
////        order.toByteArray();
////        String[] args = new String[3];
////        args[0] = "init";
////        args[1] = order.getNo();
////        args[2] = order.toByteString().toStringUtf8();
//
//        ArrayList<byte[]> bytes = new ArrayList<>(3);
//        bytes.add("init".getBytes("UTF-8"));
//        bytes.add(order.getNo().getBytes("UTF-8"));
//        bytes.add(order.toByteArray());
//
//        for(byte[] bb : bytes){
//            for(byte b : bb){
//                System.out.print(b + " ,");
//            }
//            System.out.println("========================");
//        }
//
//        invoke(client, chain, bytes);
////        invoke(client, chain, new String[]{"init", "e", "250", "f", "550"});
////        invoke(client, chain, new String[]{"move", "a", "f", "-10"});
//        logger.info("chaincode invoke init OK.............");
//
//    }
//
//    @Test
//    public void queryChaincodeTest() throws Exception {
//        HFClient client = HFClient.createNewInstance();
//        client.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
//        logger.info("client init OK ................");
//
//        HFCAClient caClient = new HFCAClient(testConfig.get("ca_url"), null);
//        client.setMemberServices(caClient);
//        logger.info("ca service init OK ............");
//
//        client.setUserContext(getUser("admin", "adminpw", testConfig.get("org_mspid"), caClient));
//        logger.info("set user[admin] OK..............");
//
//        Chain chain = getChain("foo", client);
//        logger.info("get chain OK...............");
//
//        Peer peer = getPeer(client);
//        ByteString bs = query(client, chain, peer, new String[]{"query", "hhas10234"});
//        logger.info("query chaincode OK ............");
//
//        OrderExample orderExample = OrderExample.parseFrom(bs);
//        logger.info(orderExample.toString());
//    }

    //    @Test
//    public void test() throws Exception {
//        HFClient client = HFClient.createNewInstance();
//        client.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
//
//        HFCAClient caClient = new HFCAClient(testConfig.get("ca_url"), null);
//        client.setMemberServices(caClient);
//
//        client.setUserContext(getUser("admin", "adminpw", testConfig.get("org_mspid"), caClient));
//
//        Chain chain = getChain("foo", client);
////        File file = new File("src/test/fixture/sdkintegration/e2e-2Orgs/channel/foo.tx");
////        Chain chain = initChain("foo", file, client);
//        logger.info("get chain OK...............");
//
////        Peer peer = getPeer(client);
////        chain.joinPeer(peer);
////        logger.info("peer0 join chain OK .................");
//
////        File chainCodeFile = new File("src/test/fixture");
////        install(chainCodeFile, client, chain);
////        logger.info("chaincode install OK.............");
////
//        File policyFile = new File("src/test/fixture/sdkintegration/e2e-2Orgs/channel/members_from_org1_or_2.policy");
//
//        OrderExample order = getOrderData();
//
//        String[] args = new String[2];
//        args[0] = order.getNo();
//        args[1] = order.toByteString().toStringUtf8();
//
//
//        instantiate(policyFile, client, chain, args);
//        logger.info("chaincode instantiate OK.............");
//
//        invoke(client, chain, new String[]{"init", "c", "200", "d", "500"});
//        invoke(client, chain, new String[]{"init", "e", "250", "f", "550"});
//        invoke(client, chain, new String[]{"move", "a", "f", "-10"});
//        logger.info("chaincode invoke OK.............");
////
//
//        query(client, chain, peer, new String[]{"query", "a"});
////        query(client, chain, peer, new String[]{"query", "c"});
////        query(client, chain, peer, new String[]{"query", "b"});
////        query(client, chain, peer, new String[]{"query", "d"});
//        query(client, chain, peer, new String[]{"query", "e"});
//        query(client, chain, peer, new String[]{"query", "f"});

//        String testTxID = "784d356e69e22ca0ca229a5cf3b95993cab9ce41b54f747aa89c64c66de4c71f";
//
//
//
//
//        BlockchainInfo channelInfo = chain.queryBlockchainInfo();
//        logger.info("Channel height: " + channelInfo.getHeight());
//        String chainCurrentHash = Hex.encodeHexString(channelInfo.getCurrentBlockHash());
//        String chainPreviousHash = Hex.encodeHexString(channelInfo.getPreviousBlockHash());
//        logger.info("Channel current block hash: " + chainCurrentHash);
//        logger.info("Channel previous block hash: " + chainPreviousHash);
//
//        BlockInfo returnedBlock1 = chain.queryBlockByTransactionID(testTxID);
//        logger.info("queryBlockByTxID returned block with blockNumber " + returnedBlock1.getBlockNumber());
//
////        // Query by block number. Should return latest block, i.e. block number 2
//        BlockInfo returnedBlock = chain.queryBlockByNumber(channelInfo.getHeight() - 1);
//        String previousHash = Hex.encodeHexString(returnedBlock.getPreviousHash());
//        logger.info("queryBlockByNumber returned correct block with blockNumber " + returnedBlock.getBlockNumber()
//                + " \n previous_hash " + previousHash);
////
////        // Query by block hash. Using latest block's previous hash so should return block number 1
//        byte[] hashQuery = returnedBlock.getPreviousHash();
//        returnedBlock = chain.queryBlockByHash(hashQuery);
//        logger.info("queryBlockByHash returned block with blockNumber " + returnedBlock.getBlockNumber());
////
////        // query transaction by ID
//        TransactionInfo txInfo = chain.queryTransactionByID(testTxID);
//        logger.info("QueryTransactionByID returned TransactionInfo: txID " + txInfo.getTransactionID()
//                + "\n     validation code " + txInfo.getValidationCode().getNumber());
//    }


//void invoke(HFClient client, Chain chain, String[] args, MySampleOrg mySampleOrg) throws Exception {
//    Collection<ProposalResponse> successful = new LinkedList<>();
//    Collection<ProposalResponse> failed = new LinkedList<>();
//
//    client.setUserContext(mySampleOrg.getUser(TESTUSER_1_NAME));
//
//    /// Send invoke proposal to all peers
//    InvokeProposalRequest invokeProposalRequest = client.newInvokeProposalRequest();
//    invokeProposalRequest.setChaincodeID(buildChainCodeID());
//    invokeProposalRequest.setFcn("invoke");
//    invokeProposalRequest.setArgs(args);
//    System.out.println(format("sending invokeProposal to all peers with arguments", args));
//
//    Collection<ProposalResponse> invokePropResp = chain.sendInvokeProposal(invokeProposalRequest, chain.getPeers());
//    for (ProposalResponse response : invokePropResp) {
//        if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
//            System.out.println(format("Successful invoke proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName()));
//            successful.add(response);
//        } else {
//            failed.add(response);
//        }
//    }
//    System.out.println(format("Received %d invoke proposal responses. Successful+verified: %d . Failed: %d",
//            invokePropResp.size(), successful.size(), failed.size()));
//    if (failed.size() > 0) {
//        ProposalResponse firstInvokeProposalResponse = failed.iterator().next();
//        fail("Not enough endorsers :" + failed.size() + " endorser error: " +
//                firstInvokeProposalResponse.getMessage() +
//                ". Was verified: " + firstInvokeProposalResponse.isVerified());
//    }
//    System.out.println(format("Successfully received invoke proposal responses."));
//
//    chain.sendTransaction(successful, chain.getOrderers()).thenApply(transactionEvent -> {
//        assertTrue(transactionEvent.isValid());
//        testTxID = transactionEvent.getTransactionID(); // used in the channel queries later
//        System.out.println("testTxID: " + testTxID);
//
//        return null;
//    }).exceptionally(e -> {
//        if (e instanceof TransactionEventException) {
//            BlockEvent.TransactionEvent te = ((TransactionEventException) e).getTransactionEvent();
//            if (te != null) {
//                fail(format("Transaction with txid %s failed. %s", te.getTransactionID(), e.getMessage()));
//            }
//        }
//        fail(format("Test failed with %s exception %s", e.getClass().getName(), e.getMessage()));
//
//        return null;
//    }).get(120, TimeUnit.SECONDS);
//}

}
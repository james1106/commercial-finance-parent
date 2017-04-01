package com.xiangan.platform.demo;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionEventException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.junit.Assert.*;


/**
 * Created by CL on 29/03/2017.
 */
public class E2ETest {

    private static final Config config = Config.getConfig();
    private static final String TEST_ADMIN_NAME = "admin";
    private static final String TESTUSER_1_NAME = "user1";

    private final int gossipWaitTime = config.getGossipWaitTime();

    private static final String CHAIN_CODE_NAME1 = "example_test.go";
    private static final String CHAIN_CODE_PATH1 = "github.com/example_test";
    private static final String CHAIN_CODE_VERSION = "1.0";

    private static final String FOO_CHAIN_NAME = "foo";
    private static final String BAR_CHAIN_NAME = "bar";


    private String testTxID = null;  // save the CC invoke TxID and use in queries


    private Collection<MySampleOrg> testMySampleOrgs;

    Logger logger = Logger.getLogger(E2ETest.class);


    @Before
    public void setConfig() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, MalformedURLException {


        testMySampleOrgs = config.getIntegrationTestsSampleOrgs();
        //Set up hfca for each sample org

        for (MySampleOrg mySampleOrg : testMySampleOrgs) {
            mySampleOrg.setCAClient(new HFCAClient(mySampleOrg.getCALocation(), mySampleOrg.getCAProperties()));
        }
    }

    private void deleteSampleStore() {
        File sampleStoreFile = new File(System.getProperty("java.io.tmpdir") + "/HFCSampletest.properties");
        if (sampleStoreFile.exists()) { //For testing start fresh
            sampleStoreFile.delete();
        }
    }

    @Test
    public void setup() {

        try {
            //Create instance of client.
            HFClient client = HFClient.createNewInstance();
            client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());

            //Persistence is not part of SDK. Sample file store is for demonstration purposes only!
            //   MUST be replaced with more robust application implementation  (Database, LDAP)
            File sampleStoreFile = new File(System.getProperty("java.io.tmpdir") + "/HFCSampletest.properties");
            final MySampleStore mySampleStore = new MySampleStore(sampleStoreFile);

            // get users for all orgs
            for (MySampleOrg mySampleOrg : testMySampleOrgs) {

                HFCAClient ca = mySampleOrg.getCAClient();

                final String orgName = mySampleOrg.getName();
                final String mspid = mySampleOrg.getMSPID();
                client.setMemberServices(ca);

                MySampleUser admin = null;
//                if (mySampleStore.getValue(MySampleUser.toKeyValStoreName(TEST_ADMIN_NAME, orgName)) != null) {
//                    admin = new MySampleUser(TEST_ADMIN_NAME, orgName, mySampleStore).restoreState();
//                } else {
                admin = new MySampleUser(TEST_ADMIN_NAME, orgName, mySampleStore);
//                }

//                if (!admin.isEnrolled()) {
                admin.setEnrollment(ca.enroll(admin.getName(), "adminpw"));
                admin.setMPSID(mspid);
                mySampleOrg.setAdmin(admin); // The admin of this org.
//                }

                MySampleUser user = null;
                if (mySampleStore.getValue(MySampleUser.toKeyValStoreName(TESTUSER_1_NAME, orgName)) != null) {
                    user = new MySampleUser(TESTUSER_1_NAME, mySampleOrg.getName(), mySampleStore).restoreState();
                } else {
                    user = new MySampleUser(TESTUSER_1_NAME, mySampleOrg.getName(), mySampleStore);
                }

//                if (!user.isRegistered()) {  // users need to be registered AND enrolled
//                RegistrationRequest rr = new RegistrationRequest(user.getName(), "org1.department1");
//                user.setEnrollmentSecret(ca.register(rr, admin));
//                }
//                if (!user.isEnrolled()) {
                user.setEnrollment(ca.enroll(user.getName(), user.getEnrollmentSecret()));
                user.setMPSID(mspid);
                mySampleOrg.addUser(user);//Remember user belongs to this Org
//                }
            }

            //Construct and run the chains
            MySampleOrg mySampleOrg0 = config.getIntegrationTestsSampleOrg("peerOrg1");
            client.setUserContext(mySampleOrg0.getAdmin());


            Chain chainFoo = reconstructChain(FOO_CHAIN_NAME, client, mySampleOrg0);
//            install(new File("demo"), client, chainFoo, mySampleOrg0);

            File policyFile = new File("demo/e2e-2Orgs/channel/members_from_org1_or_2.policy");
            instantiate(policyFile, client, chainFoo, new String[]{"a", "100", "b", "200"});
//            Chain chainFoo = reconstructChain(FOO_CHAIN_NAME, client, mySampleOrg0);


            invoke(client, chainFoo, new String[]{"move", "a", "b", "100"}, mySampleOrg0);
            query(client, chainFoo, new String[]{"query", "b"});

//
//            Chain chainXX = constructChain(FOO_CHAIN_NAME, client, mySampleOrg0);
//
////            install(new File(CHAINCODE_PATH), client, chainXX, mySampleOrg0);
//            instantiate(policyFile, client, chainXX, new String[]{});
//
//            ArrayList<byte[]> bytesList= new ArrayList<>();
//            String methodName = "test";
//            Record.Builder recordBuilder = Record.newBuilder();
//            recordBuilder.setId("001");
//            recordBuilder.setName("ABCD");
//            recordBuilder.setPhone("13400000000");
//            Record record = recordBuilder.build();
//
//            bytesList.add(methodName.getBytes());
//            bytesList.add(record.toByteArray());
//
//
//            invoke(client, chainXX, bytesList);
////            query(client, chainXX, new String[]{"query", "b"});

            System.out.println(format("\n"));


//            MySampleOrg sampleOrg1 = config.getIntegrationTestsSampleOrg("peerOrg2");
//            Chain chainBar = constructChain(BAR_CHAIN_NAME, client, sampleOrg1);
//            runChain(client, chainBar, true, sampleOrg1, 100); //run a newly constructed foo chain with different b value!
            System.out.println(format("That's all folks!"));
//            prtBlockInfo(mySampleOrg0, chainXX);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private ChainCodeID buildChainCodeID() {
//        return ChainCodeID.newBuilder().setName(CHAIN_CODE_NAME)
//                .setVersion(CHAIN_CODE_VERSION)
//                .setPath(CHAIN_CODE_PATH).build();
        return ChainCodeID.newBuilder().setName(CHAIN_CODE_NAME1)
                .setVersion(CHAIN_CODE_VERSION)
                .setPath(CHAIN_CODE_PATH1).build();
    }

    private void prtBlockInfo(MySampleOrg mySampleOrg, Chain chain) {
        // Channel queries

        try {
            // We can only send channel queries to peers that are in the same org as the SDK user context
            // Get the peers from the current org being used and pick one randomly to send the queries to.
            Set<Peer> peerSet = mySampleOrg.getPeers();
            Peer queryPeer = peerSet.iterator().next();
            System.out.println("Using peer %s for channel queries:" + queryPeer.getName());

            BlockchainInfo channelInfo = chain.queryBlockchainInfo(queryPeer);
            System.out.println("Channel info for : " + chain.getName());
            System.out.println("Channel height: " + channelInfo.getHeight());
            String chainCurrentHash = Hex.encodeHexString(channelInfo.getCurrentBlockHash());
            String chainPreviousHash = Hex.encodeHexString(channelInfo.getPreviousBlockHash());
            System.out.println("Channel current block hash: " + chainCurrentHash);
            System.out.println("Channel previous block hash: " + chainPreviousHash);

            // Query by block number. Should return latest block, i.e. block number 2
            BlockInfo returnedBlock = chain.queryBlockByNumber(queryPeer, channelInfo.getHeight() - 1);
            String previousHash = Hex.encodeHexString(returnedBlock.getPreviousHash());
            System.out.println("queryBlockByNumber returned correct block with blockNumber " + returnedBlock.getBlockNumber()
                    + " \n previous_hash " + previousHash);
            assertEquals(channelInfo.getHeight() - 1, returnedBlock.getBlockNumber());
            assertEquals(chainPreviousHash, previousHash);

            // Query by block hash. Using latest block's previous hash so should return block number 1
            byte[] hashQuery = returnedBlock.getPreviousHash();
            returnedBlock = chain.queryBlockByHash(queryPeer, hashQuery);
            System.out.println("queryBlockByHash returned block with blockNumber " + returnedBlock.getBlockNumber());
            assertEquals(channelInfo.getHeight() - 2, returnedBlock.getBlockNumber());

            // Query block by TxID. Since it's the last TxID, should be block 2
            returnedBlock = chain.queryBlockByTransactionID(queryPeer, testTxID);
            System.out.println("queryBlockByTxID returned block with blockNumber " + returnedBlock.getBlockNumber());
            assertEquals(channelInfo.getHeight() - 1, returnedBlock.getBlockNumber());

            // query transaction by ID
            TransactionInfo txInfo = chain.queryTransactionByID(queryPeer, testTxID);
            System.out.println("QueryTransactionByID returned TransactionInfo: txID " + txInfo.getTransactionID()
                    + "\n     validation code " + txInfo.getValidationCode().getNumber());
        } catch (ProposalException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }


    }

    private Collection<Orderer> getOrdererList(HFClient client, MySampleOrg mySampleOrg) throws InvalidArgumentException {
        Collection<Orderer> orderers = new LinkedList<>();
        for (String orderName : mySampleOrg.getOrdererNames()) {
            orderers.add(client.newOrderer(orderName, mySampleOrg.getOrdererLocation(orderName),
                    config.getOrdererProperties(orderName)));
        }

        return orderers;
    }

    private Collection<Peer> getPeerList(HFClient client, MySampleOrg mySampleOrg) throws InvalidArgumentException {
        Collection<Peer> peers = new LinkedList<>();
        for (String peerName : mySampleOrg.getPeerNames()) {
            peers.add(client.newPeer(peerName, mySampleOrg.getPeerLocation(peerName), config.getPeerProperties(peerName)));
        }

        return peers;
    }

    private Collection<EventHub> getEventHubList(HFClient client, MySampleOrg mySampleOrg) throws InvalidArgumentException {
        Collection<EventHub> peers = new LinkedList<>();
        for (String eventHubName : mySampleOrg.getEventHubNames()) {
            peers.add(client.newEventHub(eventHubName, mySampleOrg.getEventHubLocation(eventHubName),
                    config.getEventHubProperties(eventHubName)));
        }

        return peers;
    }

    private Chain reconstructChain(String name, HFClient client, MySampleOrg mySampleOrg) throws Exception {

        //Construct the chain
        client.setUserContext(mySampleOrg.getAdmin());
        Chain newChain = client.newChain(name);

        for (Orderer orderer : getOrdererList(client, mySampleOrg)) {
            newChain.addOrderer(orderer);
        }

        for (Peer peer : getPeerList(client, mySampleOrg)) {
            newChain.addPeer(peer);
            mySampleOrg.addPeer(peer);
        }
        for (EventHub eventHub : getEventHubList(client, mySampleOrg)) {
            newChain.addEventHub(eventHub);
        }

        try {
            newChain.initialize();
        } catch (InvalidArgumentException e) {
            System.out.println("reconstructChain error--InvalidArgumentException!");
        } catch (TransactionException e) {
            System.out.println("reconstructChain error--TransactionException!");
        }

        if (newChain.isInitialized()) {
            return newChain;
        }

        return null;
    }

    private Chain constructChain(String name, HFClient client, MySampleOrg mySampleOrg) throws Exception {
        //////////////////////////// TODo Needs to be made out of bounds and here chain just retrieved
        //Construct the chain

        Collection<Orderer> orderers = getOrdererList(client, mySampleOrg);

        //Just pick the first orderer in the list to create the chain.
        Orderer anOrderer = orderers.iterator().next();
//        orderers.remove(anOrderer);

        ChainConfiguration chainConfiguration = new ChainConfiguration(new File("demo/e2e-2Orgs/channel/" + name + ".tx"));

        client.setUserContext(mySampleOrg.getAdmin());


        Chain newChain = client.newChain(name, anOrderer, chainConfiguration);
        System.out.println(format("Created chain %s", name));

        for (Orderer orderer : orderers) {
            newChain.addOrderer(orderer);
        }

        for (Peer peer : getPeerList(client, mySampleOrg)) {
            newChain.joinPeer(peer);
            mySampleOrg.addPeer(peer);
        }
        for (EventHub eventHub : getEventHubList(client, mySampleOrg)) {
            newChain.addEventHub(eventHub);
        }

        newChain.initialize();

        System.out.println(format("Finished initialization chain %s", name));

        return newChain;
    }


    void install(File chainCodeFile, HFClient client, Chain chain, MySampleOrg mySampleOrg) throws Exception {
        Collection<ProposalResponse> responses;
        Collection<ProposalResponse> successful = new LinkedList<>();
        Collection<ProposalResponse> failed = new LinkedList<>();

        // Install Proposal Request
        System.out.println(format("Creating install proposal"));

        InstallProposalRequest installProposalRequest = client.newInstallProposalRequest();
        installProposalRequest.setChaincodeID(buildChainCodeID());
        ////For GO language and serving just a single user, chaincodeSource is mostly likely the users GOPATH
        installProposalRequest.setChaincodeSourceLocation(chainCodeFile);
        installProposalRequest.setChaincodeVersion(CHAIN_CODE_VERSION);

        System.out.println("Sending install proposal");

        ////////////////////////////
        // only a client from the same org as the peer can issue an install request
        int numInstallProposal = 0;
        //    Set<String> orgs = orgPeers.keySet();
        //   for (SampleOrg org : testSampleOrgs) {
        client.setUserContext(mySampleOrg.getAdmin());
        Set<Peer> peersFromOrg = mySampleOrg.getPeers();
        numInstallProposal = numInstallProposal + peersFromOrg.size();
        responses = chain.sendInstallProposal(installProposalRequest, peersFromOrg);

        for (ProposalResponse response : responses) {
            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                System.out.println(format("Successful install proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName()));
                successful.add(response);
            } else {
                failed.add(response);
            }
        }
        //   }
        System.out.println(format("Received %d install proposal responses. Successful+verified: %d . Failed: %d", numInstallProposal, successful.size(), failed.size()));

        if (failed.size() > 0) {
            ProposalResponse first = failed.iterator().next();
            fail("Not enough endorsers for install :" + successful.size() + ".  " + first.getMessage());
        }

    }

    void instantiate(File policyFile, HFClient client, Chain chain, String[] args) throws Exception {
        Collection<ProposalResponse> responses;
        Collection<ProposalResponse> successful = new LinkedList<>();
        Collection<ProposalResponse> failed = new LinkedList<>();

        InstantiateProposalRequest instantiateProposalRequest = client.newInstantiationProposalRequest();
        instantiateProposalRequest.setChaincodeID(buildChainCodeID());
        instantiateProposalRequest.setFcn("init");
        instantiateProposalRequest.setArgs(args);

            /*
              policy OR(Org1MSP.member, Org2MSP.member) meaning 1 signature from someone in either Org1 or Org2
              See README.md Chaincode endorsement policies section for more details.
            */
        ChaincodeEndorsementPolicy chaincodeEndorsementPolicy = new ChaincodeEndorsementPolicy(policyFile);
        instantiateProposalRequest.setChaincodeEndorsementPolicy(chaincodeEndorsementPolicy);

        System.out.println("Sending instantiateProposalRequest to all peers with arguments: " + args.toString());
        successful.clear();
        failed.clear();

        responses = chain.sendInstantiationProposal(instantiateProposalRequest, chain.getPeers());
        for (ProposalResponse response : responses) {
            if (response.isVerified() && response.getStatus() == ProposalResponse.Status.SUCCESS) {
                successful.add(response);
                System.out.println(format("Succesful instantiate proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName()));
            } else {
                failed.add(response);
            }
        }
        System.out.println(format("Received %d instantiate proposal responses. Successful+verified: %d . Failed: %d", responses.size(), successful.size(), failed.size()));
        if (failed.size() > 0) {
            ProposalResponse first = failed.iterator().next();
            fail("Not enough endorsers for instantiate :" + successful.size() + "endorser failed with " + first.getMessage() + ". Was verified:" + first.isVerified());
        }
    }

    void invoke(HFClient client, Chain chain, String[] args, MySampleOrg mySampleOrg) throws Exception {
        Collection<ProposalResponse> successful = new LinkedList<>();
        Collection<ProposalResponse> failed = new LinkedList<>();

        client.setUserContext(mySampleOrg.getUser(TESTUSER_1_NAME));

        /// Send invoke proposal to all peers
        InvokeProposalRequest invokeProposalRequest = client.newInvokeProposalRequest();
        invokeProposalRequest.setChaincodeID(buildChainCodeID());
        invokeProposalRequest.setFcn("invoke");
        invokeProposalRequest.setArgs(args);
        System.out.println(format("sending invokeProposal to all peers with arguments", args));

        Collection<ProposalResponse> invokePropResp = chain.sendInvokeProposal(invokeProposalRequest, chain.getPeers());
        for (ProposalResponse response : invokePropResp) {
            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                System.out.println(format("Successful invoke proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName()));
                successful.add(response);
            } else {
                failed.add(response);
            }
        }
        System.out.println(format("Received %d invoke proposal responses. Successful+verified: %d . Failed: %d",
                invokePropResp.size(), successful.size(), failed.size()));
        if (failed.size() > 0) {
            ProposalResponse firstInvokeProposalResponse = failed.iterator().next();
            fail("Not enough endorsers :" + failed.size() + " endorser error: " +
                    firstInvokeProposalResponse.getMessage() +
                    ". Was verified: " + firstInvokeProposalResponse.isVerified());
        }
        System.out.println(format("Successfully received invoke proposal responses."));

        chain.sendTransaction(successful, chain.getOrderers()).thenApply(transactionEvent -> {
            assertTrue(transactionEvent.isValid());
            testTxID = transactionEvent.getTransactionID(); // used in the channel queries later
            System.out.println("testTxID: " + testTxID);

            return null;
        }).exceptionally(e -> {
            if (e instanceof TransactionEventException) {
                BlockEvent.TransactionEvent te = ((TransactionEventException) e).getTransactionEvent();
                if (te != null) {
                    fail(format("Transaction with txid %s failed. %s", te.getTransactionID(), e.getMessage()));
                }
            }
            fail(format("Test failed with %s exception %s", e.getClass().getName(), e.getMessage()));

            return null;
        }).get(120, TimeUnit.SECONDS);
    }

    void invoke(HFClient client, Chain chain, ArrayList<byte[]> args, MySampleOrg mySampleOrg) throws Exception {
        Collection<ProposalResponse> successful = new LinkedList<>();
        Collection<ProposalResponse> failed = new LinkedList<>();

        client.setUserContext(mySampleOrg.getUser(TESTUSER_1_NAME));

        /// Send invoke proposal to all peers
        InvokeProposalRequest invokeProposalRequest = client.newInvokeProposalRequest();
        invokeProposalRequest.setChaincodeID(buildChainCodeID());
        invokeProposalRequest.setFcn("invoke");
        invokeProposalRequest.setArgBytes(args);
        System.out.println(format("sending invokeProposal to all peers with arguments: move(a,b,100)"));

        Collection<ProposalResponse> invokePropResp = chain.sendInvokeProposal(invokeProposalRequest, chain.getPeers());
        for (ProposalResponse response : invokePropResp) {
            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                System.out.println(format("Successful invoke proposal response Txid: %s from peer %s", response.getTransactionID(), response.getPeer().getName()));
                successful.add(response);
            } else {
                failed.add(response);
            }
        }
        System.out.println(format("Received %d invoke proposal responses. Successful+verified: %d . Failed: %d",
                invokePropResp.size(), successful.size(), failed.size()));
        if (failed.size() > 0) {
            ProposalResponse firstInvokeProposalResponse = failed.iterator().next();
            fail("Not enough endorsers for invoke(move a,b,100):" + failed.size() + " endorser error: " +
                    firstInvokeProposalResponse.getMessage() +
                    ". Was verified: " + firstInvokeProposalResponse.isVerified());
        }
        System.out.println(format("Successfully received invoke proposal responses."));

        chain.sendTransaction(successful, chain.getOrderers()).thenApply(transactionEvent -> {
            assertTrue(transactionEvent.isValid());
            testTxID = transactionEvent.getTransactionID(); // used in the channel queries later
            System.out.println("testTxID: " + testTxID);

            return null;
        }).exceptionally(e -> {
            if (e instanceof TransactionEventException) {
                BlockEvent.TransactionEvent te = ((TransactionEventException) e).getTransactionEvent();
                if (te != null) {
                    fail(format("Transaction with txid %s failed. %s", te.getTransactionID(), e.getMessage()));
                }
            }
            fail(format("Test failed with %s exception %s", e.getClass().getName(), e.getMessage()));

            return null;
        }).get(120, TimeUnit.SECONDS);
    }


    void query(HFClient client, Chain chain, String[] args) throws Exception {
        try {
            // Send Query Proposal to all peers
            System.out.println(format("Now query chain code for the value of b."));
            QueryProposalRequest queryProposalRequest = client.newQueryProposalRequest();
            queryProposalRequest.setArgs(args);
            queryProposalRequest.setFcn("invoke");
            queryProposalRequest.setChaincodeID(buildChainCodeID());

            Collection<ProposalResponse> queryProposals = chain.sendQueryProposal(queryProposalRequest, chain.getPeers());
            for (ProposalResponse proposalResponse : queryProposals) {
                if (!proposalResponse.isVerified() || proposalResponse.getStatus() != ProposalResponse.Status.SUCCESS) {
                    fail("Failed query proposal from peer " + proposalResponse.getPeer().getName() + " status: " + proposalResponse.getStatus() +
                            ". Messages: " + proposalResponse.getMessage()
                            + ". Was verified : " + proposalResponse.isVerified());
                } else {
                    String payload = proposalResponse.getProposalResponse().getResponse().getPayload().toStringUtf8();
                    System.out.println(format("Query payload of b from peer %s returned %s", proposalResponse.getPeer().getName(), payload));
                }
            }
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (ProposalException e) {
            e.printStackTrace();
        }

        return;
    }

    public static void main(String[] args) {
        String chainCodeName = "example_map";
        String chainCodePath = "github.com/hyperledger/fabric/examples/chaincode/go/example_map";
        String chainCodeVersion = "1.0";
//
//        String chainCodeName = "example_map";
//        String chainCodePath = "github.com/example_map";
//        String chainCodeVersion = "1.0";
    }

    static void out(String format, Object... args) {

        System.err.flush();
        System.out.flush();

        System.out.println(format(format, args));
        System.err.flush();
        System.out.flush();

    }


}

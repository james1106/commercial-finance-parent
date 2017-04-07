package com.xiangan.platform.demo;

import com.xiangan.platform.demo.config.SdkConfig;
import org.hyperledger.fabric.sdk.Chain;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.ChainNotFoundException;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoPrimitives;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * chaincode test base class
 *
 * @Creater liuzhudong
 * @Date 2017/4/6 14:37
 * @Version 1.0
 * @Copyright
 */
public class ChainCodeBaseTest extends ConfigTest {

    public HFClient getClient() throws CryptoException,
            InvalidArgumentException, MalformedURLException, EnrollmentException {

        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
        logger.info("client init OK ................");

        HFCAClient caClient = new HFCAClient(orgConfig.getCaServerUrl(), null);
        client.setMemberServices(caClient);
        logger.info("ca service init OK ............");

        return client;
    }

    public HFClient setUserContext(User user, HFClient client) throws CryptoException,
            InvalidArgumentException, MalformedURLException, EnrollmentException {

        client.setUserContext(user);
        logger.info("set user context OK .............");

        return client;
    }

    public Chain getDefaultChain(HFClient client) throws InvalidArgumentException,
            TransactionException, IOException, ProposalException {

        String name = "foo";

        List<Orderer> orderers = new ArrayList<>();
        for (SdkConfig.OrdererConfig ordererConfig : config.getOrdererConfigs()) {
            orderers.add(demo.getOrderer(client, ordererConfig.getName(), ordererConfig.getUrl()));
        }

        List<Peer> peers = new ArrayList<>();
        List<EventHub> eventHubs = new ArrayList<>();
        for (SdkConfig.PeerConfig peerConfig : orgConfig.getPeers()) {
            peers.add(demo.getPeer(client, peerConfig.getName(), peerConfig.getUrl()));
            eventHubs.add(demo.getEventHub(client, peerConfig.getName(), peerConfig.getEvents()));
        }
        File txFile = new File("demo/e2e-2Orgs/channel/foo.tx");
        return getChain(name, txFile, orderers, peers, eventHubs, client);
    }

    public Chain getChain(String name,
                          File txFile,
                          List<Orderer> orderers,
                          List<Peer> peers,
                          List<EventHub> eventHubs,
                          HFClient client) throws InvalidArgumentException,
            TransactionException, IOException, ProposalException {

        Chain chain;
        try {
            chain = demo.getChain(name, orderers, peers, eventHubs, client);
        } catch (TransactionException e) {
            if (e instanceof ChainNotFoundException) {
                chain = demo.initChain(name, txFile, orderers, peers, eventHubs, client);
            } else {
                throw e;
            }

        }
        return chain;
    }


}

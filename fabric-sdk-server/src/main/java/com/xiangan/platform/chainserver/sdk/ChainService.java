package com.xiangan.platform.chainserver.sdk;

import org.hyperledger.fabric.sdk.Chain;
import org.hyperledger.fabric.sdk.ChainConfiguration;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * fabric chain service
 *
 * @Creater liuzhudong
 * @Date 2017/4/7 17:40
 * @Version 1.0
 * @Copyright
 */
//@Service
public class ChainService {

    /**
     * 初始化chain
     *
     * @param chain
     * @param orderers
     * @param peers
     * @param eventHubs
     * @throws InvalidArgumentException
     */
    private void initializeChain(Chain chain,
                                 List<Orderer> orderers,
                                 List<Peer> peers,
                                 List<EventHub> eventHubs,
                                 boolean joinPeer) throws InvalidArgumentException, TransactionException, ProposalException {
        for (Orderer orderer : orderers) {
            chain.addOrderer(orderer);
        }

        for (Peer peer : peers) {
            chain.addPeer(peer);
            if (joinPeer) {
                chain.joinPeer(peer);
            }
        }

        for (EventHub eventHub : eventHubs) {
            chain.addEventHub(eventHub);
        }
        chain.initialize();
    }


    /**
     * 获取chain
     *
     * @param name
     * @param orderers
     * @param peers
     * @param eventHubs
     * @param client
     * @return
     * @throws InvalidArgumentException
     */
    public Chain getChain(String name,
                          List<Orderer> orderers,
                          List<Peer> peers,
                          List<EventHub> eventHubs,
                          HFClient client) throws InvalidArgumentException, TransactionException, ProposalException {
        Chain chain = client.newChain(name);
        initializeChain(chain, orderers, peers, eventHubs, false);
        if (chain.isInitialized()) {
            return chain;
        }
        return null;
    }

    /**
     * 初始化化chain
     *
     * @param name
     * @param txFile
     * @param orderers
     * @param peers
     * @param eventHubs
     * @param client
     * @return
     * @throws InvalidArgumentException
     * @throws IOException
     * @throws TransactionException
     */
    public Chain initChain(String name,
                           File txFile,
                           List<Orderer> orderers,
                           List<Peer> peers,
                           List<EventHub> eventHubs,
                           HFClient client) throws InvalidArgumentException, IOException, TransactionException, ProposalException {

        Chain chain = client.newChain(name, orderers.iterator().next(), new ChainConfiguration(txFile));
        initializeChain(chain, orderers, peers, eventHubs, true);
        if (chain.isInitialized()) {
            return chain;
        }
        return null;
    }
}

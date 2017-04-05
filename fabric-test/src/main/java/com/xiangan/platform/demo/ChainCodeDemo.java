package com.xiangan.platform.demo;

import com.google.protobuf.AbstractMessage;
import com.xiangan.platform.demo.user.CaUser;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.Chain;
import org.hyperledger.fabric.sdk.ChainCodeID;
import org.hyperledger.fabric.sdk.ChainConfiguration;
import org.hyperledger.fabric.sdk.ChaincodeEndorsementPolicy;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.InstallProposalRequest;
import org.hyperledger.fabric.sdk.InstantiateProposalRequest;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionEventException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * chaincode test demo
 * Created by liuzhudong on 2017/3/31.
 */
public class ChainCodeDemo {

    static final String CHARSET = "UTF-8";
    final Logger logger = Logger.getLogger(ChainCodeDemo.class);

    /**
     * 获取用户信息
     *
     * @param name
     * @param passwd
     * @param mspid
     * @return
     * @throws EnrollmentException
     * @throws InvalidArgumentException
     */
    public User getUser(String name,
                        String passwd,
                        String mspid,
                        HFCAClient caClient) throws EnrollmentException, InvalidArgumentException {
        CaUser user = new CaUser(name);
        user.setEnrollment(caClient.enroll(name, passwd));
        user.setMSPID(mspid);
        return user;
    }

    /**
     * 注册用户
     *
     * @param name
     * @param affiliation
     * @param registrar
     * @param caClient
     * @return
     * @throws Exception
     */
    public String registerUser(String name,
                               String affiliation,
                               User registrar,
                               HFCAClient caClient) throws Exception {
        RegistrationRequest rr = new RegistrationRequest(name, affiliation);
        return caClient.register(rr, registrar);
    }

    /**
     * 获取Orderer
     *
     * @param client
     * @param name
     * @param grpcURL
     * @return
     * @throws InvalidArgumentException
     */
    public Orderer getOrderer(HFClient client, String name, String grpcURL) throws InvalidArgumentException {
        return client.newOrderer(name, grpcURL);
    }

    /**
     * 获取Peer
     *
     * @param client
     * @param name
     * @param grpcURL
     * @return
     * @throws InvalidArgumentException
     */
    public Peer getPeer(HFClient client, String name, String grpcURL) throws InvalidArgumentException {
        return client.newPeer(name, grpcURL);
    }

    /**
     * 获取EventHub
     *
     * @param client
     * @param name
     * @param grpcURL
     * @return
     * @throws InvalidArgumentException
     */
    public EventHub getEventHub(HFClient client, String name, String grpcURL) throws InvalidArgumentException {
        return client.newEventHub(name, grpcURL);
    }

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

        return ChainCodeID.newBuilder().setName(chainCodeName)
                .setVersion(chainCodeVersion)
                .setPath(chainCodePath).build();
    }

    /**
     * chaincode install
     *
     * @param chainCodeFile
     * @param chainCodeID
     * @param client
     * @param chain
     * @throws InvalidArgumentException
     * @throws ProposalException
     */
    public ProposalResponseResult install(File chainCodeFile,
                                          ChainCodeID chainCodeID,
                                          HFClient client,
                                          Chain chain) throws InvalidArgumentException, ProposalException {

        InstallProposalRequest installProposalRequest = client.newInstallProposalRequest();
        installProposalRequest.setChaincodeID(chainCodeID);
        installProposalRequest.setChaincodeSourceLocation(chainCodeFile);
        installProposalRequest.setChaincodeVersion(chainCodeID.getVersion());

        Set<Peer> peers = new HashSet<>(chain.getPeers());

        Collection<ProposalResponse> responses = chain.sendInstallProposal(installProposalRequest, peers);

        ProposalResponseResult result = new ProposalResponseResult();
        for (ProposalResponse response : responses) {

            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                result.addSuccessful(response);
            } else {
                result.addFailed(response);
            }
        }

        return result;
    }

    private ArrayList<byte[]> formatArgs(List<Object> args) throws UnsupportedEncodingException {
        ArrayList<byte[]> result = new ArrayList<>(args.size());
        for (Object arg : args) {
            if (arg instanceof String) {
                result.add(((String) arg).getBytes(CHARSET));
            } else if (arg instanceof AbstractMessage) {
                result.add(((AbstractMessage) arg).toByteArray());
            } else {
                // TODO....
                result.add(arg.toString().getBytes(CHARSET));
            }
        }
        return result;

    }

    public ProposalResponseResult instantiate(File policyFile,
                                              ArrayList<String> args,
                                              ChainCodeID chainCodeID,
                                              HFClient client,
                                              Chain chain) throws Exception {

        InstantiateProposalRequest instantiateProposalRequest = client.newInstantiationProposalRequest();
        instantiateProposalRequest.setChaincodeID(chainCodeID);
        instantiateProposalRequest.setFcn("init");
        instantiateProposalRequest.setArgs(args);
        instantiateProposalRequest.setProposalWaitTime(chain.getDeployWaitTime());

        if (policyFile != null) {
            ChaincodeEndorsementPolicy chaincodeEndorsementPolicy = new ChaincodeEndorsementPolicy(policyFile);
            instantiateProposalRequest.setChaincodeEndorsementPolicy(chaincodeEndorsementPolicy);
        }

        Collection<ProposalResponse> responses = chain.sendInstantiationProposal(instantiateProposalRequest, chain.getPeers());

        ProposalResponseResult result = new ProposalResponseResult();
        for (ProposalResponse response : responses) {

            if (response.isVerified() && response.getStatus() == ProposalResponse.Status.SUCCESS) {
                result.addSuccessful(response);
            } else {
                result.addFailed(response);
            }
        }

        if (result.isSuccess()) {
            // 返回成功才向orderer提交请求
            chain.sendTransaction(result.getSuccessful(), chain.getOrderers())
                    .exceptionally(e -> {
                        result.setThrowable(e);
                        if (e instanceof TransactionEventException) {
                            BlockEvent.TransactionEvent te = ((TransactionEventException) e).getTransactionEvent();
                            if (te != null) {
                                result.setTransactionEvent(te);
                            }
                        }
                        return null;
                    }).get(120, TimeUnit.SECONDS);
        }

        return result;

    }

    public ProposalResponseResult invoke(ChainCodeID chainCodeID,
                                         String[] args,
                                         HFClient client,
                                         Chain chain) throws Exception {
        TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chainCodeID);
        transactionProposalRequest.setFcn("invoke");
        transactionProposalRequest.setArgs(args);
        transactionProposalRequest.setProposalWaitTime(chain.getTransactionWaitTime());
        return invoke(transactionProposalRequest, chain);
    }

    public ProposalResponseResult invoke(ChainCodeID chainCodeID,
                                         List<Object> args,
                                         HFClient client,
                                         Chain chain) throws Exception {
        return invoke(chainCodeID, formatArgs(args), client, chain);
    }

    public ProposalResponseResult invoke(ChainCodeID chainCodeID,
                                         ArrayList<byte[]> args,
                                         HFClient client,
                                         Chain chain) throws Exception {
        TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chainCodeID);
        transactionProposalRequest.setFcn("invoke");
        transactionProposalRequest.setArgBytes(args);
        transactionProposalRequest.setProposalWaitTime(chain.getTransactionWaitTime());
        return invoke(transactionProposalRequest, chain);
    }


    private ProposalResponseResult invoke(TransactionProposalRequest transactionProposalRequest, Chain chain) throws Exception {

        Collection<ProposalResponse> responses = chain.sendTransactionProposal(transactionProposalRequest, chain.getPeers());

        final ProposalResponseResult result = new ProposalResponseResult();
        for (ProposalResponse response : responses) {
            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                result.addSuccessful(response);
            } else {
                result.addFailed(response);
            }
        }
        if (result.isSuccess()) {
            chain.sendTransaction(result.getSuccessful(), chain.getOrderers())
                    .thenApply(transactionEvent -> {
                        result.setTransactionEvent(transactionEvent);
                        return null;
                    }).exceptionally(e -> {
                result.setThrowable(e);
                if (e instanceof TransactionEventException) {
                    BlockEvent.TransactionEvent te = ((TransactionEventException) e).getTransactionEvent();
                    if (te != null) {
                        result.setTransactionEvent(te);
                    }
                }
                return null;
            }).get(120, TimeUnit.SECONDS);
        }
        return result;
    }

    public ProposalResponseResult query(ChainCodeID chainCodeID,
                                        String[] args,
                                        HFClient client,
                                        Chain chain) throws Exception {
        QueryByChaincodeRequest queryProposalRequest = client.newQueryProposalRequest();
        queryProposalRequest.setChaincodeID(chainCodeID);
        queryProposalRequest.setFcn("invoke");
        queryProposalRequest.setArgs(args);
        return query(queryProposalRequest, chain);
    }

    public ProposalResponseResult query(ChainCodeID chainCodeID,
                                        List<Object> args,
                                        HFClient client,
                                        Chain chain) throws Exception {
        return query(chainCodeID, formatArgs(args), client, chain);
    }

    public ProposalResponseResult query(ChainCodeID chainCodeID,
                                        ArrayList<byte[]> args,
                                        HFClient client,
                                        Chain chain) throws Exception {
        QueryByChaincodeRequest queryProposalRequest = client.newQueryProposalRequest();
        queryProposalRequest.setChaincodeID(chainCodeID);
        queryProposalRequest.setFcn("invoke");
        queryProposalRequest.setArgBytes(args);
        return query(queryProposalRequest, chain);
    }

    public ProposalResponseResult query(QueryByChaincodeRequest queryByChaincodeRequest,
                                        Chain chain) throws Exception {


        Collection<ProposalResponse> responses = chain.queryByChaincode(queryByChaincodeRequest, chain.getPeers());

        final ProposalResponseResult result = new ProposalResponseResult();
        for (ProposalResponse response : responses) {
            if (response.isVerified() && response.getStatus() == ProposalResponse.Status.SUCCESS) {
                result.addSuccessful(response);
            } else {
                result.addFailed(response);
            }
        }
        return result;
    }

}

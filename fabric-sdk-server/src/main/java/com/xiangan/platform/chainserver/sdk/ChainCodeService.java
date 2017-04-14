package com.xiangan.platform.chainserver.sdk;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xiangan.platform.chainserver.sdk.exception.InvokeChainCodeException;
import com.xiangan.platform.chainserver.sdk.exception.QueryChainCodeException;
import com.xiangan.platform.chainserver.sdk.exception.SendInstantiationProposalException;
import com.xiangna.www.protos.common.Common.ChainCodeResponse;
import org.hyperledger.fabric.sdk.Chain;
import org.hyperledger.fabric.sdk.ChainCodeID;
import org.hyperledger.fabric.sdk.ChaincodeEndorsementPolicy;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.InstallProposalRequest;
import org.hyperledger.fabric.sdk.InstantiateProposalRequest;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.exception.ChaincodeEndorsementPolicyParseException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * chaincode server
 *
 * @Creater liuzhudong
 * @Date 2017/4/7 17:59
 * @Version 1.0
 * @Copyright
 */
@Service
public class ChainCodeService {
    private static final String CHARSET = "UTF-8";

    private final Logger logger = LoggerFactory.getLogger(ChainCodeService.class);

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
    public List<ChainCodeResponse> install(File chainCodeFile,
                                           ChainCodeID chainCodeID,
                                           HFClient client,
                                           Chain chain)
            throws InvalidArgumentException,
            ProposalException,
            InvalidProtocolBufferException {

        InstallProposalRequest installProposalRequest = client.newInstallProposalRequest();
        installProposalRequest.setChaincodeID(chainCodeID);
        installProposalRequest.setChaincodeSourceLocation(chainCodeFile);
        installProposalRequest.setChaincodeVersion(chainCodeID.getVersion());

        Set<Peer> peers = new HashSet<>(chain.getPeers());

        Collection<ProposalResponse> responses = chain.sendInstallProposal(installProposalRequest, peers);

        if (responses == null) {
            return null;
        }

        List<ChainCodeResponse> results = new ArrayList<>(responses.size());
        for (ProposalResponse response : responses) {
            ByteString bs = response.getProposalResponse().getResponse().getPayload();
            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {

                results.add(ChainCodeResponse.parseFrom(bs));
            } else {
                logger.error("chaincode install 返回失败|peer={}|result={}|chainCodeID={}", response.getPeer().getName(),
                        bs.toStringUtf8(), chainCodeID);
            }
        }

        return results;
    }

    /**
     * 将cc操作参数转换成字节数组集合
     *
     * @param args
     * @return
     * @throws UnsupportedEncodingException
     */
    private ArrayList<byte[]> formatArgs(List<Object> args) throws UnsupportedEncodingException {
        ArrayList<byte[]> result = new ArrayList<>(args.size());
        for (Object arg : args) {
            if (arg instanceof byte[]) {
                result.add((byte[]) arg);
            } else if (arg instanceof String) {
                result.add(((String) arg).getBytes(CHARSET));
            } else if (arg instanceof AbstractMessage) {
                result.add(((AbstractMessage) arg).toByteArray());
            } else {
                result.add(arg.toString().getBytes(CHARSET));
            }
        }
        return result;

    }

    /**
     * 判断cc返回的结果是否成功
     *
     * @param bs
     * @return
     */
    boolean responseIsSuccess(ByteString bs) {
        ChainCodeResponse result = null;
        try {
            result = ChainCodeResponse.parseFrom(bs);
        } catch (InvalidProtocolBufferException e) {
            logger.warn("parse to ChainCodeResponse exception ", e);
            return false;
        }

        return result != null && 0 == result.getCode();
    }

    /**
     * 实例化chaincode并执行chaincode init方法
     *
     * @param policyFile
     * @param args
     * @param chainCodeID
     * @param client
     * @param chain
     * @return
     * @throws SendInstantiationProposalException
     * @throws IOException
     * @throws ChaincodeEndorsementPolicyParseException
     */
    public boolean instantiate(File policyFile,
                               ArrayList<String> args,
                               ChainCodeID chainCodeID,
                               HFClient client,
                               Chain chain)
            throws SendInstantiationProposalException, IOException, ChaincodeEndorsementPolicyParseException {

        InstantiateProposalRequest instantiateProposalRequest = client.newInstantiationProposalRequest();
        instantiateProposalRequest.setChaincodeID(chainCodeID);
        instantiateProposalRequest.setFcn("init");
        instantiateProposalRequest.setArgs(args);
        instantiateProposalRequest.setProposalWaitTime(chain.getDeployWaitTime());
        if (policyFile != null) {
            ChaincodeEndorsementPolicy chaincodeEndorsementPolicy = new ChaincodeEndorsementPolicy();
            chaincodeEndorsementPolicy.fromYamlFile(policyFile);
            instantiateProposalRequest.setChaincodeEndorsementPolicy(chaincodeEndorsementPolicy);
        }

        return instantiate(instantiateProposalRequest, new HashSet<>(chain.getPeers()), chain);
    }

    /**
     * 实例化chaincode并执行chaincode init方法
     *
     * @param instantiateProposalRequest
     * @param peers
     * @param chain
     * @return
     * @throws SendInstantiationProposalException
     */
    public boolean instantiate(InstantiateProposalRequest instantiateProposalRequest,
                               Set<Peer> peers,
                               Chain chain)
            throws SendInstantiationProposalException {
        Collection<ProposalResponse> responses;
        try {
            responses = chain.sendInstantiationProposal(instantiateProposalRequest, peers);
        } catch (InvalidArgumentException | ProposalException e) {
            throw new SendInstantiationProposalException("chaincode instantiate send transaction to peer ", e);
        }

        if (responses == null) {
            throw new SendInstantiationProposalException("chaincode instantiate send transaction to peer response is null");
        }

        Collection<ProposalResponse> successful = new LinkedList<>();
        ChainCodeResponse result;

        for (ProposalResponse response : responses) {
            if (response.isVerified() && response.getStatus() == ProposalResponse.Status.SUCCESS) {
                ByteString bs = response.getProposalResponse().getResponse().getPayload();
                try {
                    result = ChainCodeResponse.parseFrom(bs);
                    if (0 == result.getCode()) {
                        logger.debug("chaincode instantiate | OK | Txid={},peer={}", response.getTransactionID(),
                                response.getPeer().getName());
                        successful.add(response);
                        continue;
                    }

                    logger.warn("chaincode instantiate | Failed | Txid={},peer={},result={}", response.getTransactionID(),
                            response.getPeer().getName(), result.getMessage());
                } catch (InvalidProtocolBufferException e) {
                    logger.warn("chaincode instantiate | Failed parse to ChainCodeResponse | Txid={},peer={},result={}",
                            response.getTransactionID(), response.getPeer().getName(), bs.toStringUtf8());
                }
            } else {
                logger.warn("chaincode instantiate | Failed | Txid={},peer={},result={}", response.getTransactionID(),
                        response.getPeer().getName(), response.getMessage());
            }
        }

        if (successful.size() != responses.size()) {
            throw new SendInstantiationProposalException(String.format("chaincode instantiate send transaction to peer response not all success. (%s/%s)",
                    successful.size(), responses.size()));
        }

        final int gwtimes = chain.getDeployWaitTime();
        final TransactionResult transactionResult = new TransactionResult();
        transactionResult.setSuccess(true);

        // 返回成功才向orderer提交请求
        try {
            chain.sendTransaction(successful, chain.getOrderers()).thenApply(transactionEvent -> {
                if (!transactionEvent.isValid()) {
                    logger.debug("chaincode instantiate | transaction is not valid");
                    transactionResult.setSuccess(false);
                    return null;
                }

                logger.debug("chaincode instantiate | Finished instantiate transaction with transaction id {}",
                        transactionEvent.getTransactionID());

                try {
                    logger.debug("chaincode instantiate | Wait {} milliseconds for peers to sync with each other", gwtimes);
                    TimeUnit.MILLISECONDS.sleep(gwtimes);
                } catch (InterruptedException e) {
                    logger.error("chaincode instantiate | Wait {} milliseconds for peers to sync with each other error",
                            gwtimes, e);
                }
                return null;
            }).exceptionally(e -> {
                transactionResult.setSuccess(false);
                transactionResult.setThrowable(e);
                return null;
            }).get(120, TimeUnit.SECONDS);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            throw new SendInstantiationProposalException("chaincode instantiate transaction commit to order error", e);
        }

        if (transactionResult.getThrowable() != null) {
            throw new SendInstantiationProposalException("chaincode instantiate transaction commit to order error",
                    transactionResult.getThrowable());
        }
        return transactionResult.isSuccess();
    }

    /**
     * 执行chaincode方法（可读可写）
     *
     * @param transactionProposalRequest
     * @param chain
     * @return
     * @throws InvokeChainCodeException
     */
    private List<ChainCodeResponse> invoke(TransactionProposalRequest transactionProposalRequest, Chain chain)
            throws InvokeChainCodeException {

        Collection<ProposalResponse> responses;
        try {
            transactionProposalRequest.setProposalWaitTime(chain.getTransactionWaitTime());
            responses = chain.sendTransactionProposal(transactionProposalRequest, chain.getPeers());
        } catch (ProposalException | InvalidArgumentException e) {
            throw new InvokeChainCodeException("chaincode invoke send transaction to endorser", e);
        }

        if (responses == null) {
            throw new InvokeChainCodeException("chaincode invoke send transaction to endorser response is null");
        }

        Collection<ProposalResponse> successful = new LinkedList<>();
        List<ChainCodeResponse> oks = new LinkedList<>();
        ChainCodeResponse result;

        for (ProposalResponse response : responses) {
            ByteString bs = response.getProposalResponse().getResponse().getPayload();
            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                try {
                    result = ChainCodeResponse.parseFrom(bs);
                    if (0 == result.getCode()) {
                        logger.debug("chaincode invoke | send transaction to endorser| OK | Txid={},peer={}",
                                response.getTransactionID(), response.getPeer().getName());
                        oks.add(result);
                        successful.add(response);
                        continue;
                    }
                    logger.warn("chaincode invoke | send transaction to endorser | Failed | Txid={},peer={},result={}",
                            response.getTransactionID(), response.getPeer().getName(), result.getMessage());
                } catch (InvalidProtocolBufferException e) {
                    logger.warn("chaincode invoke | send transaction to endorser | Failed parse to ChainCodeResponse | Txid={},peer={},result={}",
                            response.getTransactionID(), response.getPeer().getName(), bs.toStringUtf8());
                }
            } else {
                logger.warn("chaincode invoke | send transaction to endorser | Failed | Txid={},peer={},result={}",
                        response.getTransactionID(), response.getPeer().getName(), response.getMessage());
            }

        }

        if (successful.size() != responses.size()) {
            throw new InvokeChainCodeException(String.format("chaincode invoke send transaction to endorser response not all success. (%s/%s)",
                    successful.size(), responses.size()));
        }
        result = null;
        for (ChainCodeResponse chainCodeResponse : oks) {
            if (result == null) {
                result = chainCodeResponse;
                continue;
            }

            if (!result.equals(chainCodeResponse)) {
                // 返回结果不一致
                throw new InvokeChainCodeException("chaincode invoke send transaction to endorser success response not same. ");
            }
        }

        // 提交操作数据到order共识
        final TransactionResult transactionResult = new TransactionResult();
        transactionResult.setSuccess(true);

        try {
            chain.sendTransaction(successful, chain.getOrderers()).exceptionally(e -> {
                transactionResult.setSuccess(false);
                transactionResult.setThrowable(e);
                return null;
            }).get(120, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new InvokeChainCodeException("chaincode invoke transaction commit to order error", e);
        }

        if (transactionResult.getThrowable() != null) {
            throw new InvokeChainCodeException("chaincode invoke transaction commit to order error", transactionResult.getThrowable());
        }

        return oks;
    }

    /**
     * 执行chaincode查询方法
     *
     * @param queryByChaincodeRequest
     * @param chain
     * @param peers
     * @return
     * @throws Exception
     */
    public List<ChainCodeResponse> query(QueryByChaincodeRequest queryByChaincodeRequest,
                                         Chain chain,
                                         Set<Peer> peers) throws QueryChainCodeException {

        Collection<ProposalResponse> responses;
        try {
            queryByChaincodeRequest.setProposalWaitTime(chain.getTransactionWaitTime());
            responses = chain.queryByChaincode(queryByChaincodeRequest, peers);
        } catch (InvalidArgumentException | ProposalException e) {
            throw new QueryChainCodeException("chaincode query to peers error", e);
        }

        if (responses == null) {
            throw new QueryChainCodeException("chaincode query to peers response is null");
        }

        List<ChainCodeResponse> oks = new LinkedList<>();
        ChainCodeResponse result;

        for (ProposalResponse response : responses) {
            if (response.getStatus() != ProposalResponse.Status.SUCCESS) {
                logger.warn("chaincode query | send transaction to peer | Failed | Txid={},peer={},result={}",
                        response.getTransactionID(), response.getPeer().getName(), response.getMessage());
                continue;
            }

            ByteString bs = response.getProposalResponse().getResponse().getPayload();
            try {
                result = ChainCodeResponse.parseFrom(bs);
                if (0 == result.getCode()) {
                    logger.debug("chaincode query | send transaction to peer| OK | Txid={},peer={}",
                            response.getTransactionID(), response.getPeer().getName());
                    oks.add(result);
                    continue;
                }
                logger.warn("chaincode query | send transaction to peer | Failed | Txid={},peer={},result={}",
                        response.getTransactionID(), response.getPeer().getName(), result.getMessage());
            } catch (InvalidProtocolBufferException e) {
                logger.warn("chaincode query | send transaction to peer | Failed parse to ChainCodeResponse | Txid={},peer={},result={}",
                        response.getTransactionID(), response.getPeer().getName(), bs.toStringUtf8());
            }

        }

        if (oks.size() != responses.size()) {
            throw new QueryChainCodeException(String.format("chaincode query send transaction to peer response not all success. (%s/%s)",
                    oks.size(), responses.size()));
        }

        return oks;
    }

    private TransactionProposalRequest generateTransactionProposalRequest(ChainCodeID chainCodeID,
                                                                          String[] argStrs,
                                                                          ArrayList<byte[]> args,
                                                                          HFClient client) {
        TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chainCodeID);
        transactionProposalRequest.setFcn("invoke");
        if (argStrs != null) {
            transactionProposalRequest.setArgs(argStrs);
        }
        if (args != null) {
            transactionProposalRequest.setArgBytes(args);
        }
        return transactionProposalRequest;
    }

    private QueryByChaincodeRequest generateQueryByChaincodeRequest(ChainCodeID chainCodeID,
                                                                    String[] argStrs,
                                                                    ArrayList<byte[]> args,
                                                                    HFClient client) {
        QueryByChaincodeRequest queryProposalRequest = client.newQueryProposalRequest();
        queryProposalRequest.setChaincodeID(chainCodeID);
        queryProposalRequest.setFcn("invoke");
        if (argStrs != null) {
            queryProposalRequest.setArgs(argStrs);
        }
        if (args != null) {
            queryProposalRequest.setArgBytes(args);
        }
        return queryProposalRequest;
    }

    public List<ChainCodeResponse> invoke(ChainCodeID chainCodeID,
                                          String[] args,
                                          HFClient client,
                                          Chain chain) throws Exception {
        return invoke(generateTransactionProposalRequest(chainCodeID, args, null, client), chain);
    }

    public List<ChainCodeResponse> invoke(ChainCodeID chainCodeID,
                                          List<Object> args,
                                          HFClient client,
                                          Chain chain) throws Exception {
        return invoke(chainCodeID, formatArgs(args), client, chain);
    }

    public List<ChainCodeResponse> invoke(ChainCodeID chainCodeID,
                                          ArrayList<byte[]> args,
                                          HFClient client,
                                          Chain chain) throws Exception {
        return invoke(generateTransactionProposalRequest(chainCodeID, null, args, client), chain);
    }


    public List<ChainCodeResponse> query(ChainCodeID chainCodeID,
                                         String[] args,
                                         HFClient client,
                                         Chain chain) throws Exception {
        return query(chainCodeID, args, client, chain, chain.getPeers());
    }

    public List<ChainCodeResponse> query(ChainCodeID chainCodeID,
                                         String[] args,
                                         HFClient client,
                                         Chain chain,
                                         Collection<Peer> peers) throws Exception {
        return query(generateQueryByChaincodeRequest(chainCodeID, args, null, client), chain, new HashSet<>(peers));
    }

    public List<ChainCodeResponse> query(ChainCodeID chainCodeID,
                                         List<Object> args,
                                         HFClient client,
                                         Chain chain) throws Exception {

        return query(chainCodeID, args, client, chain, chain.getPeers());
    }

    public List<ChainCodeResponse> query(ChainCodeID chainCodeID,
                                         List<Object> args,
                                         HFClient client,
                                         Chain chain,
                                         Collection<Peer> peers) throws Exception {
        return query(chainCodeID, formatArgs(args), client, chain, new HashSet<>(peers));
    }

    public List<ChainCodeResponse> query(ChainCodeID chainCodeID,
                                         ArrayList<byte[]> args,
                                         HFClient client,
                                         Chain chain,
                                         Set<Peer> peers) throws Exception {
        return query(generateQueryByChaincodeRequest(chainCodeID, null, args, client), chain, peers);
    }

}

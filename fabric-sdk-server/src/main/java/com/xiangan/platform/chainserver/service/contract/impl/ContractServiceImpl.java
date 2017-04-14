package com.xiangan.platform.chainserver.service.contract.impl;

import com.google.protobuf.ByteString;
import com.xiangan.platform.chainserver.api.contract.vo.request.ContractOrderRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.MortgageInvoice;
import com.xiangan.platform.chainserver.common.domain.BaseRequest;
import com.xiangan.platform.chainserver.common.entity.user.OrdererConfig;
import com.xiangan.platform.chainserver.common.entity.user.PeerConfig;
import com.xiangan.platform.chainserver.common.entity.user.UserInfo;
import com.xiangan.platform.chainserver.common.utils.DateUtil;
import com.xiangan.platform.chainserver.common.utils.FileUtil;
import com.xiangan.platform.chainserver.common.utils.IDUtil;
import com.xiangan.platform.chainserver.sdk.ChainCodeService;
import com.xiangan.platform.chainserver.sdk.ChainService;
import com.xiangan.platform.chainserver.sdk.SDKClientFactory;
import com.xiangan.platform.chainserver.service.contract.ContractService;
import com.xiangan.platform.chainserver.service.contract.constant.ContractConstant;
import com.xiangna.www.protos.common.Common;
import com.xiangna.www.protos.contract.Contract;
import com.xiangna.www.protos.contract.ContractRequest;
import com.xiangna.www.protos.contract.ContractRequest.ContractExcuteRequest;
import org.hyperledger.fabric.sdk.Chain;
import org.hyperledger.fabric.sdk.ChainCodeID;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 融资申请服务实现
 *
 * @Creater liuzhudong
 * @Date 2017/4/11 11:11
 * @Version 1.0
 * @Copyright
 */
@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ChainCodeService chainCodeService;

    @Autowired
    private ChainService chainService;

    /**
     * 构建操作记录
     *
     * @param request
     * @param userInfo
     * @param operate
     * @return
     */
    private Common.OperateInfo operate(BaseRequest request, UserInfo userInfo, String operate) {
        return Common.OperateInfo.newBuilder()
                .setAppId(request.getAppId())
//                .setUserId()
                .setCaAccount(userInfo.getUserAccount().getName())
                .setCert(userInfo.getUserAccount().getEnrollment().getCert())
                .setOperateDesc(operate)
                .setSourceIP(request.getSourceIP())
                .setOperateTime(DateUtil.toUnixTime(new Date()))
                .build();
    }

    @Override
    public void init(ContractOrderRequest request, UserInfo userInfo) throws Exception {

        String no = IDUtil.generateContractNO();
        String key = IDUtil.generateContractKey(new Date(), no);

        // 请求参数
        ContractExcuteRequest.Builder requestBuilder = ContractExcuteRequest.newBuilder();
        requestBuilder.setAction("init");
        requestBuilder.setContractKey(key);
        requestBuilder.setContractNO(no);
        ByteString[] playloads = new ByteString[4];

        // 合约表单数据
        playloads[0] = request.getContractData().convert().toByteString();

        // 合约发票数据
        if (request.getInvoices() != null && !request.getInvoices().isEmpty()) {
            List<Contract.Invoice> invoices = new ArrayList<>(request.getInvoices().size());
            for (MortgageInvoice invoice : request.getInvoices()) {
                invoices.add(invoice.convert());
            }
            playloads[1] = ContractRequest.InvoiceAddRequest.newBuilder().addAllInvoice(invoices).build().toByteString();
        }

        // 合约附件处理
        if (request.getAttas() != null && !request.getAttas().isEmpty()) {
            playloads[2] = FileUtil.convertData(request.getAttas()).toByteString();
        }

        // 合约操作数据
        playloads[3] = operate(request, userInfo, ContractConstant.OperateDesc.INIT).toByteString();

        List<ByteString> playload = new ArrayList<>(playloads.length);
        for (ByteString byteString : playloads) {
            if (byteString == null) {
                playload.add(ByteString.EMPTY);
                continue;
            }
            playload.add(byteString);
        }
        requestBuilder.addAllPlayload(playload);

        excute(request.getLedgerId(), requestBuilder.build(), userInfo);

    }

    private List<Common.ChainCodeResponse> excute(String chainName, ContractRequest.ContractExcuteRequest request, UserInfo userInfo) throws Exception {
        ArrayList<Object> bytes = new ArrayList<>(2);
        bytes.add("excute");
        bytes.add(request);
        return chainCodeExcute(chainName, true, bytes, userInfo);

    }

    private List<Common.ChainCodeResponse> query(String chainName, ContractRequest.ContractQueryRequest request, UserInfo userInfo) throws Exception {
        ArrayList<Object> bytes = new ArrayList<>(2);
        bytes.add("query");
        bytes.add(request);
        return chainCodeExcute(chainName, false, bytes, userInfo);
    }

    private List<Common.ChainCodeResponse> chainCodeExcute(String chainName,
                                                           boolean invokeFlag,
                                                           ArrayList<Object> args,
                                                           UserInfo userInfo) throws Exception {
        // 执行chaincode操作
        HFClient client = SDKClientFactory.getClient();

        HFCAClient memberServices = SDKClientFactory.getCaClient(userInfo.getUserAccount().getCaServerURL());
        client.setMemberServices(memberServices);

        // 设置操作用户
        client.setUserContext(userInfo.getUserAccount());

        List<Orderer> orderers = new ArrayList<>();
        for (OrdererConfig ordererConfig : userInfo.getOrderers()) {
            orderers.add(client.newOrderer(ordererConfig.getName(), ordererConfig.getUrl()));
        }

        List<Peer> peers = new ArrayList<>();
        List<EventHub> eventHubs = new ArrayList<>();
        for (PeerConfig peerConfig : userInfo.getPeers()) {
            peers.add(client.newPeer(peerConfig.getName(), peerConfig.getUrl()));
            eventHubs.add(client.newEventHub(peerConfig.getName(), peerConfig.getEventHub()));
        }

        Chain chain = chainService.getChain(chainName, orderers, peers, eventHubs, client);

        String chainCodeName = "contract_cc_go";
        String chainCodePath = "github.com/xncc/contract_cc";
        String chainCodeVersion = "1";

        ChainCodeID chainCodeID = chainCodeService.getChainCodeId(chainCodeName, chainCodePath, chainCodeVersion);
        chain.setDeployWaitTime(120000);
        chain.setTransactionWaitTime(120000);
        if (invokeFlag) {
            return chainCodeService.invoke(chainCodeID, args, client, chain);
        }
        return chainCodeService.query(chainCodeID, args, client, chain);
    }


}

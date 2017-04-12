package com.xiangan.platform.chainserver.service.contract.impl;

import com.xiangan.platform.chainserver.api.contract.vo.request.ContractInitRequest;
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
import com.xiangna.www.protos.contract.Contract;
import com.xiangna.www.protos.contract.ContractData;
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

    @Override
    public void init(ContractInitRequest contractInitRequest, UserInfo userInfo) throws Exception {

        long now = new Date().getTime() / 1000;

        // 合约表单数据
        ContractData.ContractFormData.Builder contractFormData = ContractData.ContractFormData.newBuilder();
        contractFormData.setExpectLoanAmount(contractInitRequest.getExpectLoanAmount());
        contractFormData.setExpectLoanPeriod(contractInitRequest.getExpectLoanPeriod());
        contractFormData.setExpectLoanRate(contractInitRequest.getExpectLoanRate());
        contractFormData.setExpectLoanEndTime(DateUtil.toUnixTime(DateUtil.addDays(new Date(),
                contractInitRequest.getExpectLoanPeriod())));

        // 合约发票数据
        List<Contract.Invoice> invoices = new ArrayList<>(contractInitRequest.getInvoices().size());
        for (ContractInitRequest.MortgageInvoice invoice : contractInitRequest.getInvoices()) {
            invoices.add(invoice.convert());
        }

        // 合约数据
        Contract.FinancingContract order = Contract.FinancingContract.newBuilder()
                .setOrderNo(IDUtil.generateContractNO())
                .setContractData(contractFormData.build())
                .addAllInvoices(invoices)
                .setOperateInfo(contractInitRequest.getOperateInfo())
                .build();

        // 请求参数
        ContractExcuteRequest.Builder requestBuilder = ContractExcuteRequest.newBuilder();
        requestBuilder.setAction("init");
        requestBuilder.setContractData(order.toByteString());

        // 合约附件处理
        ContractRequest.FileAddRequest fileAddRequest = FileUtil.convertData(order.getOrderNo(), contractInitRequest.getAttas());
        if (fileAddRequest != null) {
            requestBuilder.setContractFile(fileAddRequest.toByteString());
        }

        ArrayList<Object> bytes = new ArrayList<>(2);
        bytes.add("excute");
        bytes.add(requestBuilder.build());

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

        Chain chain = chainService.getChain(contractInitRequest.getLedgerId(), orderers, peers, eventHubs, client);

        String chainCodeName = "contract_cc_go";
        String chainCodePath = "github.com/xncc/contract_cc";
        String chainCodeVersion = "1";

        ChainCodeID chainCodeID = chainCodeService.getChainCodeId(chainCodeName, chainCodePath, chainCodeVersion);

        chainCodeService.invoke(chainCodeID, bytes, client, chain);
    }


}

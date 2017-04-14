package com.xiangan.platform.chainserver.service.contract.impl;

import com.google.protobuf.ByteString;
import com.xiangan.platform.chainserver.api.contract.vo.request.CheckFlowRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.CheckRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.ContractOrderQueryRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.ContractOrderRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.ExcuteRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.MortgageInvoice;
import com.xiangan.platform.chainserver.common.domain.BaseRequest;
import com.xiangan.platform.chainserver.common.entity.user.UserInfo;
import com.xiangan.platform.chainserver.common.utils.DateUtil;
import com.xiangan.platform.chainserver.common.utils.FileUtil;
import com.xiangan.platform.chainserver.common.utils.IDUtil;
import com.xiangan.platform.chainserver.sdk.ChainTemplate;
import com.xiangan.platform.chainserver.service.contract.ContractService;
import com.xiangan.platform.chainserver.service.contract.constant.ContractConstant;
import com.xiangna.www.protos.common.Common;
import com.xiangna.www.protos.contract.Contract;
import com.xiangna.www.protos.contract.ContractOrderStatus;
import com.xiangna.www.protos.contract.ContractRequest;
import com.xiangna.www.protos.contract.ContractRequest.ContractExcuteRequest;
import org.hyperledger.fabric.sdk.ChainCodeID;
import org.hyperledger.fabric.sdk.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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

    private final ChainTemplate chainTemplate;

//    @Value("${}")
    private String chainCodeName = "contract_cc_go";

//    @Value("${}")
    private String chainCodePath = "github.com/xncc/contract_cc";

//    @Value("${}")
    private String chainCodeVersion = "1";

    @Autowired
    public ContractServiceImpl(ChainTemplate chainTemplate) {
        this.chainTemplate = chainTemplate;
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
        requestBuilder.addAllPlayload(getPlayload(request, userInfo, ContractConstant.OperateDesc.INIT));

        excute(request.getLedgerId(), requestBuilder.build(), userInfo.getUserAccount());

    }

    @Override
    public void check(ContractOrderRequest request, UserInfo userInfo) throws Exception {

        // 请求参数
        ContractExcuteRequest.Builder requestBuilder = ContractExcuteRequest.newBuilder();
        requestBuilder.setAction("check");
        requestBuilder.setContractKey(request.getOrderKey());
        requestBuilder.addAllPlayload(getPlayload(request, userInfo, ContractConstant.OperateDesc.CHECK));

        excute(request.getLedgerId(), requestBuilder.build(), userInfo.getUserAccount());

    }

    @Override
    public void excute(ExcuteRequest request, UserInfo userInfo) throws Exception {
        // 请求参数
        ContractExcuteRequest.Builder requestBuilder = ContractExcuteRequest.newBuilder();
        requestBuilder.setAction("check");
        requestBuilder.setContractKey(request.getOrderKey());
        requestBuilder.setAction(request.getActionType().getCode());
        requestBuilder.addPlayload(operate(request, userInfo, request.getActionType().getMessage()).toByteString());
        // TODO 不同处理操作的具体实现
//        switch (request.getActionType()){
//            case SUBMIT:
//            case CHECKPASS:
//            case PAYLOANS:
//            case PAYOFFLOANS:
//            case NEXT:
//        }
        excute(request.getLedgerId(), requestBuilder.build(), userInfo.getUserAccount());
    }

    @Override
    public Contract.FinancingContract get(ContractOrderQueryRequest request, UserInfo userInfo) throws Exception {
        ContractRequest.ContractQueryRequest.Builder requestBuilder = ContractRequest.ContractQueryRequest.newBuilder();
        requestBuilder.setContractKey(request.getOrderKey());
        requestBuilder.setType(ContractRequest.ContractQueryRequest.QueryType.ALL)
                .build();
        ByteString bs = query(request.getLedgerId(), requestBuilder.build(), userInfo.getUserAccount());

        return Contract.FinancingContract.parseFrom(bs);
    }

    @Override
    public ByteString getFile(ContractOrderQueryRequest request, UserInfo userInfo) throws Exception {
        ContractRequest.ContractQueryRequest.Builder requestBuilder = ContractRequest.ContractQueryRequest.newBuilder();
        requestBuilder.setContractKey(request.getOrderKey());
        requestBuilder.setFileKey(request.getFileKey());
        requestBuilder.setType(ContractRequest.ContractQueryRequest.QueryType.FILE)
                .build();
        ByteString bs = query(request.getLedgerId(), requestBuilder.build(), userInfo.getUserAccount());
        Common.LedgerFileData data = Common.LedgerFileData.parseFrom(bs);
        return data.getData();
    }

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

    /**
     * 组装请求操作数据
     *
     * @param request
     * @param userInfo
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private List<ByteString> getPlayload(ContractOrderRequest request, UserInfo userInfo, String operateDesc) throws IOException, NoSuchAlgorithmException {
        ByteString[] playloads = new ByteString[6];

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
        playloads[3] = operate(request, userInfo, operateDesc).toByteString();

        // 流程审批记录
        if (request.getCheckFlow() != null && !request.getCheckFlow().isEmpty()) {
            List<ContractOrderStatus.ContractCheckFlowData> datas = new ArrayList<>(request.getCheckFlow().size());
            for (CheckFlowRequest data : request.getCheckFlow()) {
                datas.add(data.convert());
            }
            playloads[4] = ContractRequest.CheckFlowRequest.newBuilder().addAllCheckFlows(datas).build().toByteString();
        }

        // 确认检查项
        if (request.getCheck() != null && !request.getCheck().isEmpty()) {
            List<ContractOrderStatus.CheckData> datas = new ArrayList<>(request.getCheck().size());
            for (CheckRequest data : request.getCheck()) {
                datas.add(data.convert());
            }
            playloads[5] = ContractRequest.CheckRequest.newBuilder().addAllChecks(datas).build().toByteString();
        }

        List<ByteString> playload = new ArrayList<>(playloads.length);
        for (ByteString byteString : playloads) {
            if (byteString == null) {
                playload.add(ByteString.EMPTY);
                continue;
            }
            playload.add(byteString);
        }
        return playload;
    }

    private ByteString excute(String chainName, ContractRequest.ContractExcuteRequest request, User user) throws Exception {
        ArrayList<Object> bytes = new ArrayList<>(2);
        bytes.add("excute");
        bytes.add(request);
        ChainCodeID chainCodeID = chainTemplate.getChainCodeId(chainCodeName, chainCodePath, chainCodeVersion);
        return chainTemplate.invoke(chainName, user, chainCodeID, bytes);

    }

    private ByteString query(String chainName, ContractRequest.ContractQueryRequest request, User user) throws Exception {
        ArrayList<Object> bytes = new ArrayList<>(2);
        bytes.add("query");
        bytes.add(request);

        ChainCodeID chainCodeID = chainTemplate.getChainCodeId(chainCodeName, chainCodePath, chainCodeVersion);
        return chainTemplate.query(chainName, user, chainCodeID, bytes);
    }


}

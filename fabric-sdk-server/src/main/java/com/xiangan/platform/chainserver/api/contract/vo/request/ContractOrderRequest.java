package com.xiangan.platform.chainserver.api.contract.vo.request;

import com.xiangan.platform.chainserver.common.domain.BaseRequest;
import com.xiangan.platform.chainserver.common.domain.FileDataRequest;

import java.util.List;

/**
 * 融资申请订单请求参数
 *
 * @Creater liuzhudong
 * @Date 2017/4/12 19:33
 * @Version 1.0
 * @Copyright
 */
public class ContractOrderRequest extends BaseRequest {
    private static final long serialVersionUID = -2253591163401528839L;

    /**
     * 账本ID
     */
    private String ledgerId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单基本数据
     */
    private ContractDataRequest contractData;

    /**
     * 订单转账记录
     */
    private List<TransactionDataRequest> transactionData;

    /**
     * 订单确认项数据
     */
    private List<CheckRequest> check;

    /**
     * 订单审批数据
     */
    private List<CheckFlowRequest> checkFlow;

    /**
     * 附件
     */
    private List<FileDataRequest> attas;

    /**
     * 融资抵押发票
     */
    private List<MortgageInvoice> invoices;

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public ContractDataRequest getContractData() {
        return contractData;
    }

    public void setContractData(ContractDataRequest contractData) {
        this.contractData = contractData;
    }

    public List<TransactionDataRequest> getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(List<TransactionDataRequest> transactionData) {
        this.transactionData = transactionData;
    }

    public List<CheckRequest> getCheck() {
        return check;
    }

    public void setCheck(List<CheckRequest> check) {
        this.check = check;
    }

    public List<CheckFlowRequest> getCheckFlow() {
        return checkFlow;
    }

    public void setCheckFlow(List<CheckFlowRequest> checkFlow) {
        this.checkFlow = checkFlow;
    }

    public List<FileDataRequest> getAttas() {
        return attas;
    }

    public void setAttas(List<FileDataRequest> attas) {
        this.attas = attas;
    }

    public List<MortgageInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<MortgageInvoice> invoices) {
        this.invoices = invoices;
    }


}

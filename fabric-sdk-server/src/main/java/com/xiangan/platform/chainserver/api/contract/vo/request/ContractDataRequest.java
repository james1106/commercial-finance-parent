package com.xiangan.platform.chainserver.api.contract.vo.request;

import com.xiangan.platform.chainserver.common.domain.BaseRequest;
import com.xiangan.platform.chainserver.common.domain.FileDataRequest;

import java.util.List;

/**
 * 发起融资申请请求参数
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 10:33
 * @Version 1.0
 * @Copyright
 */
public class ContractInitRequest extends BaseRequest {
    private static final long serialVersionUID = 5622200304815466511L;

    /**
     * 账本ID
     */
    private String ledgerId;

    /**
     * 期望融资金额
     */
    private long expectLoanAmount;

    /**
     * 期望融资期限
     */
    private int expectLoanPeriod;

    /**
     * 期望融资到期日期
     */
    private long expectLoanEndTime;

    /**
     * 期望融资利率
     */
    private long expectLoanRate;

    /**
     * 确认融资利率
     */
    private long confirmLoanRate;

    /**
     * 确认融资金额
     */
    private long confirmLoanAmount;

    /**
     * 确认融资期限
     */
    private int confirmLoanPeriod;

    /**
     * 确认融资到期日期
     */
    private long confirmLoanEndTime;


    /**
     * 融资抵押发票
     */
    private List<MortgageInvoice> invoices;

    /**
     * 附件
     */
    private List<FileDataRequest> attas;

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public long getExpectLoanAmount() {
        return expectLoanAmount;
    }

    public void setExpectLoanAmount(long expectLoanAmount) {
        this.expectLoanAmount = expectLoanAmount;
    }

    public int getExpectLoanPeriod() {
        return expectLoanPeriod;
    }

    public void setExpectLoanPeriod(int expectLoanPeriod) {
        this.expectLoanPeriod = expectLoanPeriod;
    }

    public long getExpectLoanRate() {
        return expectLoanRate;
    }

    public void setExpectLoanRate(long expectLoanRate) {
        this.expectLoanRate = expectLoanRate;
    }

    public List<MortgageInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<MortgageInvoice> invoices) {
        this.invoices = invoices;
    }

    public List<FileDataRequest> getAttas() {
        return attas;
    }

    public void setAttas(List<FileDataRequest> attas) {
        this.attas = attas;
    }

    public long getExpectLoanEndTime() {
        return expectLoanEndTime;
    }

    public void setExpectLoanEndTime(long expectLoanEndTime) {
        this.expectLoanEndTime = expectLoanEndTime;
    }

    public long getConfirmLoanRate() {
        return confirmLoanRate;
    }

    public void setConfirmLoanRate(long confirmLoanRate) {
        this.confirmLoanRate = confirmLoanRate;
    }

    public long getConfirmLoanAmount() {
        return confirmLoanAmount;
    }

    public void setConfirmLoanAmount(long confirmLoanAmount) {
        this.confirmLoanAmount = confirmLoanAmount;
    }

    public int getConfirmLoanPeriod() {
        return confirmLoanPeriod;
    }

    public void setConfirmLoanPeriod(int confirmLoanPeriod) {
        this.confirmLoanPeriod = confirmLoanPeriod;
    }

    public long getConfirmLoanEndTime() {
        return confirmLoanEndTime;
    }

    public void setConfirmLoanEndTime(long confirmLoanEndTime) {
        this.confirmLoanEndTime = confirmLoanEndTime;
    }
}

package com.xiangan.platform.chainserver.api.contract.vo.response;

import com.xiangan.platform.chainserver.common.domain.BaseEntity;

/**
 * 融资申请列表返回数据
 *
 * @Creater liuzhudong
 * @Date 2017/4/13 16:07
 * @Version 1.0
 * @Copyright
 */
public class ContractListData extends BaseEntity {

    private static final long serialVersionUID = 5658615565539833164L;

    /**
     * 账本ID
     */
    private String ledgerId;

    /**
     * 数据存储key
     */
    private String contractKey;

    /**
     * 申请编号
     */
    private String no;

    /**
     * 融资总金额
     */
    private long loanAmount;

    /**
     * 融资期限
     */
    private int loadPeriod;

    /**
     * 融资截止日期
     */
    private long loanEndTime;

    /**
     * 融资状态
     *
     * @see com.xiangna.www.protos.contract.ContractOrderStatus.StatusType
     */
    private int status;

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getContractKey() {
        return contractKey;
    }

    public void setContractKey(String contractKey) {
        this.contractKey = contractKey;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(long loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getLoadPeriod() {
        return loadPeriod;
    }

    public void setLoadPeriod(int loadPeriod) {
        this.loadPeriod = loadPeriod;
    }

    public long getLoanEndTime() {
        return loanEndTime;
    }

    public void setLoanEndTime(long loanEndTime) {
        this.loanEndTime = loanEndTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

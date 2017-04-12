package com.xiangan.platform.chainserver.api.contract.vo.request;

import com.xiangan.platform.chainserver.common.domain.BaseEntity;
import com.xiangna.www.protos.common.Common;

/**
 * 机构银行账户
 *
 * @Creater liuzhudong
 * @Date 2017/4/12 18:52
 * @Version 1.0
 * @Copyright
 */
public class OrgBankAccountRequest extends BaseEntity {
    private static final long serialVersionUID = -7965270578771818982L;

    /**
     * 银行账户
     */
    private String account;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行分行
     */
    private String bankBranch;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public Common.OrgBankAccount convert() {
        return Common.OrgBankAccount.newBuilder()
                .setBankName(this.getBankName())
                .setBankBranch(this.getBankBranch())
                .setAccount(this.getAccount())
                .build();
    }
}

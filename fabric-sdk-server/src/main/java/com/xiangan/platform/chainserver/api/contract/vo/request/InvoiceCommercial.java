package com.xiangan.platform.chainserver.api.contract.vo.request;

import com.xiangan.platform.chainserver.common.domain.BaseEntity;
import com.xiangna.www.protos.contract.Contract;

/**
 * 发票公司机构信息
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 10:33
 * @Version 1.0
 * @Copyright
 */
public class InvoiceCommercial extends BaseEntity {
    private static final long serialVersionUID = 4087817977790409918L;

    /**
     * 名称
     */
    private String name;

    /**
     * 机构唯一识别码
     */
    private String key;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 开户银行名称
     */
    private String bankName;

    /**
     * 开户银行账户
     */
    private String bankAccount;

    public Contract.Invoice.InvoiceCommercial convert() {
        return Contract.Invoice.InvoiceCommercial.newBuilder()
                .setName(this.getName())
                .setKey(this.getKey())
                .setAddress(this.getAddress())
                .setBankName(this.getBankName())
                .setBankAccount(this.getBankAccount())
                .setPhone(this.getPhone())
                .build();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
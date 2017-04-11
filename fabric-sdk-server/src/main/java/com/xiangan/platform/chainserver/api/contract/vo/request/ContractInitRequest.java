package com.xiangan.platform.chainserver.api.contract.vo.request;

import com.xiangan.platform.chainserver.common.domain.BaseEntity;
import com.xiangan.platform.chainserver.common.domain.BaseRequest;
import com.xiangan.platform.chainserver.common.domain.FileDataRequest;

import java.util.ArrayList;
import java.util.Date;

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
    private double expectLoanAmount;

    /**
     * 期望融资期限
     */
    private int expectLoanPeriod;

    /**
     * 期望融资利率
     */
    private double expectLoanRate;

    /**
     * 融资抵押发票
     */
    private ArrayList<MortgageInvoice> invoices;

    /**
     * 附件
     */
    private ArrayList<FileDataRequest> attas;

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public double getExpectLoanAmount() {
        return expectLoanAmount;
    }

    public void setExpectLoanAmount(double expectLoanAmount) {
        this.expectLoanAmount = expectLoanAmount;
    }

    public int getExpectLoanPeriod() {
        return expectLoanPeriod;
    }

    public void setExpectLoanPeriod(int expectLoanPeriod) {
        this.expectLoanPeriod = expectLoanPeriod;
    }

    public double getExpectLoanRate() {
        return expectLoanRate;
    }

    public void setExpectLoanRate(double expectLoanRate) {
        this.expectLoanRate = expectLoanRate;
    }

    public ArrayList<MortgageInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<MortgageInvoice> invoices) {
        this.invoices = invoices;
    }

    public ArrayList<FileDataRequest> getAttas() {
        return attas;
    }

    public void setAttas(ArrayList<FileDataRequest> attas) {
        this.attas = attas;
    }

    /**
     * 融资抵押发票
     */
    public class MortgageInvoice extends BaseEntity {

        private static final long serialVersionUID = 9222062737133279784L;

        /**
         * 发票编号
         */
        private String invoiceNO;

        /**
         * 发票金额
         */
        private double invoiceAmount;

        /**
         * 销售方
         */
        private InvoiceCommercial seller;

        /**
         * 购买方
         */
        private InvoiceCommercial purchaser;

        /**
         * 发票备注
         */
        private String invoiceRemark;

        /**
         * 发票日期
         */
        private Date invoiceTime;

        /**
         * 期望融资金额
         */
        private double expectLoanAmount;

        /**
         * 期望融资利率
         */
        private double expectLoanRate;

        /**
         * 发票扫描件
         * <p>
         * Base64编码的图片字符串
         * </p>
         */
        private ArrayList<String> images;

        public String getInvoiceNO() {
            return invoiceNO;
        }

        public void setInvoiceNO(String invoiceNO) {
            this.invoiceNO = invoiceNO;
        }

        public double getInvoiceAmount() {
            return invoiceAmount;
        }

        public void setInvoiceAmount(double invoiceAmount) {
            this.invoiceAmount = invoiceAmount;
        }

        public InvoiceCommercial getSeller() {
            return seller;
        }

        public void setSeller(InvoiceCommercial seller) {
            this.seller = seller;
        }

        public InvoiceCommercial getPurchaser() {
            return purchaser;
        }

        public void setPurchaser(InvoiceCommercial purchaser) {
            this.purchaser = purchaser;
        }

        public String getInvoiceRemark() {
            return invoiceRemark;
        }

        public void setInvoiceRemark(String invoiceRemark) {
            this.invoiceRemark = invoiceRemark;
        }

        public Date getInvoiceTime() {
            return invoiceTime;
        }

        public void setInvoiceTime(Date invoiceTime) {
            this.invoiceTime = invoiceTime;
        }

        public double getExpectLoanAmount() {
            return expectLoanAmount;
        }

        public void setExpectLoanAmount(double expectLoanAmount) {
            this.expectLoanAmount = expectLoanAmount;
        }

        public double getExpectLoanRate() {
            return expectLoanRate;
        }

        public void setExpectLoanRate(double expectLoanRate) {
            this.expectLoanRate = expectLoanRate;
        }

        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }
    }

    /**
     * 发票公司机构信息
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

}

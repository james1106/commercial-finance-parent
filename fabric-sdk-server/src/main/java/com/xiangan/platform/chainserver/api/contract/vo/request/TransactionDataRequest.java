package com.xiangan.platform.chainserver.api.contract.vo.request;

import com.google.protobuf.ByteString;
import com.xiangan.platform.chainserver.common.domain.BaseEntity;
import com.xiangan.platform.chainserver.common.domain.BaseRequest;
import com.xiangan.platform.chainserver.common.utils.FileUtil;
import com.xiangan.platform.chainserver.common.utils.IDUtil;
import com.xiangna.www.protos.contract.ContractData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 金额转账请求参数
 *
 * @Creater liuzhudong
 * @Date 2017/4/12 18:50
 * @Version 1.0
 * @Copyright
 */
public class TransactionDataRequest extends BaseEntity {
    private static final long serialVersionUID = 708371598499560559L;

    /**
     * 支出方ID
     */
    private String expensesId;

    /**
     * 支出方信息
     */
    private String expenses;

    /**
     * 支出账户
     */
    private OrgBankAccountRequest expensesAccount;

    /**
     * 支出金额
     */
    private long expensesAmount;

    /**
     * 收入方ID
     */
    private String incomeId;

    /**
     * 收入方信息
     */
    private String income;

    /**
     * 收入账户
     */
    private OrgBankAccountRequest incomeAccount;

    /**
     * 转账备注
     */
    private String remark;

    /**
     * 发票扫描件
     * <p>
     * Base64编码的图片字符串
     * </p>
     */
    private List<String> images;

    /**
     * 转账时间(unix时间戳)
     */
    private long tansactionTime;

    /**
     * 收入方确认
     */
    private boolean incomConfirm = false;

    public ContractData.ContractTransactionDetail convert() throws IOException {
        List<ByteString> invoiceImages = null;
        if (images != null) {
            invoiceImages = new ArrayList<>(images.size());
            for (String data : images) {
                invoiceImages.add(FileUtil.decoderToByteString(data));
            }
        }

        return ContractData.ContractTransactionDetail.newBuilder()
                .setNo(IDUtil.generateTransactionNO())
                .setExpenses(this.getExpenses())
                .setExpensesAccount(this.getExpensesAccount().convert())
                .setExpensesAmount(this.getExpensesAmount())
                .setIncome(this.getIncome())
                .setIncomeAccount(this.getIncomeAccount().convert())
                .setTansactionRemark(this.getRemark())
                .setTansactionTime(this.getTansactionTime())
                .addAllTansactionNotes(invoiceImages)
                .setIncomConfirm(this.isIncomConfirm())
                .build();
    }

    public String getExpensesId() {
        return expensesId;
    }

    public void setExpensesId(String expensesId) {
        this.expensesId = expensesId;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public OrgBankAccountRequest getExpensesAccount() {
        return expensesAccount;
    }

    public void setExpensesAccount(OrgBankAccountRequest expensesAccount) {
        this.expensesAccount = expensesAccount;
    }

    public long getExpensesAmount() {
        return expensesAmount;
    }

    public void setExpensesAmount(long expensesAmount) {
        this.expensesAmount = expensesAmount;
    }

    public String getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public OrgBankAccountRequest getIncomeAccount() {
        return incomeAccount;
    }

    public void setIncomeAccount(OrgBankAccountRequest incomeAccount) {
        this.incomeAccount = incomeAccount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public long getTansactionTime() {
        return tansactionTime;
    }

    public void setTansactionTime(long tansactionTime) {
        this.tansactionTime = tansactionTime;
    }

    public boolean isIncomConfirm() {
        return incomConfirm;
    }

    public void setIncomConfirm(boolean incomConfirm) {
        this.incomConfirm = incomConfirm;
    }
}

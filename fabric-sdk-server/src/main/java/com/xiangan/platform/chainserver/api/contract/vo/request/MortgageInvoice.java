package com.xiangan.platform.chainserver.api.contract.vo.request;

import com.google.protobuf.ByteString;
import com.xiangan.platform.chainserver.common.domain.BaseEntity;
import com.xiangan.platform.chainserver.common.utils.FileUtil;
import com.xiangna.www.protos.contract.Contract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 融资抵押发票
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 10:33
 * @Version 1.0
 * @Copyright
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
    private long invoiceAmount;

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
    private long invoiceTime;

    /**
     * 期望融资金额
     */
    private long expectLoanAmount;

    /**
     * 期望融资利率
     */
    private long expectLoanRate;

    /**
     * 发票扫描件
     * <p>
     * Base64编码的图片字符串
     * </p>
     */
    private List<String> images;

    /**
     * 确认融资金额
     */
    private long confirmLoanAmount;

    /**
     * 确认融资利率
     */
    private long confirmLoanRate;

    /**
     * 保理融资发放日期(unix时间戳)
     */
    private long confirmPayTime;

    /**
     * 应收账款还款日期(unix时间戳)
     */
    private long confirmReturnTime;

    /**
     * 保理融资到期日期(unix时间戳)
     */
    private long confirmEndTime;

    /**
     * 利息计收方式
     */
    private String confirmRateDesc;

    /**
     * 应收账款催收方式
     */
    private String confirmUrgeReturnDesc;

    public Contract.Invoice convert() throws IOException {
        List<ByteString> invoiceImages = null;
        if (images != null) {
            invoiceImages = new ArrayList<>(images.size());
            for (String data : images) {
                invoiceImages.add(FileUtil.decoderToByteString(data));
            }
        }

        return Contract.Invoice.newBuilder()
                .setInvoiceNo(this.getInvoiceNO())
                .setInvoiceAmount(this.getInvoiceAmount())
                .setExpeLoanAmount(this.getExpectLoanAmount())
                .setExpeLoanRate(this.getExpectLoanRate())
                .setSeller(this.getSeller().convert())
                .setPurchaser(this.getPurchaser().convert())
                .setInvoiceRemark(this.getInvoiceRemark())
                .setInvoiceTime(this.getInvoiceTime())
                .addAllInvoiceImages(invoiceImages)
                .setConfirmLoanAmount(this.getConfirmLoanAmount())
                .setConfirmLoanRate(this.getConfirmLoanRate())
                .setConfirmEndTime(this.getConfirmEndTime())
                .setConfirmPayTime(this.getConfirmPayTime())
                .setConfirmRateDesc(this.getConfirmRateDesc())
                .setConfirmReturnTime(this.getConfirmReturnTime())
                .setConfirmUrgeReturnDesc(this.getConfirmUrgeReturnDesc())
                .build();
    }


    public String getInvoiceNO() {
        return invoiceNO;
    }

    public void setInvoiceNO(String invoiceNO) {
        this.invoiceNO = invoiceNO;
    }

    public long getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(long invoiceAmount) {
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

    public long getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(long invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public long getExpectLoanAmount() {
        return expectLoanAmount;
    }

    public void setExpectLoanAmount(long expectLoanAmount) {
        this.expectLoanAmount = expectLoanAmount;
    }

    public long getExpectLoanRate() {
        return expectLoanRate;
    }

    public void setExpectLoanRate(long expectLoanRate) {
        this.expectLoanRate = expectLoanRate;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public long getConfirmLoanAmount() {
        return confirmLoanAmount;
    }

    public void setConfirmLoanAmount(long confirmLoanAmount) {
        this.confirmLoanAmount = confirmLoanAmount;
    }

    public long getConfirmLoanRate() {
        return confirmLoanRate;
    }

    public void setConfirmLoanRate(long confirmLoanRate) {
        this.confirmLoanRate = confirmLoanRate;
    }

    public long getConfirmPayTime() {
        return confirmPayTime;
    }

    public void setConfirmPayTime(long confirmPayTime) {
        this.confirmPayTime = confirmPayTime;
    }

    public long getConfirmReturnTime() {
        return confirmReturnTime;
    }

    public void setConfirmReturnTime(long confirmReturnTime) {
        this.confirmReturnTime = confirmReturnTime;
    }

    public long getConfirmEndTime() {
        return confirmEndTime;
    }

    public void setConfirmEndTime(long confirmEndTime) {
        this.confirmEndTime = confirmEndTime;
    }

    public String getConfirmRateDesc() {
        return confirmRateDesc;
    }

    public void setConfirmRateDesc(String confirmRateDesc) {
        this.confirmRateDesc = confirmRateDesc;
    }

    public String getConfirmUrgeReturnDesc() {
        return confirmUrgeReturnDesc;
    }

    public void setConfirmUrgeReturnDesc(String confirmUrgeReturnDesc) {
        this.confirmUrgeReturnDesc = confirmUrgeReturnDesc;
    }
}
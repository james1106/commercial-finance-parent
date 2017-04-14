package com.xiangan.platform.chainserver.api.contract.vo.request;

import com.xiangan.platform.chainserver.common.domain.BaseRequest;

/**
 * 融资申请数据查询请求参数
 *
 * @Creater liuzhudong
 * @Date 2017/4/14 17:04
 * @Version 1.0
 * @Copyright
 */
public class ContractOrderQueryRequest extends BaseRequest {

    private static final long serialVersionUID = 1867847648596895157L;

    /**
     * 账本ID
     */
    private String ledgerId;

    /**
     * 订单存储KEY
     */
    private String orderKey;

    /**
     * 文件的sha256值
     */
    private String fileKey;

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }
}

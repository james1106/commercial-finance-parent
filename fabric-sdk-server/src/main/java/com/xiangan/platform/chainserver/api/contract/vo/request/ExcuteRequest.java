package com.xiangan.platform.chainserver.api.contract.vo.request;

import com.xiangan.platform.chainserver.common.domain.BaseRequest;
import com.xiangan.platform.chainserver.common.enums.contract.ContractExcuteActionType;

/**
 * 执行合约请求参数
 *
 * @Creater liuzhudong
 * @Date 2017/4/14 17:02
 * @Version 1.0
 * @Copyright
 */
public class ExcuteRequest extends BaseRequest {

    private static final long serialVersionUID = -3672984437544457803L;

    /**
     * 账本ID
     */
    private String ledgerId;

    /**
     * 订单存储KEY
     */
    private String orderKey;

    /**
     * 具体操作行为
     */
    private String action;

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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ContractExcuteActionType getActionType() {
        return ContractExcuteActionType.valueOfCode(this.action);
    }
}

package com.xiangan.platform.chainserver.api.contract.vo.request;

import com.xiangan.platform.chainserver.common.domain.BaseEntity;
import com.xiangna.www.protos.contract.ContractOrderStatus;

/**
 * 确认项参数
 *
 * @Creater liuzhudong
 * @Date 2017/4/12 19:19
 * @Version 1.0
 * @Copyright
 */
public class CheckRequest extends BaseEntity {
    private static final long serialVersionUID = -5762163791152275812L;

    /**
     * 确认项标题
     */
    private String title;

    /**
     * 确认结果
     */
    private String value;

    /**
     * 确认时间
     */
    private long checkTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }

    public ContractOrderStatus.CheckData convert() {
        return ContractOrderStatus.CheckData.newBuilder()
                .setTitle(this.getTitle())
                .setValue(this.getValue())
                .setCheckTime(this.getCheckTime())
                .build();
    }
}

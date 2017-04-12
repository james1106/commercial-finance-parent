package com.xiangan.platform.chainserver.api.contract.vo.request;

import com.xiangan.platform.chainserver.common.domain.BaseRequest;
import com.xiangna.www.protos.contract.ContractOrderStatus;

/**
 * 审批流程记录请求参数
 *
 * @Creater liuzhudong
 * @Date 2017/4/12 19:27
 * @Version 1.0
 * @Copyright
 */
public class CheckFlowRequest extends BaseRequest {
    private static final long serialVersionUID = -6862206855686912688L;

    /**
     * 审核操作员名称
     */
    private String checkUserName;

    /**
     * 审核操作员手机号
     */
    private String checkUserMobile;

    /**
     * 审核时间(unix时间戳)
     */
    private long checkTime;

    /**
     * 流程审批记录
     */
    private String checkRecord;

    /**
     * 流程审批备注
     */
    private String checkRemark;

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckUserMobile() {
        return checkUserMobile;
    }

    public void setCheckUserMobile(String checkUserMobile) {
        this.checkUserMobile = checkUserMobile;
    }

    public long getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckRecord() {
        return checkRecord;
    }

    public void setCheckRecord(String checkRecord) {
        this.checkRecord = checkRecord;
    }

    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
    }

    public ContractOrderStatus.ContractCheckFlowData convert() {
        return ContractOrderStatus.ContractCheckFlowData.newBuilder()
                .setCheckApp(this.getAppId())
                .setCheckUserName(this.getCheckUserName())
                .setCheckUserMobile(this.getCheckUserMobile())
                .setCheckTime(this.getCheckTime())
                .setCheckIp(this.getSourceIP())
                .setCheckRecord(this.getCheckRecord())
                .setCheckRemark(this.getCheckRemark())
                .build();
    }

}

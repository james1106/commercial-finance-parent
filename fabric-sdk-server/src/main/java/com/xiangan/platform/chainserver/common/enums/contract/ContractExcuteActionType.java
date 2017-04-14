package com.xiangan.platform.chainserver.common.enums.contract;

import org.springframework.util.StringUtils;

/**
 * 执行合约操作类型
 *
 * @Creater liuzhudong
 * @Date 2017/4/14 17:08
 * @Version 1.0
 * @Copyright
 */
public enum ContractExcuteActionType {
    // 此操作是将已经创建的融资申请提交,开始融资流程
    SUBMIT("submit", "发起融资申请"),

    // 此操作是将以经走完融资流程审核的融资订单改为放款状态,并做相应的处理
    CHECKPASS("checkPass", "融资申请审核通过"),

    // 此操作是将已全部放款的融资订单修改为还款状态,并做相应的处理
    PAYLOANS("payLoans", "完成放款"),

    // 此操作是将已全部还款的融资订单修改为结束状态,并做相应的处理
    PAYOFFLOANS("payOffLoans", "完成还款"),

    // 此操作是执行融资流程,融资流程是已经在三方确立时配置好的
    NEXT("next", "流转融资申请"),;

    private String code;

    private String message;

    ContractExcuteActionType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ContractExcuteActionType valueOfCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (ContractExcuteActionType type : ContractExcuteActionType.values()) {
            if (code.equals(type.getCode())) {
                return type;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

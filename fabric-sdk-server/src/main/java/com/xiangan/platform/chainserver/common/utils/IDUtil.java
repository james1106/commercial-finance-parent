package com.xiangan.platform.chainserver.common.utils;

import java.util.Date;
import java.util.UUID;

/**
 * ID、编号相关操作工具类
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 12:14
 * @Version 1.0
 * @Copyright
 */
public class IDUtil {

    /**
     * 生成融资申请订单在区块链上存储的key
     *
     * @return
     */
    public static String generateContractKey(Date date, String NO) {
        // contract_20170413_xxxx
        return "contract_" + DateUtil.format(date, "YYYYMMdd") + "_" + NO;
    }

    /**
     * 生成融资申请订单编号
     *
     * @return
     */
    public static String generateContractNO() {
        // TODO
        return UUID.randomUUID().toString();

    }

    /**
     * 生成转账流水编号
     *
     * @return
     */
    public static String generateTransactionNO() {
        // TODO
        return UUID.randomUUID().toString();
    }
}

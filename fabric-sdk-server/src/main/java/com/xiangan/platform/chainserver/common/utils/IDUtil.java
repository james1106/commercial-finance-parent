package com.xiangan.platform.chainserver.common.utils;

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
     * 生成融资申请订单编号
     *
     * @return
     */
    public static String generateContractNO() {
        return UUID.randomUUID().toString();

    }
}

package com.xiangan.platform.chainserver.service.contract;

import com.xiangan.platform.chainserver.api.contract.vo.request.ContractOrderRequest;
import com.xiangan.platform.chainserver.common.entity.user.UserInfo;

/**
 * 融资申请服务
 *
 * @Creater liuzhudong
 * @Date 2017/4/11 11:10
 * @Version 1.0
 * @Copyright
 */
public interface ContractService {

    /**
     * 发起融资申请
     *
     * @param request  融资申请数据
     * @param userInfo 用户信息
     * @throws Exception
     */
    void init(ContractOrderRequest request, UserInfo userInfo) throws Exception;
}

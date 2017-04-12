package com.xiangan.platform.chainserver.service.contract;

import com.xiangan.platform.chainserver.api.contract.vo.request.ContractInitRequest;
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
     * @param contractInitRequest
     * @param userInfo
     * @throws Exception
     */
    void init(ContractInitRequest contractInitRequest, UserInfo userInfo) throws Exception;
}

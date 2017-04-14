package com.xiangan.platform.chainserver.service.contract;

import com.google.protobuf.ByteString;
import com.xiangan.platform.chainserver.api.contract.vo.request.ContractOrderQueryRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.ContractOrderRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.ExcuteRequest;
import com.xiangan.platform.chainserver.common.entity.user.UserInfo;
import com.xiangna.www.protos.contract.Contract;

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
     * 创建融资申请
     *
     * @param request  融资申请数据
     * @param userInfo 用户信息
     * @throws Exception
     */
    void init(ContractOrderRequest request, UserInfo userInfo) throws Exception;

    /**
     * 融资申请流程审批&确认检查操作
     *
     * @param request
     * @param userInfo
     * @throws Exception
     */
    void check(ContractOrderRequest request, UserInfo userInfo) throws Exception;

    /**
     * 执行合约操作
     *
     * @param request
     * @param userInfo
     * @throws Exception
     */
    void excute(ExcuteRequest request, UserInfo userInfo) throws Exception;

    /**
     * 获取融资订单数据
     *
     * @param request
     * @param userInfo
     * @return
     * @throws Exception
     */
    Contract.FinancingContract get(ContractOrderQueryRequest request, UserInfo userInfo) throws Exception;

    /**
     * 获取融资订单附件的文件流
     *
     * @param request
     * @param userInfo
     * @return
     * @throws Exception
     */
    ByteString getFile(ContractOrderQueryRequest request, UserInfo userInfo) throws Exception;
}

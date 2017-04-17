package com.xiangan.platform.chainserver.api.contract.controller;

import com.xiangan.platform.chainserver.api.contract.vo.request.ContractOrderQueryRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.ContractOrderRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.ExcuteRequest;
import com.xiangan.platform.chainserver.api.contract.vo.response.ContractListData;
import com.xiangan.platform.chainserver.common.constant.ApiResponseConstant;
import com.xiangan.platform.chainserver.common.domain.BaseResult;
import com.xiangan.platform.chainserver.common.entity.user.UserInfo;
import com.xiangan.platform.chainserver.service.contract.ContractService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * 融资申请订单API
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 09:53
 * @Version 1.0
 * @Copyright
 */
@RestController
@RequestMapping("contract")
public class ContractController {

    @Resource
    private ContractService contractService;

    /**
     * 创建融资申请
     */
    @RequestMapping("init")
    public BaseResult init(ContractOrderRequest orderRequest, HttpServletRequest request) throws Exception {
        UserInfo userInfo = new UserInfo();
        System.out.println(userInfo.getUserAccount().getName());
        contractService.init(orderRequest, userInfo);
        return new BaseResult(ApiResponseConstant.SUCCESS);
    }

    /**
     * 融资申请流程审批&确认检查操作
     */
    @RequestMapping("check")
    public void check(ContractOrderRequest orderRequest) throws Exception {
        UserInfo userInfo = new UserInfo();
        contractService.check(orderRequest, userInfo);
    }

    /**
     * 融资放款还款转账登记
     */
    @RequestMapping("transactionEnroll")
    public void transactionEnroll(ContractOrderRequest orderRequest) throws Exception {
        UserInfo userInfo = new UserInfo();
        contractService.check(orderRequest, userInfo);
    }

    /**
     * 执行融资合约
     * <p>
     * 将按照设定好的融资申请流程执行
     * </p>
     */
    @RequestMapping("excute")
    public void excute(ExcuteRequest request) throws Exception {
        UserInfo userInfo = new UserInfo();
        contractService.excute(request, userInfo);
    }

    /**
     * 查询融资申请订单列表数据
     */
    @RequestMapping("list")
    public Object list(ContractOrderQueryRequest request) {

        // TODO 查询缓存
        return new ArrayList<ContractListData>();
    }

    /**
     * 查询融资申请订单数据
     */
    @RequestMapping("get")
    public void get(ContractOrderQueryRequest request) throws Exception {
        UserInfo userInfo = new UserInfo();
        contractService.get(request, userInfo);
    }

    /**
     * 查询融资申请订单的附件文件详情
     */
    @RequestMapping("getAttachmentFile")
    public void getAttachmentFile(ContractOrderQueryRequest request) throws Exception {
        UserInfo userInfo = new UserInfo();
        contractService.getFile(request, userInfo);
        // 文件流传输
    }


}

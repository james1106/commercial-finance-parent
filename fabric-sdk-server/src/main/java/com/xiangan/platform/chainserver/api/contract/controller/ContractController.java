package com.xiangan.platform.chainserver.api.contract.controller;

import com.xiangan.platform.chainserver.UserInfoFactory;
import com.xiangan.platform.chainserver.api.contract.vo.request.ContractInitRequest;
import com.xiangan.platform.chainserver.common.entity.user.UserInfo;
import com.xiangan.platform.chainserver.service.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    @Value("${org1.user1}")
    private String userInfoStr;

    @Autowired
    private ContractService contractService;

    /**
     * 发起融资申请
     */
    @RequestMapping("init")
    public void init(ContractInitRequest contractInitRequest, HttpServletRequest request) throws Exception {
        UserInfo userInfo = UserInfoFactory.restoreState(userInfoStr);
        System.out.println(userInfo.getUserAccount().getName());
        contractService.init(contractInitRequest, userInfo);
    }

    /**
     * 更新融资申请订单数据
     */
    @RequestMapping("modify")
    public void modify() {

    }

    /**
     * 执行融资合约
     * <p>
     * 将按照设定好的融资申请流程执行
     * </p>
     */
    @RequestMapping("excute")
    public void excute() {

    }

    /**
     * 新增OA流程审批记录
     */
    @RequestMapping("addOAFlowCheckRecord")
    public void addOAFlowCheckRecord() {

    }

    /**
     * 查询融资申请订单数据
     */
    @RequestMapping("get")
    public void get() {

    }

    /**
     * 查询融资申请订单的附件文件详情
     */
    @RequestMapping("getAttachmentFile")
    public void getAttachmentFile() {

    }


}

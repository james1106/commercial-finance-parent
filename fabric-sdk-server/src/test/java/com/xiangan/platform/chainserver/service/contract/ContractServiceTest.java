package com.xiangan.platform.chainserver.service.contract;

import com.xiangan.platform.chainserver.ApplicationTest;
import com.xiangan.platform.chainserver.UserInfoFactory;
import com.xiangan.platform.chainserver.api.contract.vo.request.ContractDataRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.ContractOrderRequest;
import com.xiangan.platform.chainserver.common.entity.user.UserInfo;
import com.xiangan.platform.chainserver.common.utils.DateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

/**
 * 融资申请订单服务
 *
 * @Creater liuzhudong
 * @Date 2017/4/13 19:09
 * @Version 1.0
 * @Copyright
 */
@TestPropertySource(locations = "classpath:config/userinfo.properties")
public class ContractServiceTest extends ApplicationTest {

    @Autowired
    private ContractService contractService;

    @Value("${org1.user1}")
    private String userInfoStr;

    @Test
    public void init() throws Exception {
        ContractOrderRequest orderRequest = new ContractOrderRequest();
        orderRequest.setAppId("xxxxxxxss");
        orderRequest.setSourceIP("1.1.1.1");

        orderRequest.setLedgerId("foo");

        // 订单数据
        ContractDataRequest dataRequest = new ContractDataRequest();
        dataRequest.setExpectLoanAmount(100_0000_000000L);
        dataRequest.setExpectLoanRate(600L);
        dataRequest.setExpectLoanPeriod(180);
        dataRequest.setExpectLoanEndTime(DateUtil.toUnixTime(DateUtil.addDays(new Date(), 180)));
        orderRequest.setContractData(dataRequest);

        UserInfo userInfo = UserInfoFactory.restoreState(userInfoStr);
        System.out.println(userInfo.getUserAccount().getName());
        contractService.init(orderRequest, userInfo);
    }

}
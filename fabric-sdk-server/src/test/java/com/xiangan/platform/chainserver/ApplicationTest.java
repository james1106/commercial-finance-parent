package com.xiangan.platform.chainserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xiangan.platform.chainserver.api.contract.vo.request.CheckFlowRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.CheckRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.ContractDataRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.ContractOrderRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.InvoiceCommercial;
import com.xiangan.platform.chainserver.api.contract.vo.request.MortgageInvoice;
import com.xiangan.platform.chainserver.api.contract.vo.request.OrgBankAccountRequest;
import com.xiangan.platform.chainserver.api.contract.vo.request.TransactionDataRequest;
import com.xiangan.platform.chainserver.api.contract.vo.response.ContractListData;
import com.xiangan.platform.chainserver.common.domain.FileDataRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 单元测试
 *
 * @Creater liuzhudong
 * @Date 2017/4/12 14:05
 * @Version 1.0
 * @Copyright
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {
    protected final Logger logger = LoggerFactory.getLogger(super.getClass());

    @Test
    public void configTest() throws Exception {
        logger.info("config test | OK ..............");

    }

//    @Test
//    public void gsonTest() throws Exception {
//        ContractOrderRequest request = new ContractOrderRequest();
//
//        ContractDataRequest data = new ContractDataRequest();
//        FileDataRequest fileDataRequest = new FileDataRequest();
//        MortgageInvoice invoice = new MortgageInvoice();
//        InvoiceCommercial invoiceCommercial = new InvoiceCommercial();
//
//        CheckFlowRequest checkFlowRequest = new CheckFlowRequest();
//        CheckRequest checkRequest = new CheckRequest();
//
//        TransactionDataRequest transactionDataRequest = new TransactionDataRequest();
//        OrgBankAccountRequest orgBankAccountRequest = new OrgBankAccountRequest();
//
//        ContractListData contractListData = new ContractListData();
//
//
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        System.out.println(gson.toJson(request));
//        System.out.println(gson.toJson(data));
//        System.out.println(gson.toJson(fileDataRequest));
//        System.out.println(gson.toJson(invoice));
//        System.out.println(gson.toJson(invoiceCommercial));
//        System.out.println(gson.toJson(checkFlowRequest));
//        System.out.println(gson.toJson(checkRequest));
//        System.out.println(gson.toJson(transactionDataRequest));
//        System.out.println(gson.toJson(orgBankAccountRequest));
//        System.out.println(gson.toJson(contractListData));
//
//    }
}
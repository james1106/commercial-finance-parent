package com.xiangan.platform.chainserver.sdk;

import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.MemberServices;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoPrimitives;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;

/**
 * sdk client service
 *
 * @Creater liuzhudong
 * @Date 2017/4/7 17:42
 * @Version 1.0
 * @Copyright
 */
@Service
public class SDKClientService {

    private final Logger logger = LoggerFactory.getLogger(SDKClientService.class);

    /**
     * 新建一个sdk客户端
     *
     * @return
     * @throws CryptoException
     * @throws InvalidArgumentException
     */
    public HFClient newClient() throws CryptoException, InvalidArgumentException {
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
        logger.info("client init OK ................");
        return client;
    }

    /**
     * 新建一个ca 服务客户端
     *
     * @param url
     * @return
     * @throws MalformedURLException
     */
    public MemberServices newCaClient(String url) throws MalformedURLException {
        return new HFCAClient(url, null);
    }
}

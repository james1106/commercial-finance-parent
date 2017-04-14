package com.xiangan.platform.chainserver;

import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoPrimitives;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

/**
 * fabric sdk client factory
 *
 * @Creater liuzhudong
 * @Date 2017/4/11 11:01
 * @Version 1.0
 * @Copyright
 */
public class SDKClientFactory {
    private static final HFClient SDK_CLIENT = HFClient.createNewInstance();

    private static final Logger logger = LoggerFactory.getLogger(SDKClientFactory.class);

    /**
     * 获取fabric sdk client
     *
     * @return
     */
    public static HFClient getClient() {
        if (SDK_CLIENT.getCryptoSuite() == null) {
            try {
                SDK_CLIENT.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
            } catch (CryptoException | InvalidArgumentException e) {
                logger.error("init fabric sdk client error", e);
            }
        }
        return SDK_CLIENT;
    }

    /**
     * 新建一个ca client
     *
     * @param url
     * @return
     */
    public static HFCAClient getCaClient(String url) {
        try {
            return new HFCAClient(url, null);
        } catch (MalformedURLException e) {
            logger.error("new ca client error", e);
            return null;
        }
    }
}

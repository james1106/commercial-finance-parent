package com.xiangan.platform.demo;

import com.google.gson.Gson;
import com.xiangan.platform.demo.config.SdkConfig;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * 服务配置
 *
 * @Creater liuzhudong
 * @Date 2017/4/6 14:30
 * @Version 1.0
 * @Copyright
 */
public class ConfigTest {

    protected Logger logger = Logger.getLogger(super.getClass());
    protected ChainCodeDemo demo;
    protected SdkConfig config;
    protected SdkConfig.OrgConfig orgConfig;


    @Before
    public void setUp() throws Exception {
        String configJson = "{\t\n" +
                "\t\"ordererConfigs\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"url\": \"grpc://localhost:7050\",\n" +
                "\t\t\t\"name\": \"orderer0\",\n" +
                "\t\t\t\"tlsCacerts\": \"../../fixtures/tls/orderer/ca-cert.pem\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"orgConfigs\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"peerOrg1\",\n" +
                "\t\t\t\"mspid\": \"Org1MSP\",\n" +
                "\t\t\t\"caServerUrl\": \"http://localhost:7054\",\n" +
                "\t\t\t\"peers\": [\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"url\": \"grpc://localhost:7051\",\n" +
                "\t\t\t\t\t\"events\": \"grpc://localhost:7053\",\n" +
                "\t\t\t\t\t\"name\": \"peer0\",\n" +
                "\t\t\t\t\t\"tlsCacerts\": \"../../fixtures/tls/peers/peer0/ca-cert.pem\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"url\": \"grpc://localhost:7056\",\n" +
                "\t\t\t\t\t\"events\": \"grpc://localhost:7058\",\n" +
                "\t\t\t\t\t\"name\": \"peer1\",\n" +
                "\t\t\t\t\t\"tlsCacerts\": \"../../fixtures/tls/peers/peer1/ca-cert.pem\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t]\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"peerOrg2\",\n" +
                "\t\t\t\"mspid\": \"Org2MSP\",\n" +
                "\t\t\t\"caServerUrl\": \"http://localhost:8054\",\n" +
                "\t\t\t\"peers\": [\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"url\": \"grpc://localhost:8051\",\n" +
                "\t\t\t\t\t\"events\": \"grpc://localhost:8053\",\n" +
                "\t\t\t\t\t\"name\": \"peer2\",\n" +
                "\t\t\t\t\t\"tlsCacerts\": \"../../fixtures/tls/peers/peer2/ca-cert.pem\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"url\": \"grpc://localhost:8056\",\n" +
                "\t\t\t\t\t\"events\": \"grpc://localhost:8058\",\n" +
                "\t\t\t\t\t\"name\": \"peer3\",\n" +
                "\t\t\t\t\t\"tlsCacerts\": \"../../fixtures/tls/peers/peer3/ca-cert.pem\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t]\n" +
                "\t\n" +
                "}";

        Gson gson = new Gson();
        config = gson.fromJson(configJson, SdkConfig.class);
        orgConfig = config.getOrgConfigs().iterator().next();
        logger.info("config init OK.................");
        demo = new ChainCodeDemo();
        logger.info("chaincode server init OK .............");
    }

    @Test
    public void configTest() throws Exception {
        logger.info("config test OK .................");

    }
}

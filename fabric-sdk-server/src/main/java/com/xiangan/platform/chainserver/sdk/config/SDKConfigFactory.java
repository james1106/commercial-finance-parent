package com.xiangan.platform.chainserver.sdk.config;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoPrimitives;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sdk配置信息
 *
 * @Creater liuzhudong
 * @Date 2017/4/13 17:39
 * @Version 1.0
 * @Copyright
 */
public class SDKConfigFactory {

    private static final Logger logger = LoggerFactory.getLogger(SDKConfigFactory.class);

    private static final HFClient SDK_CLIENT = HFClient.createNewInstance();

    private String sdkConfigPath;

    /**
     * chiancode 部署等待时间(ms)
     */
    private int deployWaitTime;

    /**
     * 交易请求等待时间(ms)
     */
    private int transactionWaitTime;

    /**
     * fabric orderer 节点
     */
    private List<Orderer> orderers = new ArrayList<>();

    /**
     * 组织信息
     */
    private Map<String, OrgInfo> orgInfoMap = new HashMap<>();

    public int getDeployWaitTime() {
        return deployWaitTime;
    }

    public int getTransactionWaitTime() {
        return transactionWaitTime;
    }

    public void setSdkConfigPath(String sdkConfigPath) {
        this.sdkConfigPath = sdkConfigPath;
    }

    public List<Orderer> getOrderers() {
        return orderers;
    }

    public List<Peer> getPeers(String mspid) {
        OrgInfo orgInfo = orgInfoMap.get(mspid);
        if (orgInfo == null) {
            return null;
        }
        return orgInfo.getPeers();
    }

    public List<EventHub> getEventHubs(String mspid) {
        OrgInfo orgInfo = orgInfoMap.get(mspid);
        if (orgInfo == null) {
            return null;
        }
        return orgInfo.getEventHubs();
    }

    /**
     * 获取ca服务
     *
     * @param mspid
     * @return
     * @throws MalformedURLException
     */
    public HFCAClient getCaService(String mspid) throws MalformedURLException {
        OrgInfo orgInfo = orgInfoMap.get(mspid);
        if (orgInfo == null) {
            return null;
        }
        return new HFCAClient(orgInfo.getCa(), null);
    }

    /**
     * 获取sdk操作客户端
     *
     * @return
     */
    public HFClient getClient() {
        return SDK_CLIENT;
    }

    /**
     * 初始化
     */
    public void init() throws Exception {
        // sdk client 初始化
        if (SDK_CLIENT.getCryptoSuite() == null) {
            SDK_CLIENT.setCryptoSuite(CryptoPrimitives.Factory.getCryptoSuite());
            logger.info("fabric sdk client 设置加密组件");
        }
        logger.info("fabric sdk client 初始化完成");

        // sdk 配置初始化
        File configJsonFile = new File(sdkConfigPath);
        String configJson = FileUtils.readFileToString(configJsonFile, "UTF-8");
        logger.info("已加载sdk配置json文件");

        Gson gson = new Gson();
        SdkConfigJson config = gson.fromJson(configJson, SdkConfigJson.class);

        initCommonConfig(config.getConfig());

        if (config.getOrderers() != null) {
            for (SdkConfigJson.OrdererConfig ordererConfig : config.getOrderers()) {
                initOrdererConfig(ordererConfig);
            }
            logger.info("初始化fabric orderer节点配置完成");
        }

        if (config.getOrgs() != null) {
            for (SdkConfigJson.OrgConfig orgConfig : config.getOrgs()) {
                initOrgConfig(orgConfig);
            }
            logger.info("初始化fabric ca,peer节点配置完成");
        }
    }

    private void initCommonConfig(SdkConfigJson.CommonConfig config) {
        this.deployWaitTime = config.getDeployWaitTime();
        this.transactionWaitTime = config.getTransactionWaitTime();
        logger.info("初始化sdk通用配置完成");
    }

    private void initOrdererConfig(SdkConfigJson.OrdererConfig config) throws InvalidArgumentException {
        this.orderers.add(getClient().newOrderer(config.getServer_hostname(), config.getUrl()));
    }

    private void initOrgConfig(SdkConfigJson.OrgConfig config) throws InvalidArgumentException {
        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setName(config.getName());
        orgInfo.setMspid(config.getMspid());
        orgInfo.setCa(config.getCa());
        this.orgInfoMap.put(orgInfo.getMspid(), orgInfo);

        if (config.getPeers() != null) {
            List<Peer> peers = new ArrayList<>(config.getPeers().size());
            List<EventHub> eventHubs = new ArrayList<>(config.getPeers().size());
            for (SdkConfigJson.PeerConfig peerConfig : config.getPeers()) {
                peers.add(getClient().newPeer(peerConfig.getServer_hostname(), peerConfig.getRequests()));
                eventHubs.add(getClient().newEventHub(peerConfig.getServer_hostname(), peerConfig.getEvents()));
            }
            orgInfo.setPeers(peers);
            orgInfo.setEventHubs(eventHubs);
        }
    }


}



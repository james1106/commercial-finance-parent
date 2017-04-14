package com.xiangan.platform.chainserver;

import com.xiangan.platform.chainserver.common.entity.user.OrdererConfig;
import com.xiangan.platform.chainserver.common.entity.user.PeerConfig;
import com.xiangan.platform.chainserver.common.entity.user.UserAccount;
import com.xiangan.platform.chainserver.common.entity.user.UserInfo;
import org.bouncycastle.util.encoders.Hex;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 用户信息创建（供测试）
 *
 * @Creater liuzhudong
 * @Date 2017/4/11 15:51
 * @Version 1.0
 * @Copyright
 */
public class UserInfoFactory {

    static String org = "org1";

    static String org_mspid = "Org1MSP";

    public static void main(String[] args) throws Exception {
        File cache = new File("fabric-sdk-server/src/main/resources/config/userinfo.properties");
        String ca = "http://localhost:7054";
        HFClient client = SDKClientFactory.getClient();
        HFCAClient hfcaClient = SDKClientFactory.getCaClient(ca);
        client.setMemberServices(hfcaClient);

        UserAccount admin = new UserAccount();
        admin.setName("admin");
        admin.setEnrollment(hfcaClient.enroll("admin", "adminpw"));

        Properties properties = new Properties();
        String userName = "user1";
        saveState(properties, org + "." + userName, getUserInfo(userName, ca, hfcaClient, admin));

        userName = "user2";
        saveState(properties, org + "." + userName, getUserInfo(userName, ca, hfcaClient, admin));

        userName = "user3";
        saveState(properties, org + "." + userName, getUserInfo(userName, ca, hfcaClient, admin));

        OutputStream out = new FileOutputStream(cache);
        properties.store(out, "");
        out.close();

    }

    public static UserInfo getUserInfo(String name, String ca, HFCAClient hfcaClient, UserAccount admin) throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setName(name);
        userAccount.setCaServerURL(ca);
        userAccount.setMSPID(org_mspid);
        
        RegistrationRequest rr = new RegistrationRequest(name, org);
        userAccount.setEnrollmentSecret(hfcaClient.register(rr, admin));

        userAccount.setEnrollment(hfcaClient.enroll(name, userAccount.getEnrollmentSecret()));

        List<OrdererConfig> ordererConfigs = new ArrayList<>(1);
        OrdererConfig ordererConfig = new OrdererConfig();
        ordererConfig.setName("orderer0");
        ordererConfig.setUrl("grpc://localhost:7050");
        ordererConfigs.add(ordererConfig);

        List<PeerConfig> peerConfigs = new ArrayList<>(2);
        PeerConfig peerConfig = new PeerConfig();
        peerConfig.setName("peer0");
        peerConfig.setUrl("grpc://localhost:7051");
        peerConfig.setEventHub("grpc://localhost:7053");
        peerConfigs.add(peerConfig);

//        peerConfig = new PeerConfig();
//        peerConfig.setName("peer1");
//        peerConfig.setUrl("grpc://localhost:7056");
//        peerConfig.setEventHub("grpc://localhost:7058");
//        peerConfigs.add(peerConfig);


        UserInfo userInfo = new UserInfo();
        userInfo.setOrderers(ordererConfigs);
        userInfo.setPeers(peerConfigs);
        userInfo.setUserAccount(userAccount);


        return userInfo;
    }

    /**
     * Save the state of this user to the key value store.
     */
    static void saveState(Properties properties, String key, UserInfo userInfo) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(userInfo);
            oos.flush();
            properties.setProperty(key, Hex.toHexString(bos.toByteArray()));
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restore the state of this user from the key value store (if found).  If not found, do nothing.
     */
    public static UserInfo restoreState(String data) {
        if (null != data) {
            // The user was found in the key value store, so restore the
            // state.
            byte[] serialized = Hex.decode(data);
            ByteArrayInputStream bis = new ByteArrayInputStream(serialized);
            try {
                ObjectInputStream ois = new ObjectInputStream(bis);
                UserInfo state = (UserInfo) ois.readObject();
                return state;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

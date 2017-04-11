package com.xiangan.platform.chainserver.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * Created by CL on 11/04/2017.
 */
public class AESUtil {

    private static SecretKey getSecretKey(String key) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("aes");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            keyGenerator.init(128, secureRandom);
            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Init Secret Key ERROR!");
        }
    }

    public static String AESEncrypt(String source, String skey) throws Exception {

        //1.生成key
        Key key = getSecretKey(skey);

        //2.加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//加解密方式+工作模式+填充方式
        cipher.init(Cipher.ENCRYPT_MODE, key);//以加密模式初始化
        byte[] result = cipher.doFinal(source.getBytes());
        System.out.println("JDK AES加密：" + Base64.encodeBase64String(result));
        return parseByte2HexStr(result);
    }


    public static byte[] AESDecrypt(byte[] result, String skey) throws Exception {
        //1.生成key
        Key key = getSecretKey(skey);

        //4.解密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//加解密方式+工作模式+填充方式
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] source = cipher.doFinal(result);
        System.out.println("JDK AES解密：" + new String(source));
        return source;
    }

    public static byte[] AESDecrypt(String result, String skey) throws Exception {
        //1.生成key
        Key key = getSecretKey(skey);

        //4.解密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//加解密方式+工作模式+填充方式
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] source = cipher.doFinal(parseHexStr2Byte(result));
        System.out.println("JDK AES解密：" + new String(source));
        return source;
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String sha256(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data);
        return parseByte2HexStr(md.digest()); // to HexString
    }

    public static void main(String[] args) throws Exception {
        String source = "香纳区块链+供应链金融";
        String skey = "3333333";
        String res = AESEncrypt(source, skey);
        System.out.println("加密后：" + res);

        byte[] decryptFrom = parseHexStr2Byte(res);
        AESDecrypt(res, skey);
        AESDecrypt(decryptFrom, skey);
    }
}

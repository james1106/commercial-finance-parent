package com.xiangan.platform.chainserver.common.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * Created by CL on 11/04/2017.
 * MD系列：HmacMD2、HmacMD4、HmacMD5
 * SHA系列：HmacSHA1、HmacSHA224、HmacSHA256、HmacSHA384、HmacSHA512
 */
public class HMACUtil {

    /**
     * @param source
     * @param key
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws DecoderException
     */
    public static byte[] HMACSHA256(String source, String key, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException, DecoderException{
        //1.得到密钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        SecretKey secretKey = keyGenerator.generateKey();//生成密钥
//        byte[] key = secretKey.getEncoded();//获得密钥
        byte[] decodeHex = Hex.decodeHex(key.toCharArray());

        //2.还原密钥
        SecretKey restoreSecretKey = new SecretKeySpec(decodeHex, algorithm);

        //3.信息摘要
        Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());//实例化mac
        mac.init(restoreSecretKey);//初始化mac
        byte[] hmacBytes = mac.doFinal(source.getBytes());//执行摘要

        System.out.println("jdkHmac:\t" + Hex.encodeHexString(hmacBytes));
        return hmacBytes;
    }

    /**
     * @deprecated
     * @param source
     * @param key
     * @return
     */
    public static byte[]  bouncyCastleHmacSHA256(String source, String key) {

        HMac hmac = new HMac(new SHA256Digest());
        //生成密钥的时候以aabbccddee为基准
        hmac.init(new KeyParameter(org.bouncycastle.util.encoders.Hex.decode(key)));
        hmac.update(source.getBytes(), 0, source.getBytes().length);

        // 执行摘要
        byte[]hmacSHA256Bytes = new byte[hmac.getMacSize()];
        hmac.doFinal(hmacSHA256Bytes, 0);

        System.out.println("bcHmac:\t"+org.bouncycastle.util.encoders.Hex.toHexString(hmacSHA256Bytes));

        return hmacSHA256Bytes;
    }


    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, DecoderException {
        String src = "object-oriente"; // 需要加密的原始字符串
        String key = "aabbccddee";
        String algorithm = "HmacSHA256";
        System.out.println("原始字符串：" + src + "\n");
        HMACSHA256(src, key, algorithm);
        bouncyCastleHmacSHA256(src, key);
    }
}

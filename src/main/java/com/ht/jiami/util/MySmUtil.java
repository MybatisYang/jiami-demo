package com.ht.jiami.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.security.KeyPair;
import java.util.Arrays;

/**
 * @Description: 加密工具类
 * @Author: yjs
 * @createTime: 2022年05月05日 10:01:22
 * @version: 1.0
 */
public class MySmUtil {

    public static SM2 sm2;
    private static final String ENCODING = "UTF-8";
    private static SymmetricCrypto sm4 = SmUtil.sm4();


    static {
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        sm2 = SmUtil.sm2(privateKey, publicKey);
    }

    /**
     * @Description: sm2公钥加密
     * @Date: 2022/5/5 10:24
     * @param text: 字符串
     * @return: java.lang.String
     **/
    public static String sm2Encode(String text){
        // 公钥加密
        String encryptStr2 = sm2.encryptBcd(text, KeyType.PublicKey);
//        System.out.println("sm2公钥加密：" + encryptStr2);
        return encryptStr2;
    }

    /**
     * @Description: sm2私钥解密
     * @Date: 2022/5/5 10:24
     * @param text: 字符串
     * @return: java.lang.String
     **/
    public static String sm2Decode(String text){
        //私钥解密
        String decryptStr2 = StrUtil.utf8Str(sm2.decryptFromBcd(text, KeyType.PrivateKey));
//        System.out.println("sm2私钥解密：" + decryptStr2);
        return decryptStr2;
    }

    /**
     * @Description: sm3加密
     * @Date: 2022/5/5 10:27
     * @param text: 字符串
     * @return: java.lang.String
     **/
    public static String sm3Encode(String text){
        String digestHex = SmUtil.sm3(text);
        System.out.println("加密后：" + digestHex);
        return digestHex;
    }

    /**
     * @Description: sm4加密
     * @Date: 2022/5/5 10:36
     * @param text: 字符串
     * @return: java.lang.String
     **/
    public static String sm4Encode(String text){
        String encryptHex = sm4.encryptHex(text);
//        System.out.println("加密后：" + encryptHex);
        return encryptHex;
    }

    /**
     * @Description: sm4解密
     * @Date: 2022/5/5 10:36
     * @param text: 字符串
     * @return: java.lang.String
     **/
    public static String sm4Decode(String text){
        String decryptStr = sm4.decryptStr(text, CharsetUtil.CHARSET_UTF_8);
//        System.out.println("解密后：" + decryptStr);
        return decryptStr;
    }

    /**
     * SM3加密方式之：不提供密钥的方式 SM3加密，返回加密后长度为64位的16进制字符串
     *
     * @param src 明文
     * @return
     */
    public static String encrypt(String src) {
        return ByteUtils.toHexString(getEncryptBySrcByte(src.getBytes()));
    }

    /**
     * 返回长度为32位的加密后的byte数组
     *
     * @param srcByte
     * @return
     */
    public static byte[] getEncryptBySrcByte(byte[] srcByte) {
        SM3Digest sm3 = new SM3Digest();
        sm3.update(srcByte, 0, srcByte.length);
        byte[] encryptByte = new byte[sm3.getDigestSize()];
        sm3.doFinal(encryptByte, 0);
        return encryptByte;
    }

    /**
     * 校验源数据与加密数据是否一致
     *
     * @param src       源数据
     * @param sm3HexStr 16进制的加密数据
     * @return
     * @throws Exception
     */
    public static boolean verify(String src, String sm3HexStr) throws Exception {
        byte[] sm3HashCode = ByteUtils.fromHexString(sm3HexStr);
        byte[] newHashCode = getEncryptBySrcByte(src.getBytes(ENCODING));
        return Arrays.equals(newHashCode, sm3HashCode);
    }
}

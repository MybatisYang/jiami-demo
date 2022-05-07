package com.ht.jiami.controller;

import com.ht.jiami.util.SM2Util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import org.apache.commons.codec.binary.Base64;


/**
 * @Description: test
 * @Author: yjs
 * @createTime: 2022年05月07日 10:30:16
 * @version: 1.0
 */
public class Test1 {
    public static void main(String[] args) {
        KeyPair keyPair = SM2Util.generateSm2KeyPair();
//        String privateKey = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
//        String publicKey  = Base64.encodeBase64String(keyPair.getPublic().getEncoded());

        String publicKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEwPRgjuwzzI39el4ZRW5mF9SJPLSgZ6Ff+K4mrUjyg3ZhAe6nmYXq4gRgcXYibCV6MYBS4uMuqRAMUYo3J/WZLg==";
        String privateKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgdAhmH+xPSMpT/wg+zvMVtWPfE83yGlrV2eqqGjafO8+gCgYIKoEcz1UBgi2hRANCAATA9GCO7DPMjf16XhlFbmYX1Ik8tKBnoV/4riatSPKDdmEB7qeZheriBGBxdiJsJXoxgFLi4y6pEAxRijcn9Zku";

        System.out.println("公钥："+publicKey);
        System.out.println("私钥："+privateKey);

        String data="123456";
        String encrypt = SM2Util.encrypt(publicKey,data);
        String decrypt = SM2Util.decrypt(privateKey, encrypt);

        System.out.println("加密后数据："+encrypt);
        System.out.println("解密后数据："+decrypt);
    }


    /**
     * SM2算法生成密钥对
     */
//    public static void generateSm2KeyPair() {
//        try {
//            final ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
//            // 获取一个椭圆曲线类型的密钥对生成器
//            final KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
//            SecureRandom random = new SecureRandom();
//            // 使用SM2的算法区域初始化密钥生成器
//            kpg.initialize(sm2Spec, random);
//            // 获取密钥对
//            KeyPair keyPair = kpg.generateKeyPair();
//            String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
//            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
//            System.out.println("私钥：" + privateKey);
//            System.out.println("公钥：" + publicKey);
//        } catch (Exception e) {
//            System.out.println("generate sm2 key pair failed:{}");
//        }
//    }
}

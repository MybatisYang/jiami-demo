package com.ht.jiami.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import sun.misc.BASE64Decoder;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Description: SM2Util
 * @Author: yjs
 * @createTime: 2022年05月07日 10:40:02
 * @version: 1.0
 */
@Slf4j
public class SM2Util {
    /**
     * SM2加密算法
     * @param publicKeyStr     公钥
     * @param data          明文数据
     * @return
     */
    public static String encrypt(String publicKeyStr, String data){
        Security.addProvider(new BouncyCastleProvider());
        PublicKey publicKey = null;
        try {
//            log.info("开始转换字符串公钥，公钥值：{},数据值:{}",publicKeyStr,data);
            byte[] keyBytes;
            keyBytes = (new BASE64Decoder()).decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            publicKey = keyFactory.generatePublic(keySpec);
//            log.info("转换后的公钥：{}",publicKey);
        } catch (Exception e) {
            log.error("SM2字符串公钥转换异常：{}",e.getMessage());
            e.printStackTrace();
        }
//        log.info("SM2开始加密数据");
        ECPublicKeyParameters ecPublicKeyParameters = null;
        if (publicKey instanceof BCECPublicKey) {
            BCECPublicKey bcecPublicKey = (BCECPublicKey) publicKey;
            ECParameterSpec ecParameterSpec = bcecPublicKey.getParameters();
            ECDomainParameters ecDomainParameters = new ECDomainParameters(ecParameterSpec.getCurve(),
                    ecParameterSpec.getG(), ecParameterSpec.getN());
            ecPublicKeyParameters = new ECPublicKeyParameters(bcecPublicKey.getQ(), ecDomainParameters);
        }
        SM2Engine sm2Engine = new SM2Engine();
        sm2Engine.init(true, new ParametersWithRandom(ecPublicKeyParameters, new SecureRandom()));
        byte[] arrayOfBytes = null;
        try {
            byte[] in = data.getBytes("utf-8");
            arrayOfBytes = sm2Engine.processBlock(in,0, in.length);
        } catch (Exception e) {
            log.error("SM2加密时出现异常:",e.getMessage());
            System.out.println("SM2加密时出现异常:");
        }
        return  Base64.encodeBase64String(arrayOfBytes);
    }

    /**
     * SM2解密算法
     * @param privateKeyStr        私钥
     * @param cipherData        密文数据
     * @return
     */
    public static String decrypt(String privateKeyStr, String cipherData){

        PrivateKey privateKey = null;
        byte[] keyBytes;
        try {
            keyBytes = (new BASE64Decoder()).decodeBuffer(privateKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            privateKey=keyFactory.generatePrivate(keySpec);
        }catch (Exception e){
            log.error("SM2字符串私钥转换异常：{}",e.getMessage());
            e.printStackTrace();
        }

        BCECPrivateKey bcecPrivateKey = (BCECPrivateKey) privateKey;
        ECParameterSpec ecParameterSpec = bcecPrivateKey.getParameters();
        ECDomainParameters ecDomainParameters = new ECDomainParameters(ecParameterSpec.getCurve(),
                ecParameterSpec.getG(), ecParameterSpec.getN());
        ECPrivateKeyParameters ecPrivateKeyParameters = new ECPrivateKeyParameters(bcecPrivateKey.getD(),
                ecDomainParameters);
        SM2Engine sm2Engine = new SM2Engine();
        sm2Engine.init(false, ecPrivateKeyParameters);
        String result = null;

        byte[] arrayOfBytes = null;
        try {
            byte[] in = Base64.decodeBase64(cipherData);
            arrayOfBytes = sm2Engine.processBlock(in,0, in.length);
            result=new String(arrayOfBytes, "utf-8");
        } catch (Exception e) {
            System.out.println("SM2解密时出现异常");
        }
        return result;
    }


    /**
     * SM2算法生成密钥对
     * @return 密钥对信息
     */
    public static KeyPair generateSm2KeyPair() {
        KeyPair keyPair = null;
        try {
            final ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
            // 获取一个椭圆曲线类型的密钥对生成器
            final KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            // 使用SM2的算法区域初始化密钥生成器
            kpg.initialize(sm2Spec, random);
            // 获取密钥对
            keyPair = kpg.generateKeyPair();
        } catch (Exception e) {
            log.error("generate sm2 key pair failed:{}", e.getMessage(), e);
        }
        return keyPair;
    }
}

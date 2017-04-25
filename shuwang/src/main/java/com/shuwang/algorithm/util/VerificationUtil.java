package com.shuwang.algorithm.util;

import org.apache.commons.lang3.ArrayUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by w景洋
 * Time 2016/10/10.
 */
public class VerificationUtil {
    
    /**
     * 生成随机密钥
     * @param size 位数
     * @return
     */
    public static String generateRandomKey(int size) {
        StringBuilder key = new StringBuilder();
        String chars = "0123456789ABCDEFHIJKLMNOPQISTUVWXYZ";
        for (int i = 0; i < size; i++) {
            int index = (int) (Math.random() * (chars.length() - 1));
            key.append(chars.charAt(index));
        }
        return key.toString();
    }

    /**
     * RSA签名
     * @param localPrivKey 私钥
     * @param plainBytes 需要签名的信息
     * @return byte[]
     * @throws Exception
     */
    public static byte[] signRSA(RSAPrivateKey localPrivKey, byte[] plainBytes, boolean useBase64Code, String charset) throws Exception {
        String SIGNATURE_ALGORITHM = "SHA1withRSA";
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(localPrivKey);
        signature.update(plainBytes);

        // 如果是Base64编码的话，需要对签名后的数组以Base64编码
        if (useBase64Code) {
            return AlgorithmBase64Util.encodeBASE64(signature.sign()).getBytes(charset);
        } else {
            return signature.sign();
        }
    }

    private static String BASE64 = "base64";
    private static String HEX = "hex";
    private final static byte[] hex = "0123456789abcdef".getBytes();

    /**
     * 签名函数入口
     * @param alg 算法
     * @param localPrivKey 私钥
     * @param plainBytes 需要签名的信息
     * @param encode 编码信息
     * @throws Exception
     */
    public static String sign(String alg, PrivateKey localPrivKey, byte[] plainBytes, String encode) throws Exception {
        Signature signature = Signature.getInstance(alg);
        signature.initSign(localPrivKey);
        signature.update(plainBytes);
        // 如果是Base64编码的话，需要对签名后的数组以Base64编码
        if (BASE64.equalsIgnoreCase(encode)) {
            return AlgorithmBase64Util.encodeBASE64(signature.sign());
        } else if (HEX.equalsIgnoreCase(encode)) {
            return bytesToHexString(signature.sign());
        } else {
            return signature.sign().toString();
        }
    }

    private static String bytesToHexString(byte[] b) {
        byte[] buff = new byte[2 * b.length];
        for (int i = 0; i < b.length; i++) {
            buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
            buff[2 * i + 1] = hex[b[i] & 0x0f];
        }
        return new String(buff);
    }

    /**
     * 验签操作
     * @param peerPubKey 公钥
     * @param plainBytes 需要验签的信息
     * @param signBytes 签名信息
     * @return boolean
     */
    public static boolean verifyRSA(RSAPublicKey peerPubKey, byte[] plainBytes, byte[] signBytes, boolean useBase64Code, String charset)
            throws Exception {
        boolean isValid = false;
        String SIGNATURE_ALGORITHM = "SHA1withRSA";
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(peerPubKey);
        signature.update(plainBytes);

        // 如果是Base64编码的话，需要对验签的数组以Base64解码
        if (useBase64Code) {
            isValid = signature.verify(AlgorithmBase64Util.decodeBASE64(new String(signBytes, charset)));
        } else {
            isValid = signature.verify(signBytes);
        }
        return isValid;
    }

    /**
     * 验签操作
     * @param alg 算法
     * @param peerPubKey 证书对象
     * @param plainBytes 需要验签的信息
     * @param signStr 签名信息
     * @param encode 编码信息
     * @return boolean
     */
    public static boolean verify(String alg, PublicKey peerPubKey, byte[] plainBytes, String signStr, String encode) throws Exception {
        boolean isValid = false;
        Signature signature = Signature.getInstance(alg);
        signature.initVerify(peerPubKey);
        signature.update(plainBytes);
        // 如果是Base64编码的话，需要对签名后的数组以Base64编码
        if (BASE64.equalsIgnoreCase(encode)) {
            isValid = signature.verify(AlgorithmBase64Util.decodeBASE64(signStr));
        } else if (HEX.equalsIgnoreCase(encode)) {
            isValid = signature.verify(hexStringToBytes(signStr));
        } else {
            isValid = signature.verify(signStr.getBytes());
        }
        return isValid;
    }

    private static byte[] hexStringToBytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a') {
            return (c - 'a' + 10) & 0x0f;
        }
        if (c >= 'A') {
            return (c - 'A' + 10) & 0x0f;
        }
        return (c - '0') & 0x0f;
    }

    /**
     * @param key
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] mac(byte[] key, byte[] content) throws Exception {
        byte[] macBytes = AlgorithmDesUtil.encodeDES(key, content);
        macBytes = ArrayUtils.subarray(macBytes, macBytes.length - 8, macBytes.length);
        return macBytes;
    }

}

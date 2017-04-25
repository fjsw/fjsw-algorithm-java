package com.shuwang.algorithm.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

/**
 * Created by w景洋
 * Time 2017/2/25.
 */

public class AlgorithmDesUtil {
    
    /**
     * 3DES加密
     * @param key 密钥信息
     * @param content 待加密信息
     * @return
     * @throws Exception
     */
    public static byte[] encode3DES(byte[] key, byte[] content) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // 不是8的倍数的，补足
        if (key.length % 8 != 0) {
            int groups = key.length / 8 + (key.length % 8 != 0 ? 1 : 0);
            byte[] temp = new byte[groups * 8];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(key, 0, temp, 0, key.length);
            key = temp;
        }
        // 长度为16位，转换成24位的密钥
        if (key.length == 16) {
            byte[] temp = new byte[24];
            System.arraycopy(key, 0, temp, 0, key.length);
            System.arraycopy(key, 0, temp, key.length, temp.length - key.length);
            key = temp;
        }

        // 不是8的倍数的，补足
        byte[] srcBytes = content;
        if (srcBytes.length % 8 != 0) {
            int groups = content.length / 8 + (content.length % 8 != 0 ? 1 : 0);
            srcBytes = new byte[groups * 8];
            Arrays.fill(srcBytes, (byte) 0);
            System.arraycopy(content, 0, srcBytes, 0, content.length);
        }

        SecretKey deskey = new SecretKeySpec(key, "DESede");
        Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] temp = cipher.doFinal(srcBytes);
        byte[] tgtBytes = new byte[content.length];
        System.arraycopy(temp, 0, tgtBytes, 0, tgtBytes.length);
        return tgtBytes;
    }

    /**
     * 3DES解密
     * @param key 密钥
     * @param content 待解密信息
     * @return
     * @throws Exception
     */
    public static byte[] decode3DES(byte[] key, byte[] content) throws Exception {
        // 不是8的倍数的，补足
        if (key.length % 8 != 0) {
            int groups = key.length / 8 + (key.length % 8 != 0 ? 1 : 0);
            byte[] temp = new byte[groups * 8];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(key, 0, temp, 0, key.length);
            key = temp;
        }
        // 长度为16位，转换成24位的密钥
        if (key.length == 16) {
            byte[] temp = new byte[24];
            System.arraycopy(key, 0, temp, 0, key.length);
            System.arraycopy(key, 0, temp, key.length, temp.length - key.length);
            key = temp;
        }

        // 不是8的倍数的，补足
        byte[] srcBytes = content;
        if (srcBytes.length % 8 != 0) {
            int groups = content.length / 8 + (content.length % 8 != 0 ? 1 : 0);
            srcBytes = new byte[groups * 8];
            Arrays.fill(srcBytes, (byte) 0);
            System.arraycopy(content, 0, srcBytes, 0, content.length);
        }

        SecretKey deskey = new SecretKeySpec(key, "DESede");
        Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        byte[] tgtBytes = cipher.doFinal(srcBytes);
        return tgtBytes;
    }

    /**
     * DES加密
     * @param key 密钥信息
     * @param content 待加密信息
     * @return
     * @throws Exception
     */
    public static byte[] encodeDES(byte[] key, byte[] content) throws Exception {
        // 不是8的倍数的，补足
        if (key.length % 8 != 0) {
            int groups = key.length / 8 + (key.length % 8 != 0 ? 1 : 0);
            byte[] temp = new byte[groups * 8];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(key, 0, temp, 0, key.length);
            key = temp;
        }

        // 不是8的倍数的，补足
        byte[] srcBytes = content;
        if (srcBytes.length % 8 != 0) {
            int groups = content.length / 8 + (content.length % 8 != 0 ? 1 : 0);
            srcBytes = new byte[groups * 8];
            Arrays.fill(srcBytes, (byte) 0);
            System.arraycopy(content, 0, srcBytes, 0, content.length);
        }

        IvParameterSpec iv = new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv, sr);
        byte[] tgtBytes = cipher.doFinal(srcBytes);
        return tgtBytes;
    }

    /**
     * DES解密
     * @param key 密钥信息
     * @param content 待加密信息
     * @return
     * @throws Exception
     */
    public static byte[] decodeDES(byte[] key, byte[] content) throws Exception {
        // 不是8的倍数的，补足
        if (key.length % 8 != 0) {
            int groups = key.length / 8 + (key.length % 8 != 0 ? 1 : 0);
            byte[] temp = new byte[groups * 8];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(key, 0, temp, 0, key.length);
            key = temp;
        }
        // 不是8的倍数的，补足
        byte[] srcBytes = content;
        if (srcBytes.length % 8 != 0) {
            int groups = content.length / 8 + (content.length % 8 != 0 ? 1 : 0);
            srcBytes = new byte[groups * 8];
            Arrays.fill(srcBytes, (byte) 0);
            System.arraycopy(content, 0, srcBytes, 0, content.length);
        }

        IvParameterSpec iv = new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv, sr);
        byte[] tgtBytes = cipher.doFinal(content);
        return tgtBytes;
    }

}

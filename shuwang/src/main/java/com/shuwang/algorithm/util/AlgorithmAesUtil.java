package com.shuwang.algorithm.util;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by w景洋
 * Time 2017/2/25.
 */

public class AlgorithmAesUtil {
  
    /**
     * AES加密
     * @param plainBytes 明文字节数组
     * @param keyBytes 对称密钥字节数组
     * @param useBase64Code 是否使用Base64编码
     * @param charset 编码格式
     * @return byte[]
     */
    public static byte[] encryptAES(byte[] plainBytes, byte[] keyBytes, boolean useBase64Code, String charset) throws Exception {
        String cipherAlgorithm = "AES/ECB/PKCS5Padding";
        String keyAlgorithm = "AES";
        String IV = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
            if(IV != null && StringUtils.trimToNull(IV) != null){
                IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            }else{
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }
            byte[] encryptedBlock = cipher.doFinal(plainBytes);

            if (useBase64Code) {
                return AlgorithmBase64Util.encodeBASE64(encryptedBlock).getBytes(charset);
            } else {
                return encryptedBlock;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("AES加密失败");
        }
    }

    /**
     * AES解密
     * @param cryptedBytes 密文字节数组
     * @param keyBytes 对称密钥字节数组
     * @param useBase64Code 是否使用Base64编码
     * @param charset 编码格式
     * @return byte[]
     */
    public static byte[] decryptAES(byte[] cryptedBytes, byte[] keyBytes, boolean useBase64Code, String charset) throws Exception {
        String cipherAlgorithm = "AES/ECB/PKCS5Padding";
        String keyAlgorithm = "AES";
        String IV = null;
        byte[] data = null;
        // 如果是Base64编码的话，则要Base64解码
        if (useBase64Code) {
            data = AlgorithmBase64Util.decodeBASE64(new String(cryptedBytes, charset));
        } else {
            data = cryptedBytes;
        }
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            SecretKey secretKey = new SecretKeySpec(keyBytes, keyAlgorithm);
            if(IV != null && StringUtils.trimToNull(IV) != null){
                IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            }else{
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            }
            byte[] decryptedBlock = cipher.doFinal(data);
            return decryptedBlock;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("AES解密失败");
        }
    }

}

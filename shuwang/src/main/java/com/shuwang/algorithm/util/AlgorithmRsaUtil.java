package com.shuwang.algorithm.util;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by w景洋
 * Time 2017/2/25.
 */

public class AlgorithmRsaUtil {
    
    /**
     * RAS加密
     * @param peerPubKey 公钥
     * @param data 待加密信息
     * @return byte[]
     * @throws Exception
     */
    public static byte[] encryptRSA(RSAPublicKey peerPubKey, byte[] data, boolean useBase64Code, String charset) throws Exception {
        String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
        int KEYBIT = 2048;
        int RESERVEBYTES = 11;
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        int decryptBlock = KEYBIT / 8; // 256 bytes
        int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
        // 计算分段加密的block数 (向上取整)
        int nBlock = (data.length / encryptBlock);
        if ((data.length % encryptBlock) != 0) { // 余数非0，block数再加1
            nBlock += 1;
        }
        // 输出buffer, 大小为nBlock个decryptBlock
        ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlock);
        cipher.init(Cipher.ENCRYPT_MODE, peerPubKey);
        // 分段加密
        for (int offset = 0; offset < data.length; offset += encryptBlock) {
            // block大小: encryptBlock 或 剩余字节数
            int inputLen = (data.length - offset);
            if (inputLen > encryptBlock) {
                inputLen = encryptBlock;
            }
            // 得到分段加密结果
            byte[] encryptedBlock = cipher.doFinal(data, offset, inputLen);
            // 追加结果到输出buffer中
            outbuf.write(encryptedBlock);
        }
        // 如果是Base64编码，则返回Base64编码后的数组
        if (useBase64Code) {
            return AlgorithmBase64Util.encodeBASE64(outbuf.toByteArray()).getBytes(charset);
        } else {
            return outbuf.toByteArray(); // ciphertext
        }
    }

    /**
     * RSA解密
     * @param localPrivKey 私钥
     * @param cryptedBytes 待解密信息
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decryptRSA(RSAPrivateKey localPrivKey, byte[] cryptedBytes, boolean useBase64Code, String charset) throws Exception {
        String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密block需要预留11字节
        byte[] data = null;

        // 如果是Base64编码的话，则要Base64解码
        if (useBase64Code) {
            data = AlgorithmBase64Util.decodeBASE64(new String(cryptedBytes, charset));
        } else {
            data = cryptedBytes;
        }
        int KEYBIT = 2048;
        int RESERVEBYTES = 11;
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        int decryptBlock = KEYBIT / 8; // 256 bytes
        int encryptBlock = decryptBlock - RESERVEBYTES; // 245 bytes
        // 计算分段解密的block数 (理论上应该能整除)
        int nBlock = (data.length / decryptBlock);
        // 输出buffer, , 大小为nBlock个encryptBlock
        ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * encryptBlock);
        cipher.init(Cipher.DECRYPT_MODE, localPrivKey);
        // 分段解密
        for (int offset = 0; offset < data.length; offset += decryptBlock) {
            // block大小: decryptBlock 或 剩余字节数
            int inputLen = (data.length - offset);
            if (inputLen > decryptBlock) {
                inputLen = decryptBlock;
            }
            // 得到分段解密结果
            byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
            // 追加结果到输出buffer中
            outbuf.write(decryptedBlock);
        }
        outbuf.flush();
        outbuf.close();
        return outbuf.toByteArray();
    }

}

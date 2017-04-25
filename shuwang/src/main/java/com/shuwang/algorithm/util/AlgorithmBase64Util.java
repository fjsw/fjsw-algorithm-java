package com.shuwang.algorithm.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by w景洋
 * Time 2017/2/25.
 */

public class AlgorithmBase64Util {
    
    /**
     * BASE64加密
     * @param content
     * @return
     * @throws Exception
     */
    public static String encodeBASE64(byte[] content) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(content);
    }

    /**
     * BASE64解密
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decodeBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

}

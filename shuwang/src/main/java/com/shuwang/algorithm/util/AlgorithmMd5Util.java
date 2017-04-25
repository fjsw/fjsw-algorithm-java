package com.shuwang.algorithm.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by w景洋
 * Time 2017/2/25.
 */

public class AlgorithmMd5Util {
    
    /**
     * MD5加密
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String md5(byte[] bytes) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);
        String result = new BigInteger(1, md.digest()).toString(16);
        result = StringUtils.leftPad(result, 32, "0").toUpperCase();
        return result;
    }

}

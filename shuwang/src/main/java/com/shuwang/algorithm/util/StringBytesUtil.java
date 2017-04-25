package com.shuwang.algorithm.util;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by w景洋
 * Time 2017/2/25.
 */

public class StringBytesUtil {
  
    /**
     * XML2JSON
     * @param xmlString
     * @return
     */
    public static String xml2json(String xmlString) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json = xmlSerializer.read(xmlString);
        return json.toString(1);
    }
    /**
     * 字符数组转16进制字符串
     * @param str
     * @return
     */
    public static byte[] string2bytes(String str, int radix) {
        byte[] srcBytes = str.getBytes();

        // 2个16进制字符占用1个字节，8个二进制字符占用1个字节
        int size = 2;
        if (radix == 2) {
            size = 8;
        }

        byte[] tgtBytes = new byte[srcBytes.length / size];
        for (int i = 0; i < srcBytes.length; i = i + size) {
            String tmp = new String(srcBytes, i, size);
            tgtBytes[i / size] = (byte) Integer.parseInt(tmp, radix);
        }
        return tgtBytes;
    }

    /**
     * 字符数组转16进制字符串
     * @param bytes
     * @return
     */
    public static String bytes2string(byte[] bytes, int radix) {
        // 2个16进制字符占用1个字节，8个二进制字符占用1个字节
        int size = 2;
        if (radix == 2) {
            size = 8;
        }
        StringBuilder sb = new StringBuilder(bytes.length * size);
        for (int i = 0; i < bytes.length; i++) {
            int integer = bytes[i];
            while (integer < 0) {
                integer = integer + 256;
            }
            String str = Integer.toString(integer, radix);
            sb.append(StringUtils.leftPad(str.toUpperCase(), size, "0"));
        }
        return sb.toString();
    }

}

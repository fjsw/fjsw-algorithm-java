package com.shuwang.algorithm.util;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

/**
 * Created by w景洋
 * Time 2017/2/25.
 */

public class PublicPrivateKeyUtil {
    
    /**
     * 从文件中输入流中加载公钥
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey getPublicKey(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            return getPublicKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入缓存流出错");
            }

            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

    /**
     * 从字符串中加载公钥
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey getPublicKey(String publicKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从文件中加载公钥
     * @param filePath 证书文件路径
     * @param fileType 文件类型
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey getPublicKey(String filePath, String fileType) throws Exception {
        InputStream in = new FileInputStream(filePath);
        try {
            if (!"PEM".equalsIgnoreCase(fileType)) {
                Certificate cert = null;
                CertificateFactory factory = CertificateFactory.getInstance("X.509");
                cert = factory.generateCertificate(in);
                return cert.getPublicKey();
            } else {
                return getPublicKey(in);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

    /**
     * 从输入流中加载私钥
     * @return 是否成功
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            return getPrivateKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入缓存流出错");
            }

            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

    /**
     * 从字符串中加载私钥
     * @param privateKeyStr 公钥数据字符串
     * @throws Exception 加载私钥时产生的异常
     */
    public static PrivateKey getPrivateKey(String privateKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (IOException e) {
            throw new Exception("私钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 从文件中加载私钥
     * @param filePath 证书文件路径
     * @param fileType 文件类型
     * @throws Exception 加载公钥时产生的异常
     */
    public static PrivateKey getPrivateKey(String filePath, String fileType, String passwd) throws Exception {
        PrivateKey key = null;
        InputStream in = new FileInputStream(filePath);

        String keyType = "JKS";
        if ("keystore".equalsIgnoreCase(fileType)) {
            keyType = "JKS";
        } else if ("pfx".equalsIgnoreCase(fileType)) {
            keyType = "PKCS12";
        } else if ("pem".equalsIgnoreCase(fileType)) {
            keyType = "PEM";
        }

        try {
            if (!"PEM".equalsIgnoreCase(keyType)) {
                KeyStore ks = KeyStore.getInstance(keyType);
                char[] cPasswd = passwd.toCharArray();
                ks.load(in, cPasswd);
                Enumeration<String> aliasenum = ks.aliases();
                String keyAlias = null;
                while (aliasenum.hasMoreElements()) {
                    keyAlias = (String) aliasenum.nextElement();
                    key = (PrivateKey) ks.getKey(keyAlias, cPasswd);
                    if (key != null)
                        break;
                }
                return key;
            } else {
                return getPrivateKey(in);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭输入流出错");
            }
        }
    }

}

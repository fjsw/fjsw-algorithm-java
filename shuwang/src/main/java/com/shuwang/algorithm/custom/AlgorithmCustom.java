package com.shuwang.algorithm.custom;

import com.google.gson.Gson;
import com.shuwang.algorithm.exception.CustomException;
import com.shuwang.algorithm.model.SWPublicType;
import com.shuwang.algorithm.model.UnifyResponse;
import com.shuwang.algorithm.util.AlgorithmAesUtil;
import com.shuwang.algorithm.util.AlgorithmRsaUtil;
import com.shuwang.algorithm.util.PublicPrivateKeyUtil;
import com.shuwang.algorithm.util.VerificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by w景洋
 * Time 2017/2/25.
 */

public class AlgorithmCustom {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    final static String swpubkey = "" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwmT8sp3jsqihuRIwz+oe\n" +
            "6wNKH/iVZZoNoZHBcCKHgeJxghF2Fyao9F1mgJ598N3rdmeiSxH2m3ms9omlWSGs\n" +
            "5bRNUlk0cyhMWsIiy8KL2p7+s14/mkQi287/5bu1oEhWGgt+0sQIo8d4AARcfGnJ\n" +
            "DbxdifJiAPdgmpmVzAbL6iDVSlES63SNAj5uo03rCT+4JklVPwBNoP4D60YcWedt\n" +
            "yjhYPzZgvAx5a9wh7Ro/lfWW3lgL/QnfR8BmYB2Fd0UzfoMvom847i2/G98nERxE\n" +
            "N674BraxozuUdeu9viSwow7gwHbD4AyV58Rx5h0MuVRXgtxnWRSDWAXQ0jb1upLp\n" +
            "SQIDAQAB";
    /**
     * 加密请求报文
     * @param encryptdata
     * @param partner_prikey
     * @return
     * @throws Exception
     */
    public SWPublicType easyEncrypt(String encryptdata,String partner_prikey) throws Exception {

        String AESkey = VerificationUtil.generateRandomKey(16);
        //获取数网公钥
        PublicKey pubsw_key = PublicPrivateKeyUtil.getPublicKey(swpubkey);
        //获取合作方私钥
        PrivateKey prikey = PublicPrivateKeyUtil.getPrivateKey(partner_prikey);
        // 加密报文
        byte[] encryptDate = AlgorithmAesUtil.encryptAES(encryptdata.getBytes(), AESkey.getBytes(), true, "UTF-8");
        // 加密AES对称秘钥
        byte[] encryptKey = AlgorithmRsaUtil.encryptRSA((RSAPublicKey) pubsw_key, AESkey.getBytes(), true, "UTF-8");
        // 签名
        byte[] signdata = VerificationUtil.signRSA((RSAPrivateKey) prikey, encryptdata.getBytes(), true, "UTF-8");

        SWPublicType res = new SWPublicType();
        res.setEncryptData(new String(encryptDate));
        res.setEncryptKey(new String(encryptKey));
        res.setSignData(new String(signdata));
        log.info("提交到接口的数据:{}",new Gson().toJson(res));
        return res;
    }
    
    /**
     * 解密返回报文
     * @param unifyResponse
     * @param partner_prikey
     * @return
     * @throws Exception
     */
    public String easydecrypt(UnifyResponse unifyResponse,String partner_prikey) throws Exception {

//        log.info("{}",);
        if(unifyResponse.getEncryptData().equals("null")){
            log.info("请求错误:{}",unifyResponse.getResMsg());
            return unifyResponse.toJson();
        }
        String encryptKey = unifyResponse.getEncryptKey();//获取加密的AES秘钥
        String encryptData = unifyResponse.getEncryptData(); //获取加密的报文
        String sign = unifyResponse.getSignData(); //获取签名

        //获取合作方私钥
        PrivateKey prikey = PublicPrivateKeyUtil.getPrivateKey(partner_prikey);
        //获取数网公钥
        PublicKey pubsw_key = PublicPrivateKeyUtil.getPublicKey(swpubkey);
        //解密得到AES
        byte[] aeskey = AlgorithmRsaUtil.decryptRSA((RSAPrivateKey) prikey, encryptKey.getBytes(), true, "UTF-8");
        byte[] resObject = AlgorithmAesUtil.decryptAES(encryptData.getBytes(), aeskey, true, "UTF-8");
        Boolean flat = VerificationUtil.verifyRSA((RSAPublicKey) pubsw_key, resObject, sign.getBytes(), true, "UTF-8");
        if(flat){
            log.info("签名验证通过");
            String resData = new String(resObject, "UTF-8");
            return resData;
        }else{
        	throw new CustomException("签名验证未通过,body :"+new String(resObject, "UTF-8"));
        }
    }
}

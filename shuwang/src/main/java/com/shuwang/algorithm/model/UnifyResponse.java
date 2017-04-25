package com.shuwang.algorithm.model;

/**
 * Created by w景洋
 * Time 2016/12/1.
 */
public class UnifyResponse {
    private String encryptData;
    private String encryptKey;
    private String signData;
    private String reqMsgId;
    private String resMsg;
    private String reqcode;

    public String toJson(){
        String json = "{\"encryptData\":\""+this.encryptData+"\",\"encryptKey\":\""+this.encryptKey+"\",\"signData\":\""+this.signData+"\",\"reqcode\":\""+this.reqcode+"\",\"reqMsgId\":\""+this.reqMsgId+"\",\"resMsg\":\""+this.resMsg+"\"}";
        return json;
    }
    public String getEncryptData() {
        return encryptData;
    }

    public void setEncryptData(String encryptData) {
        this.encryptData = encryptData;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getReqcode() {
        return reqcode;
    }

    public void setReqcode(String reqcode) {
        this.reqcode = reqcode;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public String getReqMsgId() {
        return reqMsgId;
    }

    public void setReqMsgId(String reqMsgId) {
        this.reqMsgId = reqMsgId;
    }
}
package com.shuwang.algorithm.model;

import com.shuwang.algorithm.exception.CustomException;

public class DefaultCall {
	
	private String partner_prikey = ""; //合作方私钥
	private String apiUrl = ""; //连接请求的地址
	private String cooperator = ""; //合作方标识符
	private String tranCode = ""; //业务服务码
	private String callBack = ""; //请求回调地址
	private String reqMsgId = ""; //请求流水号
	private String body = ""; //请求数据
	private String ext = ""; //备注
	
	public DefaultCall(String partner_prikey,String apiUrl,String cooperator,String tranCode,String callBack,String reqMsgId,String body){
		this.partner_prikey =partner_prikey;
		this.apiUrl = apiUrl;
		this.cooperator = cooperator;
		this.tranCode = tranCode;
		this.callBack = callBack;
		this.reqMsgId = reqMsgId;
		this.body = body;
	}
	
	public void checkParam(){
		String msg = "";
		if(this.partner_prikey.isEmpty()){
			msg = "参数缺少,合作方秘钥：[partner_prikey] 为空";
			throw new CustomException(msg);
		}
		if(this.apiUrl.isEmpty()){
			msg = "参数缺少,api接口地址：[apiUrl] 为空";
			throw new CustomException(msg);
		}
		if(this.cooperator.isEmpty()){
			msg = "参数缺少,合作方标识符：[cooperator] 为空";
			throw new CustomException(msg);
		}
		if(this.tranCode.isEmpty()){
			msg = "参数缺少,业务服务码：[tranCode] 为空";
			throw new CustomException(msg);
		}
		if(this.callBack.isEmpty()){
			msg = "参数缺少,回调地址：[callBack] 为空";
			throw new CustomException(msg);
		}
		if(this.reqMsgId.isEmpty()){
			msg = "参数缺少,消息流水号：[reqMsgId] 为空";
			throw new CustomException(msg);
		}
		if(this.body.isEmpty()){
			msg = "参数缺少,明文数据：[body] 为空";
			throw new CustomException(msg);
		}
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getPartner_prikey() {
		return partner_prikey;
	}
	public void setPartner_prikey(String partner_prikey) {
		this.partner_prikey = partner_prikey;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	public String getCooperator() {
		return cooperator;
	}
	public void setCooperator(String cooperator) {
		this.cooperator = cooperator;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getCallBack() {
		return callBack;
	}
	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}
	public String getReqMsgId() {
		return reqMsgId;
	}
	public void setReqMsgId(String reqMsgId) {
		this.reqMsgId = reqMsgId;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	
	
}

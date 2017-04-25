package com.shuwang.algorithm.custom;


import com.google.gson.Gson;
import com.shuwang.algorithm.model.DefaultCall;
import com.shuwang.algorithm.model.SWPublicType;
import com.shuwang.algorithm.model.UnifyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by w景洋
 * Time 2017/2/25.
 */

public class CallClient {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * api call start
	 * @param call
	 * @return
	 * @throws Exception
	 */
	public UnifyResponse callClient(DefaultCall call) throws Exception{
		log.info("请求参数:{}",new Gson().toJson(call));
		//检查参数
		if(!call.getTranCode().equals("SWZF100")){
			call.checkParam();
		}
		AlgorithmCustom custom = new AlgorithmCustom();
		SWPublicType request = custom.easyEncrypt(call.getBody(),call.getPartner_prikey());
		request.setCallBack(call.getCallBack());
		request.setCooperator(call.getCooperator());
		request.setReqMsgId(call.getReqMsgId());
		request.setTranCode(call.getTranCode());
		request.setExt(call.getExt());
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		UnifyResponse response = httpclient.defaultHttpClient(request, call.getApiUrl());
		String body = custom.easydecrypt(response, call.getPartner_prikey());
		response.setEncryptData(body);
		return response;
	}
}

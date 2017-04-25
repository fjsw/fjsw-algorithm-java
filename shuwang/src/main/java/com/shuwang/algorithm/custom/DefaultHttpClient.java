package com.shuwang.algorithm.custom;


import com.google.gson.Gson;
import com.shuwang.algorithm.model.SWPublicType;
import com.shuwang.algorithm.model.UnifyResponse;
import com.shuwang.algorithm.service.HttpsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by w景洋
 * Time 2017/2/25.
 */

public class DefaultHttpClient {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 根据url 发送http post请求
	 * @param swpub
	 * @param url
	 * @return
	 */
	public UnifyResponse defaultHttpClient(SWPublicType swpub,String url){

		HttpsService httpclient = new HttpsService();
		String response = "";
		try {
			response = httpclient.jsonPost(url,new Gson().toJson(swpub));
		}catch (Exception e){
			log.info("HTTP请求超时");
		}
		return new Gson().fromJson(response, UnifyResponse.class);
	}
}

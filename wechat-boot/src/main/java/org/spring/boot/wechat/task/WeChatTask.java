package org.spring.boot.wechat.task;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.boot.wechat.util.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Configuration// 1.主要用于标记配置类,兼备Component的效果.
@EnableScheduling// 2.开启定时任务
public class WeChatTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(WeChatTask.class);
	@Value("${weixin.appid}")
	private String appid;
	@Value("${weixin.appSecret}")
	private String appSecret;
	@Value("${weixin.tokenUrl}")
	private String tokenUrl;
	/**
	 * @Description: 任务执行体
	 * @param @throws Exception
	 */
//	@Scheduled(cron = "0 0/1 * * * ?")
	@Scheduled(cron = "0/5 * * * * ?")
	public void getToken_getTicket() throws Exception {
		LOGGER.info(LocalDateTime.now().toLocalTime() + "====获取token开始====");
		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "client_credential");
		params.put("appid", appid);
		params.put("secret", appSecret);
		System.out.println(params);
		String jstoken = HttpUtils.sendGet(tokenUrl, params);
		JSONObject jsonObject = (JSONObject)JSON.parse(jstoken);
		String access_token = (String) jsonObject.get("access_token"); // 获取到token并赋值保存
		//将token存在缓存中,存在redis???
		//GlobalConstants.interfaceUrlProperties.put("access_token", access_token);
		LOGGER.info(LocalDateTime.now().toLocalTime() + "====获取的token为====" + access_token);
	}

}

package org.spring.boot.wechat.task;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.boot.wechat.util.HttpUtils;
import org.spring.boot.wechat.util.RedisUtil;
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
	@Resource
	private RedisUtil redisUtil;
	
	@Value("${weixin.appid}")
	private String appid;
	
	@Value("${weixin.appSecret}")
	private String appSecret;
	
	@Value("${weixin.tokenUrl}")
	private String tokenUrl;
	
	/**
	 * @Author LiuTao @Date 2021年1月15日 下午9:53:39 
	 * @Title: getToken_getTicket 
	 * @Description: 定时获取access_token,并存放起来备用,token有效期7200秒
	 * 
	 * cron表达式由6或7个空格分隔的时间字段组成:秒 分钟 小时 日期 月份 星期 年（可选）
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
//	@Scheduled(cron = "0/5 * * * * ?")
//	@Scheduled(fixedRate=5000)//指定间隔时间()
	public void getToken_getTicket() throws Exception {
		LOGGER.info(LocalDateTime.now().toLocalTime() + "====获取token开始====");
		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "client_credential");
		params.put("appid", appid);
		params.put("secret", appSecret);
		String jstoken = HttpUtils.sendGet(tokenUrl, params);
		JSONObject jsonObject = (JSONObject)JSON.parse(jstoken);
		String access_token = jsonObject.getString("access_token"); // 获取到token并赋值保存
		//将token存在缓存中,存在redis???是否可以缓存在spring容器的
		//GlobalConstants.interfaceUrlProperties.put("access_token", access_token);
		redisUtil.set("access_token", access_token, 7200L);
		LOGGER.info(LocalDateTime.now().toLocalTime() + "====获取的token为====" + access_token);
	}

	/**
	 * @Author LiuTao @Date 2021年1月18日 下午2:12:01 
	 * @Title: sendMassageToAll 
	 * @Description: 群发消息(订阅好每天1条)
	 */
	public void sendMassageToAll(){
		
	}
}

package org.spring.boot.wechat.task;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//@Configuration// 1.主要用于标记配置类,兼备Component的效果.
//@EnableScheduling// 2.开启定时任务
public class ScheduleTask {
	@Value("${weixin.appid}")
	private String appid;
	@Value("${weixin.appSecret}")
	private String appSecret;
	@Value("${weixin.tokenUrl}")
	private String tokenUrl;
	// 3.添加定时任务
	@Scheduled(cron = "0/5 * * * * ?")
	// 或直接指定时间间隔,例如:5秒
	// @Scheduled(fixedRate=5000)
	private void configureTasks() {
		System.out.println("------" + appid);
		System.out.println("------" + appSecret);
		System.out.println("------" + tokenUrl);
		System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
	}
}

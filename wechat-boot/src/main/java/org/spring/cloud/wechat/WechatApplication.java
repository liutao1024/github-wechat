package org.spring.cloud.wechat;


/**
 * @Author LiuTao @Date 2020�?11�?3�? 下午8:17:59
 * @ClassName: Eureka1Application
 * @Description: Eureka1启动�? 
 * 
 * http://c.biancheng.net/view/5324.html
 * http://eurek1:8761/&&http:eureka2:8762/
 */
@SpringBootApplication
//@EnableEurekaServer
public class WechatApplication {
	//项目启动入口
	public static void main(String[] args) {
		SpringApplication.run(WechatApplication.class, args);
	}
}

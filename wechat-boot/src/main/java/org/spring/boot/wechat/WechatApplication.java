package org.spring.boot.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author LiuTao @Date 2021年1月12日 下午9:05:36
 * @ClassName: WechatApplication 
 * @Description: TODO(Describe)
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

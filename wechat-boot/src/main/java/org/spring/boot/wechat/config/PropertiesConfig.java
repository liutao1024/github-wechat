package org.spring.boot.wechat.config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.spring.boot.wechat.util.PropertiesUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @Author LiuTao @Date 2021年1月19日 上午9:36:05
 * @ClassName: PropertiesConfig 
 * @Description: spring启动时,加载此Bean时,将Environment引入,
 * 				并给PropertiesUtil的静态变量environment赋值.后续直接调用即可
 */
@Configuration
public class PropertiesConfig {

	@Resource
	private Environment environment;

	@PostConstruct
	public void setProperties() {
		PropertiesUtil.setEnvironment(environment);
	}

}

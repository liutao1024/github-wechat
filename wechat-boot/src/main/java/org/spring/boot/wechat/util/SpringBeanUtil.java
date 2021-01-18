package org.spring.boot.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author LiuTao @Date 2021年1月16日 上午11:35:21
 * @ClassName: SpringUtil 
 * @Description: TODO(Describe)
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringBeanUtil.class);
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		LOGGER.info("====================================");
		if (SpringBeanUtil.applicationContext == null) {
			SpringBeanUtil.applicationContext = applicationContext;
		}
		LOGGER.info("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext="
						+ SpringBeanUtil.applicationContext + "========");
	}

	// 获取applicationContext
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @Author LiuTao @Date 2021年1月16日 上午11:34:34 
	 * @Title: getBean 
	 * @Description: 通过name获取 Bean. 
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	/**
	 * @Author LiuTao @Date 2021年1月16日 上午11:34:49 
	 * @Title: getBean 
	 * @Description: 通过class获取Bean. 
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	/**
	 * @Author LiuTao @Date 2021年1月16日 上午11:35:09 
	 * @Title: getBean 
	 * @Description: 通过name,以及Clazz返回指定的Bean
	 * @param name
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}

}

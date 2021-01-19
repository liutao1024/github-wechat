package org.spring.boot.wechat.util;

import org.springframework.core.env.Environment;

public class PropertiesUtil {
	
	private static Environment environment = null;

	public static void setEnvironment(Environment environment) {
		PropertiesUtil.environment = environment;
	}

	public static String getProperty(String key) {
		return PropertiesUtil.environment.getProperty(key).trim();
	}

}

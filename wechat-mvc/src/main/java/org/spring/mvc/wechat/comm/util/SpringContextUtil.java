package org.spring.mvc.wechat.comm.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SuppressWarnings("static-access")
public class SpringContextUtil implements ApplicationContextAware{

	protected static ApplicationContext applicationContext;// Spring应用上下文环境
	//下面的这个方法上加了@Override注解,原因是继承ApplicationContextAware接口是必须实现的方法
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public static ApplicationContext  getApplicationContext(){
        return applicationContext;
    }

    public static Object getBean(String beanName) throws BeansException {
        return applicationContext.getBean(beanName);
    }

//	public static Object getBean(Class c) throws BeansException {
//        return applicationContext.getBean(c);
//    }
	
//	public static Object getBean(String name, Class requiredType) throws BeansException {
//        return applicationContext.getBean(name, requiredType);
//    }
 
	public static <T> T getBean(Class<T> c) throws BeansException {
		return applicationContext.getBean(c);
	}
	
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}
	
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }
 
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }
 
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }
 
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getAliases(name);
    }
}

package com.myron.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * springContext工具类获取容器对象(一般在wb作为spring容器时候使用)
 * @author Administrator
 *
 */
public class SpringContextHolder implements ApplicationContextAware{
	private static ApplicationContext applicationContext = null;
	
	private Log log = LogFactory.getLog(getClass());
	
	/**
	 * 
	 * 实现ApplicationContextAware接口, 注入Context到静态变量中.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextHolder.applicationContext=applicationContext;
		log.debug("ApplicationContext registed");
	}
	
	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	/**
	 * 从静态变量applicationContext中取得Bean
	 */
	public static Object getBean(String name) {
		  return getApplicationContext().getBean(name);
	}
	
	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}
}

package com.yinhai.sheduledTask.frame.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoaderListener;

public final class AppContextHolder implements ApplicationContextAware {
	private static ApplicationContext context;

	static{
		if (context == null) {
			context = ContextLoaderListener.getCurrentWebApplicationContext();
		}
	}

	/**
	 * 根据 spring 配置中 bean id 返回 bean 的实例
	 * 
	 * @param id
	 * @return
	 */
	public static Object getBean(String id) {
		return context.getBean(id);
	}

	public static boolean containsBean(String id) {
		return context.containsBean(id);
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		context = arg0;
	}

	public static void setContext(ApplicationContext context) {
		AppContextHolder.context = context;
	}

}

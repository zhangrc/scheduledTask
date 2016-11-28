package com.yinhai.sheduledTask.frame.plugin.quartz.util;



import com.yinhai.sheduledTask.frame.exception.AppException;
import com.yinhai.sheduledTask.frame.plugin.network.util.RpcDTO;
import com.yinhai.sheduledTask.frame.util.AppContextHolder;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class TaskUtils {
	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * 
	 */
	public static Object post(RpcDTO dto) throws Exception {
		if(!AppContextHolder.containsBean(dto.getService())) {
			Exception e = new AppException("找不到对应的service，请检查！");
			warperException(e);
			throw e;
		}
		Object service = AppContextHolder.getBean(dto.getService());
		try {
			return  service.getClass().getMethod(dto.getMethod(), Map.class).invoke(service, dto.toMap());
		} catch (Exception e) {
			throw warperException(e);
		}
	}
	
	/**
	 * 转换异常
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private static Exception warperException (Exception e) throws Exception {
		String msg = "";
		if(e instanceof IllegalArgumentException) {
			msg = "调用方法时，参数类型异常，请检查source 项目的代码！";
		}else if(e instanceof NoSuchMethodException) {
			msg = "找不到对应bean的指定方法！";
		}else if(e instanceof InvocationTargetException){
			e = (Exception)((InvocationTargetException) e).getTargetException();
			msg = e.getMessage();
		}else {
			msg = e.getMessage();
		}
		return e;
	}
}

package com.yinhai.sheduledTask.frame.plugin.network.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface NetworkService {

	/**
	 * 通过完整的http地址，发送一个post请求，并返回json格式数据
	 * 
	 * @param url
	 *            请求action的地址
	 * @param parm
	 *            传入的参数
	 * @param security
	 *            接口安全加密复杂度
	 * @return json格式的数据
	 * @throws Exception
	 */
	public String post(String url, Map param, Integer security) throws Exception;

	/**
	 * 通过完整的http地址，发送一个post请求，并返回json格式数据
	 * 
	 * @param url
	 *            请求action的地址
	 * @param parm
	 *            传入的参数
	 * @param security
	 *            接口安全加密复杂度
	 * @return Object 返回后需要强制转换成 Map 或者 List
	 * @throws Exception
	 */
	public Object postForObject(String url, Map param, Integer security) throws Exception;

	/**
	 * 被调用action接收参数的方法，返回json格式的数据，也可作为回调接受参数的方法
	 * 
	 * @param request
	 *            action中的request对象
	 * @param response
	 *            action中的response对象
	 * @return json格式的参数数据
	 * @throws Exception
	 */
	public String receive(Integer security, HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 被调用的action处理完业务逻辑后，通过此方法返回给调用者数据，
	 * 
	 * @param param
	 *            需要返回给调用者的数据
	 * @param security
	 *            接口安全加密复杂度
	 * @param response
	 *            action中的response对象
	 * @throws Exception
	 */
	public void write(Map param, Integer security, HttpServletResponse response) throws Exception;

	/**
	 * 被调用的action处理完业务逻辑后，通过此方法返回给调用者数据，
	 * 
	 * @param param
	 *            需要返回给调用者的数据
	 * @param security
	 *            接口安全加密复杂度
	 * @param response
	 *            action中的response对象
	 * @throws Exception
	 */
	public void write(List param, Integer security, HttpServletResponse response) throws Exception;

	/**
	 * 被调用的action处理完业务逻辑后，通过此方法返回给调用者数据，
	 * 
	 * @param param
	 *            需要返回给调用者的数据 json 格式
	 * @param security
	 *            接口安全加密复杂度
	 * @param response
	 *            action中的response对象
	 * @throws Exception
	 */
	public void write(String param, Integer security, HttpServletResponse response) throws Exception;
	
	/**
	 * 被调用的action处理完业务逻辑后，通过此方法返回给调用者错误信息，
	 * 
	 * @param param
	 *            需要返回给调用者的数据 json 格式
	 * @param security
	 *            接口安全加密复杂度
	 * @param response
	 *            action中的response对象
	 * @param security
	 *            接口安全加密复杂度
	 * @throws Exception
	 */
	public void writeError(String param, HttpServletResponse response) throws Exception;
}

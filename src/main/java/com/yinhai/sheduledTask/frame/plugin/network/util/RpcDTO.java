package com.yinhai.sheduledTask.frame.plugin.network.util;

import java.util.HashMap;
import java.util.Map;


/**
 * 传递的参数
 * @author zrc
 *
 */
public class RpcDTO {
	/**调用的方法名称**/
	private String method;
	/**调用的service名称**/
	private String service;
	/**传递的参数**/
	private Map param ;
	/**来源系统 **/
	private String from = ConstUtil.FROM_CHAIN+"";
	/**url**/
	private String url;
	
	
	public RpcDTO(String url,String service,String method,Map param) {
		super();
		this.url = url;
		this.method = method;
		this.service = service;
		this.param = param;
	}
	/**
	 * 
	 * @param service source项目的spring bean id
	 * @param method service接口中请求的方法名
	 * @param param 传递的参数
	 */
	public RpcDTO(String service,String method,Map param) {
		this(NetworkConfigUtil.getSourcePath(),service,method,param);
	}
	
	/**
	 * 默认来自连锁
	 * @param from
	 * @return
	 */
	public RpcDTO setFrom(String from){
		this.from = from;
		return this;
	}
	
	public Map toMap(){
		Map map = new HashMap(param.entrySet().size()+3);
		map.putAll(param);
		map.put(ConstUtil.FROM, this.from);
		map.put(ConstUtil.SERVICENAME,this.service );
		map.put(ConstUtil.METHODNAME, this.method);
		return map;
	}
	
	public RpcDTO put(Object key ,Object value) {
		this.param.put(key, value);
		return this;
	}
	
	public String getUrl() {
		return this.url;
	}
	public String getMethod() {
		return method;
	}
	public String getService() {
		return service;
	}
	public Map getParam() {
		return param;
	}
	
}

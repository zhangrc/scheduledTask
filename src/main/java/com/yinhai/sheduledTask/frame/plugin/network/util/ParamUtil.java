package com.yinhai.sheduledTask.frame.plugin.network.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.yinhai.sheduledTask.frame.plugin.network.exception.NetWorkException;
import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ParamUtil {

	/**
	 * request.getParameter(name)中的 name
	 */
	public static final String PARAM_NAME = "simpson_param";
	/**
	 * 错误标识
	 */
	public static final String ERROR_NAME = "simpson_error";

	private static final String ERROR = "error";
	private static final String ERROR_MSG = "error_msg";
	private static final String DATA = "data";
	private static final String EMPTY = "\"\"";
	private static final String UTF8 = "UTF-8";
	private static final String QUOT = "\"";
	private static final String COMMA = ",";
	private static final String COLON = ":";

	/**
	 * 将map形式的参数转换成json格式并加密
	 * 
	 * @param param
	 * @param security
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String encode(Map param, Integer security) throws UnsupportedEncodingException  {
		// 在此加入加密算法
		String s = encodeParam(param, security);
		return s;
	}

	/**
	 * 将list形式的参数转换成json格式并加密
	 * 
	 * @param param
	 * @param security
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String encode(List param, Integer security) throws UnsupportedEncodingException  {
		// 在此加入加密算法
		String s = encodeParam(param, security);
		return s;
	}

	/**
	 * 将json格式的数据加密
	 * 
	 * @param param
	 * @param security
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String encode(String param, Integer security) throws UnsupportedEncodingException {
		// 在此加入加密算法
		String s = encodeParam(param, security);
		return s;
	}
	
	public static String encode(Object param ,Integer security) throws UnsupportedEncodingException {
		String s = encodeParam(param, security);
		return s;
	}

	private static String encodeParam(Object o, Integer security) throws UnsupportedEncodingException {
		String s = "";
		if (o == null) {
			s = encrypt(EMPTY, security);
		} else if (o instanceof String) {
			s = encrypt((String) o, security);
		} else {
			s = encrypt(JSON.toJSONStringWithDateFormat(o, "yyyy-MM-dd HH:mm:ss.S",new SerializerFeature[] { SerializerFeature.WriteMapNullValue }), security);
		}
		return s;
	}

	/**
	 * 加密
	 * 
	 * @param param
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	private static String encrypt(String param, Integer security) throws UnsupportedEncodingException {
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append(QUOT).append(ERROR).append(QUOT).append(COLON).append(false).append(COMMA);
		json.append(QUOT).append(ERROR_MSG).append(QUOT).append(COLON).append(EMPTY).append(COMMA);
		json.append(QUOT).append(DATA).append(QUOT).append(COLON).append(param);
		json.append("}");
		String r = null;
		switch (security) {
		case NetWorkConstUtil.SECURITY_LEVEL_ONE:
			r = json.toString();
			break;
		case NetWorkConstUtil.SECURITY_LEVEL_TWO:
			r = new String(Base64.encodeBase64(json.toString().getBytes(UTF8), true, true), UTF8);
			break;
		case NetWorkConstUtil.SECURITY_LEVEL_THREE:
			r = new String(Base64.encodeBase64(json.toString().getBytes(UTF8), true, true), UTF8);
			break;
		case NetWorkConstUtil.SECURITY_LEVEL_FOUR:
			r = new String(Base64.encodeBase64(json.toString().getBytes(UTF8), true, true), UTF8);
			break;
		default:
			r = json.toString();
			break;
		}
		// 暂未实现
		return r;
	}

	/**
	 * 将错误信息编码
	 * 
	 * @param param
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String encodeError(String param) throws UnsupportedEncodingException  {
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append(QUOT).append(ERROR).append(QUOT).append(COLON).append(true).append(COMMA);
		json.append(QUOT).append(ERROR_MSG).append(QUOT).append(COLON).append(QUOT).append(param).append(QUOT).append(COMMA);
		json.append(QUOT).append(DATA).append(QUOT).append(COLON).append(EMPTY);
		json.append("}");
		String r = new String(json.toString().getBytes(UTF8), UTF8);
		return r;
	}

	/**
	 * 对返回数据进行解密，返回 json 字符串
	 * 
	 * @param encodedStr
	 * @param security
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String decode(String param, Integer security) throws UnsupportedEncodingException  {
		// 在此加入解密算法
		Object o = decrypt(param, security);
		String d = JSON.toJSONStringWithDateFormat(o, "yyyy-MM-dd HH:mm:ss.S",new SerializerFeature[] { SerializerFeature.WriteMapNullValue });
		if (EMPTY.equals(d)) {
			d = null;
		}
		return d;
	}

	/**
	 * 对返回数据进行解密，返回 object 对象
	 * 
	 * @param encodedStr
	 * @param security
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Object decodeForObject(String param, Integer security) throws UnsupportedEncodingException{
		Object o = decrypt(param, security);
		if (EMPTY.equals(o)) {
			o = null;
		}
		return o;
	}

	/**
	 * 解密返回的数据
	 * 
	 * @param param
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static Object decrypt(String param, Integer security) throws UnsupportedEncodingException {
		String json = null;
		switch (security) {
		case NetWorkConstUtil.SECURITY_LEVEL_ONE:
			json = param;
			break;
		case NetWorkConstUtil.SECURITY_LEVEL_TWO:
			json = new String(Base64.decodeBase64(param), UTF8);
			break;
		case NetWorkConstUtil.SECURITY_LEVEL_THREE:
			json = new String(Base64.decodeBase64(param), UTF8);
			break;
		case NetWorkConstUtil.SECURITY_LEVEL_FOUR:
			json = new String(Base64.decodeBase64(param), UTF8);
			break;
		default:
			json = param;
			break;
		}

		Map map = JSONObject.parseObject(json);
		Object r = null;
		if (Boolean.valueOf(map.get(ERROR).toString())) {
			throw new NetWorkException((String) map.get(ERROR_MSG));
		} else {
			r = map.get(DATA);
		}
		// 暂未实现
		return r;
	}
	public static String changeTextCode(String paramValue){
		return paramValue.replace("%", "α").replace("&", "β").replace(",", "-").replace(";", "γ");
	}
	
	public static String reChangeTextCode(String param) {
		return param.replace("α", "%").replace("β", "&").replace("-", ",").replace("γ", ";");
	}
}

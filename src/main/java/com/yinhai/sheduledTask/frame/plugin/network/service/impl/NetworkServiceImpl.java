package com.yinhai.sheduledTask.frame.plugin.network.service.impl;

import com.yinhai.sheduledTask.frame.plugin.network.service.NetworkService;
import com.yinhai.sheduledTask.frame.plugin.network.util.ParamUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class NetworkServiceImpl implements NetworkService {

	static Logger logger = Logger.getLogger(NetworkService.class);
	
	@Override
	public String post(String url, Map param, Integer security) throws IOException  {
		logger.debug("request url :\n"+url);
		String result = null;
		String encodedParam = null;
		URL u = null;
		URLConnection con = null;
		OutputStream out = null;
		InputStream in = null;
		ByteArrayOutputStream bo = null;
		try {
			encodedParam = ParamUtil.encode(param, security);
			u = new URL(url);
			con = u.openConnection();
			con.setDoOutput(true);
			con.setConnectTimeout(1000);
//			con.setReadTimeout(1000);
			out = con.getOutputStream();
			String p = ParamUtil.PARAM_NAME + "=" + encodedParam;
			out.write(p.getBytes("UTF-8"));
			out.flush();
			in = con.getInputStream();
			bo = new ByteArrayOutputStream();
			byte[] b = new byte[8 * 1024];
			int n;
			while ((n = in.read(b)) != -1) {
				bo.write(b, 0, n);
			}
			String encodedStr = new String(bo.toByteArray(), "UTF-8");
			result = ParamUtil.decode(encodedStr, security);
		} finally {
			if (bo != null) {
				bo.close();
			}
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
		logger.debug("request result :\n" + result);
		return result;
	}

	@Override
	public Object postForObject(String url, Map param, Integer security) throws IOException {
		logger.debug("request url:\n"+url);
		Object result = null;
		String encodedParam = null;
		URL u = null;
		URLConnection con = null;
		OutputStream out = null;
		InputStream in = null;
		ByteArrayOutputStream bo = null;
		try {
			encodedParam = ParamUtil.encode(param, security);
			u = new URL(url);
			con = u.openConnection();
			con.setDoOutput(true);
			out = con.getOutputStream();
			String p = ParamUtil.PARAM_NAME + "=" + encodedParam;
			out.write(p.getBytes("UTF-8"));
			out.flush();
			in = con.getInputStream();
			bo = new ByteArrayOutputStream();
			byte[] b = new byte[8 * 1024];
			int n;
			while ((n = in.read(b)) != -1) {
				bo.write(b, 0, n);
			}
			String encodedStr = new String(bo.toByteArray(), "UTF-8");
			result = ParamUtil.decodeForObject(encodedStr, security);
		} finally {
			if (bo != null) {
				bo.close();
			}
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
		logger.debug("request result :\n"+result);
		return result;
	}

	@Override
	public String receive(Integer security, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		String param = request.getParameter(ParamUtil.PARAM_NAME);
		String result = ParamUtil.decode(param, security);
		logger.debug("receive :\n"  + result);
		return result;
	}

	@Override
	public void write(Map param, Integer security, HttpServletResponse response) throws IOException {
		String result = ParamUtil.encode(param, security);
		write(result, response);
		logger.debug("write map: "  + result);
	}
	

	@Override
	public void write(List param, Integer security, HttpServletResponse response) throws IOException {
		String result = ParamUtil.encode(param, security);
		write(result, response);
		logger.debug("write list :\n"  + result);
	}

	@Override
	public void write(String param, Integer security, HttpServletResponse response) throws IOException {
		String result = ParamUtil.encode(param, security);
		write(result, response);
		logger.debug("write String :\n"  + result);
	}

	@Override
	public void writeError(String param, HttpServletResponse response) throws IOException {
		String result = ParamUtil.encodeError(param);
		write(result, response);
		logger.debug("write error string :\n"  + result);
	}

	private void write(String result, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
	}
}

package com.yinhai.sheduledTask.frame.plugin.network.filter;


import com.yinhai.sheduledTask.frame.plugin.network.util.ErrorUtil;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ECExceptionMappingInterceptor implements HandlerExceptionResolver {

	private static final long serialVersionUID = 3604780447682702089L;

	public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
		e.printStackTrace();
		String emsg = ErrorUtil.getError(e);
		httpServletRequest.setAttribute("exception",emsg);
		return null;
	}
}

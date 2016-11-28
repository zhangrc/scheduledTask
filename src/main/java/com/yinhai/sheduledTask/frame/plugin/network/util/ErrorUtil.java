package com.yinhai.sheduledTask.frame.plugin.network.util;


public class ErrorUtil {

	public static String getError(Exception e){
		String msg = "";
		if (e.getMessage() == null){
			msg = e.toString() + " " + e.getStackTrace()[0].toString();
		} else {
			msg = getError(e.getMessage());
		}
		return msg;
	}
	
	public static String getError(String msg){
		if(msg == null){
			return "";
		}
		return msg.replaceAll("\\[", "&#91;")
				.replaceAll("\\]", "&#93;")
				.replaceAll("\\{", "&#123;")
				.replaceAll("\\}", "&#125;")
				.replaceAll(":", "&#58;")
				.replaceAll("'", "&#39;");
	}
}

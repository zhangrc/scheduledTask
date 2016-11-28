package com.yinhai.sheduledTask.frame.plugin.network.util;

import java.util.Properties;

public class NetworkConfigUtil {

	private static Properties p;

	static {
		init();
	}

	public NetworkConfigUtil() {
	}

	private static void init() {
		try {
			initProperty();
		} catch (Exception e) {
			init();
		}
	}

	private static void initProperty() throws Exception {
		if (p == null) {
			p = new Properties();
		}
		p.load(NetworkConfigUtil.class.getResourceAsStream("/services-path.properties"));
	}

	/**
	 * 根据key返回value
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		return p.getProperty(key);
	}

	public static String getSourcePath() {
		return p.getProperty("server.source")+"/Rooter";
	}


}
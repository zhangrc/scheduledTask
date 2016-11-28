package com.yinhai.sheduledTask.frame.plugin.network.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 
 * @author zhaolin 字符串长度推荐60以上
 */
public  class ZipUtil {
	
	private static final Integer ZIPLENGTH = 60;
	
	// 压缩
	public static final String compress(String str) throws IOException {
		if (str == null || str.length() == 0  ) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes("ISO-8859-1"));
		gzip.close();
		return out.toString();
	}

	// 解压缩
	public static final String uncompress(String str) throws IOException {   
	    if (str == null || str.length() == 0) {   
	      return str;   
	    }   
	    ByteArrayOutputStream out = new ByteArrayOutputStream();   
	    ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));   
	    GZIPInputStream gunzip = new GZIPInputStream(in);   
	    byte[] buffer = new byte[256];   
	    int n;   
		while ((n = gunzip.read(buffer)) >= 0) {
	      out.write(buffer, 0, n);   
	    }   
	    // toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)
	    return out.toString();
	  }
	// 测试方法
	public static void main(String[] args) throws IOException {

		// 测试字符串
		String str = "{1fdsa }";

		System.out.println("原长度：" + str.length());

		System.out.println("压缩后：" + ZipUtil.compress(str).length());

		//System.out.println("解压缩：" + ZipUtil.uncompress(ZipUtil.compress(str)));
	}
}

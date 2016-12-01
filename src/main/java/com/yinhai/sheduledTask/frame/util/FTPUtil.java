package com.yinhai.sheduledTask.frame.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
* @package com.yinhai.ec.base.util
* <p>Title: FTPUtil.java</p>
* <p>Description: FTP上传下载文件工具类 使用此工具类需要注意的是自己所在的目录 自己不在要操作的文件所在的目录内会导致操作失败</p>
* <p>Copyright: Copyright (c) 2016</p>
* <p>Company: 四川久远银海软件股份有限公司</p>
* @author 刘惠涛
* @date 2016年2月23日 下午2:38:36
* @version 1.0
 */
public class FTPUtil {
	private final static Properties p = new Properties();
	private final static Logger logger = LoggerFactory.getLogger(FTPUtil.class);
	private static String userName;
	private static String password;
	private static String ip;
	private static int port;
	private static FTPClient ftpClient = null;
	public static final String ALLOWIMAGETYPE = "allowImageType";// 允许上传的图片类型
	
	static{
		try {
			p.load(FTPUtil.class.getResourceAsStream("/config/ftp/ftp.properties"));
			userName = getConfig("ftp.user");
			password = getConfig("ftp.password");
			ip = getConfig("ftp.ip");
			port = Integer.valueOf(getConfig("ftp.port"));
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error("找不到 ftp.properties 配置文件",e);
			}
		}
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method getConfig 方法 
	  * @describe <p>方法说明:获取单个属性</p>
	  * @return String 
	  * @author 刘惠涛
	  * @date 2016年2月23日 下午2:46:29
	 */
	public static String getConfig(String key){
		String value = p.getProperty(key);
		if (StringUtils.isEmpty(value)) {
			if (logger.isDebugEnabled()) {
				logger.debug("没有找到\""+key+"\"的相关配置项");
			}
		}
		return value;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method connectServer 方法 
	  * @describe <p>方法说明:建立FTP连接:::每次使用之后一定要记得关闭连接</p>
	  * @return boolean 
	  * @author 刘惠涛
	  * @date 2016年2月23日 下午3:07:21
	 */
	public static boolean connectServer() {
		boolean flag = true;
		if (ftpClient == null) {
			int reply;
			try {
				ftpClient = new FTPClient();
				ftpClient.setControlEncoding("GBK");
				ftpClient.connect(ip,port);
				reply = ftpClient.getReplyCode();
				if (!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
					if (logger.isDebugEnabled()) {
						logger.debug("FTP 服务拒绝连接!");
					}
					flag = false;
				}
				ftpClient.login(userName, password);
				ftpClient.setDataTimeout(120000);
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置二进制传输
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			} catch (SocketException e) {
				flag = false;
				if (logger.isErrorEnabled()) {
					if (e.getMessage() != null) {
						logger.error("登录ftp服务器 " + ip + " 失败:"+e.getMessage());
					}else{
						logger.error("登录ftp服务器 " + ip + " 失败");
					}
				}
			} catch (IOException e) {
				flag = false;
				if (logger.isErrorEnabled()) {
					logger.error("登录ftp服务器 " + ip + " 失败，FTP服务器无法打开！");
				}
			}
		}
		return flag;
	}
	
    /**
      * @package com.yinhai.ec.base.util
      * @method closeConnect 方法 
      * @describe <p>方法说明:关闭连接,每次使用之后一定要记得关闭连接</p>
      * @return void 
      * @author 刘惠涛
      * @date 2016年2月23日 下午3:35:26
     */
    public static boolean closeConnect() { 
    	boolean flag = false;
		try {
			if (ftpClient != null && ftpClient.isConnected()) {
				if(ftpClient.logout()){
					flag = true;
					ftpClient.disconnect();
				}
			}
		} catch (Exception e) {
			flag = false;
			if (logger.isErrorEnabled()) {
				logger.error("ftp服务器" + ip + " 关闭连接失败");
			}
		} finally{
			ftpClient = null;
		}
		return flag;
    }
    
   /**
     * @package com.yinhai.ec.base.util
     * @method getFtpClient 方法 
     * @describe <p>方法说明:扩展使用 </p>
     * @return FTPClient 
     * @author 刘惠涛
     * @date 2016年2月23日 下午3:38:48
    */
    public static FTPClient getFtpClient() {
    	if (connectServer()) {
			return ftpClient;
		}
        return null;
    }
    
	/**
	 * @package com.yinhai.ec.base.util
	 * @method iso8859togbk 方法
	 * @describe
	 * 			<p>
	 *           方法说明:转码[ISO-8859-1 -> GBK] 不同的平台需要不同的转码
	 *           </p>
	 * @return String
	 * @author 刘惠涛
	 * @date 2016年2月23日 下午3:34:28
	 */
	public static String iso8859togbk(Object obj) {
		try {
			if (obj == null)
				return "";
			else
				return new String(obj.toString().getBytes("iso-8859-1"), "GBK");
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method changeWorkingDirectory 方法 
	  * @describe <p>方法说明:进入到服务器的某个目录下  目录必须预先存在</p>
	  * @return void 
	  * @author 刘惠涛
	  * @date 2016年2月23日 下午3:40:05
	 */
	public static boolean changeWorkingDirectory(String directory) {
		boolean flag = false;
		try {
			directory = removeHeadAndEnd(directory);
			flag = ftpClient.changeWorkingDirectory(directory);
		} catch (IOException e) {
			flag = false;
			if (logger.isDebugEnabled()) {
				logger.debug("进入目录失败:"+e.getMessage());
			}
		}
		return flag;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method makeAndEnterDirectory 方法 
	  * @describe <p>方法说明:在服务器上创建并进入文件路劲 创建后自动处于文件路劲的最后一级,如果路劲全部存在,则直接进入最后一级  不能含有特殊字符，如 \ 、/ 、: 、* 、?、 "、 <、>... </p>
	  * @return boolean 
	  * @author 刘惠涛
	  * @date 2016年2月24日 上午9:07:38
	 */
	public static boolean makeAndEnterDirectory(String dir) {
		boolean flag = false;
		try {
			if(dir.contains("/")){
				dir = removeHeadAndEnd(dir);
				String[] pps = dir.split("/");
				for (int i = 0; i < pps.length; i++) {
					String path = pps[i];
					if(ftpClient.changeWorkingDirectory(path)){
						flag = true;
					}else{
						flag = ftpClient.makeDirectory(path);
						if(flag){
							ftpClient.changeWorkingDirectory(path);
							continue;
						}else{
							if(logger.isDebugEnabled()){
								logger.debug("创建目录:"+path+" 失败");
							}
							break;
						}
					}
				}
			}else{
				if(!ftpClient.changeWorkingDirectory(dir)){
					flag = ftpClient.makeDirectory(dir);
				}
			}
		} catch (Exception e) {
			flag = false;
			if (logger.isErrorEnabled()) {
				logger.error("创建文件夹:"+dir+" 失败:"+e.getMessage());
			}
		}
		return flag;
	}
	
	/**
	 * @package com.yinhai.ec.base.util
	 * @method getFtpConfig 方法
	 * @describe
	 * 			<p>
	 *           方法说明:设置FTP客服端的配置--一般可以不设置
	 *           </p>
	 * @return FTPClientConfig
	 * @author 刘惠涛
	 * @date 2016年2月24日 上午9:10:25
	 */
	@SuppressWarnings("unused")
	private static FTPClientConfig getFtpConfig() {
		FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
		return ftpConfig;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method renameFile 方法 
	  * @describe <p>方法说明:重命名文件 </p>
	  * @return void 
	  * @author 刘惠涛
	  * @date 2016年2月24日 上午9:11:36
	 */
	public static boolean renameFile(String oldFileName, String newFileName) {
		boolean flag = false;
		try {
			flag = ftpClient.rename(oldFileName, newFileName);
		} catch (IOException ioe) {
			flag = false;
			if (logger.isErrorEnabled()) {
				logger.error("重命名文件:"+oldFileName+" 失败"+ioe.getMessage());
			}
		}
		return flag;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method changeToParentDirectory 方法 
	  * @describe <p>方法说明:返回到上一层目录 </p>
	  * @return void 
	  * @author 刘惠涛
	  * @date 2016年2月24日 上午9:15:33
	 */
	public static boolean changeToParentDirectory() {
		boolean flag = false;
		try {
			flag = ftpClient.changeToParentDirectory();
		} catch (IOException ioe) {
			flag = false;
			if (logger.isErrorEnabled()) {
				logger.error("返回上一级目录失败:"+ioe.getMessage());
			}
		}
		return flag;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method deleteFile 方法 
	  * @describe <p>方法说明:删除文件,指定文件所在文件夹</p>
	  * @return boolean 
	  * @author 刘惠涛
	  * @date 2016年2月24日 上午11:27:25
	 */
	public static boolean deleteFile(String path, String filename) {
		boolean flag = false;
		try {
			flag = ftpClient.changeWorkingDirectory(path);
			if(flag){
				flag = deleteFile(filename);
			}
		} catch (IOException e) {
			flag = false;
			if (logger.isErrorEnabled()) {
				logger.error("删除文件:"+filename+"失败"+e.getMessage());
			}
		}
		return flag;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method deleteFile 方法 
	  * @describe <p>方法说明:删除一个文件.注意不是文件夹 删除文件时必须处于文件所在的文件夹 不然删除失败</p>
	  * @return boolean 
	  * @author 刘惠涛
	  * @date 2016年2月24日 上午9:19:59
	 */
	public static boolean deleteFile(String filename) {
		boolean flag = false;
		try {
			flag = ftpClient.deleteFile(filename);
		} catch (IOException ioe) {
			flag = false;
			if (logger.isErrorEnabled()) {
				logger.error("删除文件:"+filename+"失败"+ioe.getMessage());
			}
		}
		return flag;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method removeHeadAndEnd 方法 
	  * @describe <p>方法说明:删除路劲前后的"/"</p>
	  * @return String 
	  * @author 刘惠涛
	  * @date 2016年2月24日 下午2:22:34
	 */
	private static String removeHeadAndEnd(String ftpPath) {
		if(StringUtils.isEmpty(ftpPath)){
			return "";
		}
		if (ftpPath.startsWith("/")) {
			ftpPath = ftpPath.substring(1, ftpPath.length());
		}
		if (ftpPath.endsWith("/")) {
			ftpPath = ftpPath.substring(0, ftpPath.lastIndexOf("/"));
		}
		return ftpPath;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method deleteDirectory 方法 
	  * @describe <p>方法说明:删除目录</p>
	  * @return void 
	  * @author 刘惠涛
	  * @date 2016年2月24日 上午9:21:17
	 */
	public static boolean deleteDirectory(String ftpPath) {
		boolean flag = false; 
		try {
			ftpPath = removeHeadAndEnd(ftpPath);
			String[] paths = ftpPath.split("/");
			if(paths.length > 1){
				String pp = ftpPath.substring(0, ftpPath.lastIndexOf("/"));
				changeWorkingDirectory(pp);
				ftpPath = ftpPath.substring(ftpPath.lastIndexOf("/")+1,ftpPath.length());
			}
			FTPFile[] files = ftpClient.listFiles(ftpPath);  
	        for(FTPFile f:files){  
	            String path = ftpPath+"/"+f.getName();  
	            if(f.isFile()){  
	                // 是文件就删除文件  
	                deleteFile(ftpPath,f.getName());
	            }else if(f.isDirectory()){  
	            	deleteDirectory(path);  
	            }  
	        }  
	        // 每次删除文件夹以后就去查看该文件夹下面是否还有文件，没有就删除该空文件夹  
	        FTPFile[] files2 = ftpClient.listFiles(ftpPath);   
	        if(files2.length==0){
	        	changeWorkingDirectory(ftpPath);
	        	changeToParentDirectory();
	            flag = ftpClient.removeDirectory(ftpPath);  
	        }else{
	            flag = false;  
	        }  
		} catch (IOException e) {
			flag = false;
			if (logger.isErrorEnabled()) {
				logger.error("删除文件夹:"+ftpPath+"失败"+e.getMessage());
			}
		}
        return flag;  
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method uploadFile 方法 
	  * @describe <p>方法说明:上传文件</p>
	  * @return boolean 
	  * @author 刘惠涛
	  * @date 2016年2月24日 上午10:01:19
	 */
	public static boolean uploadFile(File localFile,String fileName) {
		boolean flag = false;
		try {
			InputStream in = new FileInputStream(localFile);
			flag = uploadFile(in, fileName);
		} catch (FileNotFoundException e) {
			flag = false;
			if (logger.isErrorEnabled()) {
				logger.error("上传文件:"+fileName+"失败"+e.getMessage());
			}
		}
		return flag;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method isExistFile 方法 
	  * @describe <p>方法说明:检查某一文件是否存在: 必须处于该文件所在的文件夹内</p>
	  * @return boolean 
	  * @author 刘惠涛
	  * @date 2016年2月24日 下午5:01:41
	 */
	public static boolean isExistFile(String fileName) {
		boolean flag = false;
		try {
			FTPFile[] files = ftpClient.listFiles(fileName);
			if(files.length ==1 && files[0].isFile()){
				flag = true;
			}
		} catch (IOException e) {
			flag = false;
			if (logger.isErrorEnabled()) {
				logger.error("找不到指定文件:"+fileName+e.getMessage());
			}
		}
		return flag;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method uploadFile 方法 
	  * @describe <p>方法说明:上传文件</p>
	  * @return boolean 
	  * @author 刘惠涛
	  * @date 2016年2月24日 上午10:03:46
	 */
	public static boolean uploadFile(InputStream in, String fileName) {
		boolean flag = false;
		try {
			flag = ftpClient.storeFile(fileName, in);
		} catch (IOException e) {
			flag = false;
			if (logger.isErrorEnabled()) {
				logger.error("上传文件:"+fileName+"失败"+e.getMessage());
			}
		}
		return flag;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method downloadFile 方法 
	  * @describe <p>方法说明:下载文件 必须处于该文件所在的文件夹 
	  * remoteFileName 服务器文件名称 localFileName本地文件名称</p>
	  * @return boolean 
	  * @author 刘惠涛
	  * @date 2016年2月24日 上午11:28:44
	 */
	public static boolean downloadFile(String remoteFileName, String localFileName) {
		boolean flag = true;
		BufferedOutputStream buffOut = null; 
		try {
			buffOut = new BufferedOutputStream(new FileOutputStream(localFileName));
			flag = ftpClient.retrieveFile(remoteFileName, buffOut); 
		} catch (IOException e) {
			flag = false;
			if (logger.isErrorEnabled()) {
				logger.error("下载文件:"+remoteFileName+"失败"+e.getMessage());
			}
		} finally {
			try {
				if (buffOut != null)
					buffOut.close();
			} catch (Exception e) {
				flag = false;
				if (logger.isErrorEnabled()) {
					logger.error("下载文件:"+remoteFileName+"失败"+e.getMessage());
				}
			}
		}
		return flag;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method isImage 方法 
	  * @describe <p>方法说明:验证是否是图片</p>
	  * @return boolean 
	  * @author 刘惠涛
	  * @date 2016年3月22日 上午10:53:53
	 */
	public static boolean isImage(String type) {
		if(type.contains("/")){
			type = type.substring(type.lastIndexOf("/")+1, type.length());
		}
		String allows = getConfig(ALLOWIMAGETYPE);
		if(!StringUtils.isEmpty(allows)){
			String[] aas = allows.split(",");
			for (String str : aas) {
				if (type.equalsIgnoreCase(str)) {
					return true;
				}
			}
		}else{
			return true;
		}
		return false;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method uploadFTPFile 方法 
	  * @describe <p>方法说明:上传图片</p>
	  * @return boolean 
	  * @author 刘惠涛
	  * @date 2016年3月22日 上午10:55:17
	 */
	public static boolean uploadFTPFile(MultipartFile file, String fileName, String path) {
		boolean flag = false;
		try {
			flag = connectServer();
			if (flag) {
				// 进入指定目录
				flag = makeAndEnterDirectory(path);
				if(flag){
					flag = uploadFile(file.getInputStream(), fileName);
				}
			}
		} catch (IOException e) {
			flag = false;
			if (logger.isErrorEnabled()) {
				logger.error("上传文件:"+file.getOriginalFilename()+"失败:"+e.getMessage());
			}
		} finally{
			if (closeConnect()) {
				if (logger.isDebugEnabled()) {
					if(flag){
						logger.debug("上传文件:"+file.getOriginalFilename()+"成功,已成功关闭FTP连接");
					}else{
						logger.debug("上传文件:"+file.getOriginalFilename()+"失败,已成功关闭FTP连接");
					}
				}
			}
		}
		return flag;
	}
	
//	命令    描述    
//	ABOR    中断数据连接程序    
//	ACCT <account>    系统特权帐号    
//	ALLO <bytes>     为服务器上的文件存储器分配字节    
//	APPE <filename>    添加文件到服务器同名文件    
//	CDUP <dir path>    改变服务器上的父目录    
//	CWD <dir path>    改变服务器上的工作目录    
//	DELE <filename>    删除服务器上的指定文件    
//	HELP <command>    返回指定命令信息    
//	LIST <name>    如果是文件名列出文件信息，如果是目录则列出文件列表    
//	MODE <mode>    传输模式（S=流模式，B=块模式，C=压缩模式）    
//	MKD <directory>    在服务器上建立指定目录    
//	NLST <directory>    列出指定目录内容    
//	NOOP    无动作，除了来自服务器上的承认    
//	PASS <password>    系统登录密码    
//	PASV    请求服务器等待数据连接    
//	PORT <address>    IP 地址和两字节的端口 ID    
//	PWD    显示当前工作目录    
//	QUIT    从 FTP 服务器上退出登录    
//	REIN    重新初始化登录状态连接    
//	REST <offset>    由特定偏移量重启文件传递    
//	RETR <filename>    从服务器上找回（复制）文件    
//	RMD <directory>    在服务器上删除指定目录    
//	RNFR <old path>    对旧路径重命名    
//	RNTO <new path>    对新路径重命名    
//	SITE <params>    由服务器提供的站点特殊参数    
//	SMNT <pathname>    挂载指定文件结构    
//	STAT <directory>    在当前程序或目录上返回信息    
//	STOR <filename>    储存（复制）文件到服务器上    
//	STOU <filename>    储存文件到服务器名称上    
//	STRU <type>    数据结构（F=文件，R=记录，P=页面）    
//	SYST    返回服务器使用的操作系统    
//	TYPE <data type>    数据类型（A=ASCII，E=EBCDIC，I=binary）    
//	USER <username>>    系统登录的用户名   
}

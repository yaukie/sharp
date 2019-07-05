package org.simple.framework.helper;

import java.util.Properties;

import org.simple.framework.constant.ConfigConstant;
import org.simple.framework.util.PropsUtil;

/**
 * 
 * @author yebn
 * 属性文件助手类
 */
public final class ConfigHelper {
	
	private static final Properties PROP = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);
	
	/**
	 * 获取jdbc 驱动
	 * @return
	 */
	public static String getJdbcDriver()
	{
		return PropsUtil.getString(PROP, ConfigConstant.JDBC_DRIVER);
	}
	/**
	 * 链接地址
	 * @return
	 */
	public static String getJdbcUrl()
	{
		return PropsUtil.getString(PROP, ConfigConstant.JDBC_URL);
	}
	/**
	 * 获取用户名
	 * @return
	 */
	public static String getJdbcUserName()
	{
		return PropsUtil.getString(PROP, ConfigConstant.JDBC_USER);
	}
	
	/**
	 * 获取应用基础包名
	 * @return
	 */
	public static String getAppBasePackage()
	{
		return PropsUtil.getString(PROP, ConfigConstant.APP_PACKAGE);
	}
	
	/**
	 * 获取jsp路径
	 * @return
	 */
	public static String getAppJspPath()
	{
		return PropsUtil.getString(PROP, ConfigConstant.APP_JSP_PATH,"/WEB-INF/view/");
	}
	
	public static String getAppDefJspPath()
	{
		return PropsUtil.getString(PROP, ConfigConstant.APP_DEF_JSP_PATH, "login.jsp");
	}
	
	/**
	 * 获取静态文件资源路径
	 * @return
	 */
	public static String getAppStaticPath()
	{
		return PropsUtil.getString(PROP, ConfigConstant.APP_STATIC_PATH,"/static/");
	}
	
	/**
	 * 获取应用文件,上传限制
	 * @return
	 */
	public static int getAppUploadLimit(){
		return PropsUtil.getInt(PROP, ConfigConstant.APP_UPLOAD_LIMIT, 10);
	}
 
	public static String getString(String key){
		return PropsUtil.getString(PROP, key);
	}
	
	public static int getInt(String key){
		return PropsUtil.getInt(PROP, key);
	}
	
	public static boolean getBoolean(String key){
		return PropsUtil.getBoolean(PROP, key);
	}

}

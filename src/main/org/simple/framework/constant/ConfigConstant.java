package org.simple.framework.constant;

/**
 * 
 * @author yebn 
 * 常量定义函数
 */
public interface ConfigConstant {

	String CONFIG_FILE = "simple.properties";
	String JDBC_DRIVER = "simple.framework.jdbc.driver";
	String JDBC_URL="simple.framework.jdbc.url";
	String JDBC_USER="simple.framework.jdbc.username";
	String JDBC_PASS="simple.framework.jdbc.pass";
	
	String APP_PACKAGE="simple.framework.app.base_package";//应用的基础包名
	String APP_JSP_PATH="simple.framework.app.jsp_path";
	String APP_DEF_JSP_PATH="simple.framework.app.def_path";//默认页面
	String APP_STATIC_PATH="simple.framework.app.static_path";

	String APP_UPLOAD_LIMIT = "simple.framework.app.upload_limit";
}

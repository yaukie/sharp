package org.simple.framework.helper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * servlet 助手类
 * @author yuenbin
 *
 */
public class ServletHelper {

	private static final Logger log = LoggerFactory.getLogger(ServletHelper.class);
	
	//保证每个线程请求安全,独立
	private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<ServletHelper>();
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private ServletHelper(HttpServletRequest request,HttpServletResponse response ){
		this.request=request;
		this.response=response;
	}
	
	public static void init (HttpServletRequest request,HttpServletResponse response ){
		SERVLET_HELPER_HOLDER.set(new ServletHelper(request,response));
	}
	
	public static void destroy(){
		SERVLET_HELPER_HOLDER.remove();
	}
	
	/**
	 * 获取 request 对象
	 * @return
	 */
	private static HttpServletRequest getRequest()
	{
		return  SERVLET_HELPER_HOLDER.get().request;
	}
	
	/**
	 * 获取response对象
	 * @return
	 */
	private static HttpServletResponse getResponse(){
		return SERVLET_HELPER_HOLDER.get().response;
	}
	
	/**
	 * 获取 session 对象
	 * @return
	 */
	private static HttpSession getSession(){
		return getRequest().getSession();
	}
	
	/**
	 * 获取 ServletContext 对象
	 * @return
	 */
	private static ServletContext getServletContext()
	{
		return getRequest().getServletContext();
	}
	
	
	
}

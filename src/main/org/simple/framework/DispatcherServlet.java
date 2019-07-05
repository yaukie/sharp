package org.simple.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.simple.framework.bean.Handler;
import org.simple.framework.bean.Request;
import org.simple.framework.helper.BeanHelper;
import org.simple.framework.helper.ConfigHelper;
import org.simple.framework.helper.ControllerHelper;
import org.simple.framework.helper.RequestHelper;
import org.simple.framework.helper.ServletHelper;
import org.simple.framework.helper.UploaderHelper;
import org.simple.framework.param.Params;
import org.simple.framework.reflect.ReflectionUtil;
import org.simple.framework.util.JsonUtil;
import org.simple.framework.util.StringUtil;
import org.simple.framework.view.Data;
import org.simple.framework.view.View;

/**
 *servlet 请求转发器
 *请求转发最核心的部分
 * @author YEBN
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns="/*",loadOnStartup=0)
public class DispatcherServlet extends HttpServlet { 
	private static final Logger log =LoggerFactory.getLogger(DispatcherServlet.class);
	private static Request request ;
 
 @Override
public void init() throws ServletException {
	 //初始化框架类
	 HelperLoader.init();
	 //往容器注册静态资源
	 ServletContext context = getServletConfig().getServletContext();
	 register(context);
	 //初始化文件上传请求
	 UploaderHelper.init(context);
	 
 	}
 
 @Override
public void service(HttpServletRequest req, HttpServletResponse rep)
		throws ServletException, IOException {
	 	//初始化servlet api 
	 	ServletHelper.init(req,rep);
	 	try
	 	{
	 	String requestMethod = req.getMethod().toLowerCase();
	 	String requestUrl = req.getPathInfo();
	 	//过滤掉静态资源请求 
	 	if(requestUrl.startsWith("/*.ico"))
	 	{
	 		return;
	 	}
	 	
	 	//初始化请求处理对象
	 	Handler handler = ControllerHelper.getHandler(requestMethod, requestUrl);
	 	if(handler !=null )
	 	{
	 	 	//目标方法
		 	Method method = handler.getActionMethod();
		 	//目标类
		 	Class<?> targetClass = handler.getControllerClass();
		 	//目标实例化对象
		 	Object targetObj = BeanHelper.getBean(targetClass);//ReflectionUtil.newInstance(targetClass);
		 	//目标方法返回结果
		 	Object resultObj ; 
		 	 //处理请求参数
		 	Params params ;
		 	if(UploaderHelper.isMultiRequest(req))
		 	{
		 		params = UploaderHelper.createParam(req);
		 	}else
		 	{
		 		params = RequestHelper.createParams(req);
		 	}
		 	
		 	if(params.isEmpty()  ){
		 		resultObj =  ReflectionUtil.invoke(targetObj, method);
		 	}else
		 	{
		 		resultObj = ReflectionUtil.invoke(targetObj, method, params);
		 	}
		 	
		 	if(resultObj != null )
		 	{
		 		if(resultObj instanceof View )
		 		{
		 			this.handleViewResult((View)resultObj, req, rep);
		 		}else if (resultObj instanceof Data){
		 			this.handleDataResult((Data)resultObj, rep);
		 		}
		 	}
		 	
	 		}
	 	}finally{
	 		ServletHelper.destroy();
	 	}
}
 
 /**
  * 注册静态资源
  * jsp ,default resources 
  * @param context
  */
 private void register(ServletContext context ){
	 
	 //jsp
	 ServletRegistration jspServlet = context.getServletRegistration("jsp");
	 
	 jspServlet.addMapping(ConfigHelper.getAppJspPath()+"*");
	 
	 ServletRegistration defaultServlet = context.getServletRegistration("default");
	 jspServlet.addMapping("*");
	 
 }
 
 /**
  * 返回json数据
  * @param request
  * @return
 * @throws IOException 
  */
 private void	 handleDataResult (Data data, HttpServletResponse rep) throws IOException{
	 
	 	if(data != null )
	 	{
	 			Object obj = data.getModel();
	 			rep.setContentType("application/json");
	 			rep.setCharacterEncoding("UTF-8");
	 			PrintWriter pw = rep.getWriter();
	 			String json = JsonUtil.toJson(obj);
	 			pw.write(json);
	 			pw.flush();
	 			pw.close();
	 	}
	 
 }
 
 /**
  * 封装视图
  * @param request
  * @return
 * @throws IOException 
 * @throws ServletException 
  */
 private void handleViewResult(View view , HttpServletRequest request,HttpServletResponse rep) throws IOException, ServletException{
	 
	 if( view != null ){
		 	
		String path =  view.getPath();
		if(StringUtil.isNotEmpty(path))
		{
			//是一个servlet 请求
			if(path.startsWith("/")){
				rep.sendRedirect(path);
			}else
			{
			Map<String,Object> map = 	view.getModel();
			for(Map.Entry<String, Object> entry :map.entrySet() )
			{
				request.setAttribute(entry.getKey(),entry.getValue());
			}
		
			request.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(request, rep);
			
			}
		}
	 }
 }
 
 
}

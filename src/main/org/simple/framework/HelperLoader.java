package org.simple.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.simple.framework.helper.AopHelper;
import org.simple.framework.helper.BeanHelper;
import org.simple.framework.helper.ClassHelper;
import org.simple.framework.helper.ControllerHelper;
import org.simple.framework.ioc.IocHelper;
import org.simple.framework.util.ClassUtil;

/**
 * 初始化对应的helper类
 * @author YEBN
 *
 */
public final class HelperLoader {

	private static Logger log = LoggerFactory.getLogger(HelperLoader.class);
	
	public static void init()
	{
		if(log.isDebugEnabled())
		{
			log.debug("init  初始化工具类...");
		}
		Class<?>[] clsArrays ={
				ClassHelper.class,//获取的所有bean类
				BeanHelper.class,//封装类跟实例
				AopHelper.class,//加载aop框架
				IocHelper.class,//初始化注解类
				ControllerHelper.class//封装请求处理映射
		};
		
		for(Class<?> cls : clsArrays)
		{
			ClassUtil.loadClass(cls.getName(), true);
		}
		if(log.isDebugEnabled())
		{
			log.debug("HelperLoader 初始化工具类完成...");
		}
	}
	
}

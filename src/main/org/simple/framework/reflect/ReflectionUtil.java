package org.simple.framework.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author yebn
 * 通过反射来实例化一个class,并获取对象
 *
 */
public final class ReflectionUtil {

	private static final  Log log = LogFactory.getLog(ReflectionUtil.class);
	
	/**
	 * 创建实例
	 * @param cls
	 * @return
	 */
	public static Object newInstance(Class<?> cls)
	{
		Object instance = null;
		
		try {
			instance = cls.newInstance();
		} catch (Exception e) {
			if(log.isErrorEnabled())
			{
				log.error("new instance failure ",e);
				throw new RuntimeException(e);
			}
		}  
		
		return instance;
		
	}
	
	/**
	 * 调用实例对象的方法
	 * 无参数的情况下反向调用
	 * @param obj
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invoke(Object obj,Method method)
	{
		Object instance = null;
		method.setAccessible(true);
		try {
			instance=method.invoke(obj, null);
		} catch (Exception e) {
		 if(log.isErrorEnabled())
		 {
			 log.error(" get method failure..",e);
			 throw new RuntimeException(e);
		 }
		}  
		return instance;
	}
	
	/**
	 * 调用实例对象的方法
	 * @param obj
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invoke(Object obj,Method method,Object ... args)
	{
		Object instance = null;
		method.setAccessible(true);
		try {
			instance=method.invoke(obj, args);
		} catch (Exception e) {
		 if(log.isErrorEnabled())
		 {
			 log.error(" get method failure..",e);
			 throw new RuntimeException(e);
		 }
		}  
		return instance;
	}
	
	/**
	 * 设置属性
	 * @param obj
	 * @param field
	 * @param value
	 */
	public static void setFiled(Object obj ,Field field,Object value)
	{
		
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			if(log.isErrorEnabled())
			{
				log.error("set field failure.",e);
				throw new RuntimeException(e);
			}
		}  
	}
	
}

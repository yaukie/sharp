package org.simple.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simple.framework.reflect.ReflectionUtil;

/**
 * 获取bean实例
 * 所有注解类对应的实例,
 * 并封装到BEAN_MAP中
 * @author yebn
 */
public final class BeanHelper {

	private static final Log log = LogFactory.getLog(BeanHelper.class);
	
	public static final Map<Class<?>,Object> BEAN_MAP = new HashMap();
	
	static
	{
		//加载所有的bean实例对象,存放到map集合中
		Set<Class<?>> set = ClassHelper.getBeanClassSet();
		if(set.size()>0)
		{
			for(Class cls : set )
			{
				BEAN_MAP.put(cls, ReflectionUtil.newInstance(cls));
			}
		}
		
	}
	
	/**
	 * 返回初始化的beanmap
	 * bean 映射集合
	 * @return
	 */
	public  static Map<Class<?>,Object> getBeanMap()
	{
		return BEAN_MAP;
	}
	
	/**
	 * 根据 一个具体的类,返回实例对象
	 * 返回的肯定是T类的实例对象,,用自定义泛型T
	 * @param cls
	 * @return
	 */
	public static <T> T getBean(Class<T> cls)
	{
		Map map = getBeanMap();
		if(!map.containsKey(cls))
		{
			if(log.isErrorEnabled())
			{
				log.error("getbean failed ..");
			}
			throw new RuntimeException("bean map does not contains key ="+cls.getName());
			
		}
		
		return (T) map.get(cls);
	}
	
	
	public static void setBean(Class<?> cls,Object obj)
	{
		BEAN_MAP.put(cls, obj);
	}
			
}

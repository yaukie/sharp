package org.simple.framework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.simple.framework.annotation.Aspect;
import org.simple.framework.annotation.Service;
import org.simple.framework.proxy.AspectProxy;
import org.simple.framework.proxy.Proxy;
import org.simple.framework.proxy.ProxyManager;
import org.simple.framework.proxy.TransactionProxy;

/**
 * 封装一个apsect的工具类
 * @author yaukie
 *@modified by yaukie 添加事务控制
 */
public class AopHelper {
	private static final Logger log=LoggerFactory.getLogger(AopHelper.class);
	
	//初始化aop框架
	static{
		try {
			Map <Class<?>,Set<Class<?>>> proxyMap = createProxyMap();
			Map <Class<?>,List<Proxy>> targetMap = createTargetMap(proxyMap);
			for(Map.Entry<Class<?>,List<Proxy>> entry : targetMap.entrySet())
			{
				Class<?> targetClass = entry.getKey();
				List<Proxy> proxyList = entry.getValue();
				//创建代理对象
				Object obj = ProxyManager.createProxy(targetClass, proxyList);
				//将代理对象重新放入映射集合中,
				BeanHelper.setBean(targetClass, obj);
			}
		} catch (Exception e) {
			log.error("aop init failure ");
		}
	}
	
	/**
	 * 构造一个带有aspect注解的类
	 * @param aspect
	 * @return
	 */
	private static Set<Class<?>> createTargetClassSet(Aspect aspect)
	{
		Set<Class<?>> targetClass = new HashSet<Class<?>>();
		Class<? extends Annotation> annotation = aspect.value();
		if(annotation !=null && !annotation.equals(Aspect.class))
		{
			targetClass.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}
		return targetClass;
	}
	
	/**
	 * 获取切面类跟目标类的映射关系
	 * @return
	 */
	public static Map<Class<?>,Set<Class<?>>> createProxyMap()
	{
		Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<Class<?>,Set<Class<?>>>(); 
		addAspectProxy(proxyMap);
		addTransactionProxy(proxyMap);
		return proxyMap;
	}
	
	/**
	 * 获取切面类与目标类的关系
	 * @param proxyMap
	 */
	private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap)
	{
		Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		for(Class<?> cls : proxyClassSet )
		{
			if(cls.isAnnotationPresent(Aspect.class))
			{
				Aspect aspect = cls.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
				proxyMap.put(cls, targetClassSet);
			}
		}
	}
	
	/**
	 * 获取事务类与目标类的关系
	 * 代理类与目标类关系
	 * @param proxyMap
	 */
	private static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap)
	{ 
			Set<Class<?>> targetClass = ClassHelper.getClassSetByAnnotation(Service.class);
			proxyMap.put(TransactionProxy.class, targetClass);
		
	}
	
	/**
	 *获取目标类与代理对象之间的关系
	 * @param proxyMap
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static Map<Class<?>,List<Proxy>> createTargetMap (Map<Class<?>,Set<Class<?>>> proxyMap ) 
			throws Exception
	{
		Map<Class<?>, List<Proxy>>  targetMap = new HashMap<Class<?>,List<Proxy>>();
		for(Map.Entry<Class<?>, Set<Class<?>>> entity : proxyMap.entrySet())
		{
			Class<?> proxyClass =entity.getKey();
			Set<Class<?>> targetClass = entity.getValue();
			for(Class<?> cls : targetClass )
			{
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if(targetMap.containsKey(cls))
				{
					targetMap.get(cls).add(proxy);
				}else
				{
					List<Proxy> list = new ArrayList<Proxy>();
					list.add(proxy);
					targetMap.put(cls, list);
				}
			}
		}
		return targetMap;
	}
	
}
 

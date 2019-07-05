package org.simple.framework.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 用于创建代理对象
 * @author yaukie
 *
 */
public class ProxyManager {

	@SuppressWarnings("unchecked")
	public static <T> T createProxy(final Class<?> targetClass,final List<Proxy> proxyList)
	{
		return (T) Enhancer.create(targetClass,new MethodInterceptor() {
			public Object intercept(Object target, Method methd, Object[] args,
					MethodProxy proxy) throws Throwable {
				return new ProxyChain(targetClass, target, methd, proxy, args, proxyList).doProxyChain();
			}
		});
		
	}
	
}

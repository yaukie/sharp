package org.simple.framework.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;
/**
 * 
 * @author yaukie
 *  代理链:
 *  将多个代理通过一条链子串起来,一个一个的去执行,
 *  顺序为添加到链上的代理顺序
 */
public class ProxyChain {

	private final Class<?> targetClass;
	private final Object targetObject;
	private final Method targetMethod;
	private final MethodProxy methodProxy;
	private final Object[] methodParams;
	
	private List<Proxy> proxyList = new ArrayList<Proxy>();
	private int proxyIndex = 0 ;
	
	public ProxyChain(Class<?> targetClass,Object targetObject,
			Method targetMethod,MethodProxy methodProxy,
			Object[] methodParams,List<Proxy> proxyList){
		this.targetClass=targetClass;
		this.targetObject=targetObject;
		this.targetMethod=targetMethod;
		this.methodProxy=methodProxy;
		this.methodParams=methodParams;
		this.proxyList=proxyList;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public Object[] getMethodParams() {
		return methodParams;
	}
	
	public Object doProxyChain() throws Throwable{
		Object methodResult;
		if(this.proxyIndex < this.proxyList.size())
		{
			methodResult = this.proxyList.get(proxyIndex++).doProxy(this);
		}else
		{
			methodResult = methodProxy.invokeSuper(targetObject, methodParams);
		}
		return methodResult;
	}
	
}

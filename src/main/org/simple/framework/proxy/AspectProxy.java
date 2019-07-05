package org.simple.framework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象模版类
 * 用于在目标方法的前后执行相应的逻辑
 * @author yaukie
 *
 */
public abstract class AspectProxy implements Proxy {
	
	private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

	public final Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		Class<?> cls = proxyChain.getTargetClass();
		Method method= proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();
		begin();
		try
		{
			if(intercept(cls,method,params))
			{
				before(cls,method,params);
				 result = proxyChain.doProxyChain();
				after(cls,method,params,result);
			}else
			{
				result = proxyChain.doProxyChain();
			}
		}catch(Exception e )
		{
			logger.error("proxy failure",e );
			error(cls,method,params,e);
		}finally
		{
			end();
		}
	
		return null;
	}
	
	public boolean intercept(Class<?> cls,Method method,Object[] params ) 
	{
		return true;
	}
	
	public void begin()
	{
		
	}
	
	
	public void before (Class<?> cls,Method method,Object[] params ) throws Throwable{
		
	}
	
	public void after(Class<?> cls,Method method,Object[] params ,Object result) throws Throwable
	{
		
	}
	
	public void error(Class<?> cls,Method method,Object[] params ,Throwable e ) throws Throwable
	{
		
	}
	
	public void end()
	{
		
	}

}

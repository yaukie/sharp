package org.simple.framework.proxy;

import java.lang.reflect.Method;

import org.simple.framework.annotation.Transaction;
import org.simple.framework.helper.DatabaseHelper;

/**
 * 事务代理切面
 * @author yaukie
 *
 */
public class TransactionProxy implements Proxy {

	private static ThreadLocal<Boolean> HOLDER = new ThreadLocal<Boolean>(){
		protected Boolean initialValue() {
			return false;
		};
	};
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null ;
		//获取目标方法
		Method method = proxyChain.getTargetMethod();
		boolean flag =HOLDER.get();
		try
		{
			if(!flag && method.isAnnotationPresent(Transaction.class))
			{
				HOLDER.set(true);
				//开启事务
				DatabaseHelper.beginTransaction();
				result = proxyChain.doProxyChain();
				//提交事务
				DatabaseHelper.commitTransaction();
			}else{
				result = proxyChain.doProxyChain();
			}
		}catch(Exception e)
		{
			DatabaseHelper.rollabackTransaction();
			throw e;
		}finally
		{
			HOLDER.remove();
		}
		return result;
}

}

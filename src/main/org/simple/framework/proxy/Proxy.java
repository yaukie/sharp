package org.simple.framework.proxy;
/**
 * 
 * @author yaukie
 * 代理接口
 */
public interface Proxy {

	/**
	 * 执行链式代理
	 * @param proxyChain
	 * @return
	 * @throws Throwable
	 */
	Object doProxy(ProxyChain proxyChain) throws Throwable;
	
}

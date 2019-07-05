package org.simple.framework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 请求方法
 * @author yebn
 *  action注解的类需要得到请求对象
 *  进而获取请求路径与请求方法,此类的作用
 *  就是封装一个请求对象
 */
public class Request {

	private String requestMethod;
	private String requestPath;
	
	public Request(String requestMethod,String requestPath)
	{
		this.requestMethod=requestMethod;
		this.requestPath=requestPath;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public String getRequestPath() {
		return requestPath;
	}
	
	
	@Override
	public int hashCode() {
//		return HashCodeBuilder.reflectionHashCode(this);
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this,obj);
	}
	
}

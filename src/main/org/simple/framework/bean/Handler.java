package org.simple.framework.bean;

import java.lang.reflect.Method;

/**
 * 请求处理方法
 * 该类的作用主要是处理reques对象
 * @author yebn
 *
 */
public class Handler {

	/**
	 * contoller 类
	 */
	
	private Class<?> controllerClass;
	
	/**
	 * contoller 处理方法
	 */
	
	private Method actionMethod;
	
	
	public Handler(Class<?> controllerClass,Method actionMethod)
	{
		this.controllerClass=controllerClass;
		this.actionMethod=actionMethod;
	}


	public Class<?> getControllerClass() {
		return controllerClass;
	}


	public Method getActionMethod() {
		return actionMethod;
	}
	
	
}


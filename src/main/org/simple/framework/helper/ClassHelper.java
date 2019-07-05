package org.simple.framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.sql.rowset.serial.SerialArray;

import org.simple.framework.annotation.Controller;
import org.simple.framework.annotation.Service;
import org.simple.framework.util.ClassUtil;


/**
 * 
 * @author yebn 
 * 获取指定应用包名下所有bean类
 * 注意:所谓bean类 指的是被service controller 注解的类
 */
public final class ClassHelper {

	private static final Set<Class<?>> CLASS_SET  ;
	
	static
	{
		String basePackage = ConfigHelper.getAppBasePackage();//获取基础包名
		CLASS_SET=ClassUtil.getClassSet(basePackage);//获取该包下的所有类
	}
	
	public static Set<Class<?>> getClassSet()
	{
		return CLASS_SET;
	}
	
	
	/**
	 * 获取service注解的类
	 * @return
	 */
	private static Set<Class<?>> getServiceClassSet()
	{
		Set<Class<?>> set = new HashSet();
		if(getClassSet().size()>0)
		{
			for(Class cls : getClassSet())
			{
				if(cls.isAnnotationPresent(Service.class))
					set.add(cls);
			}
		}
		
		return set;
	
	}
	
	/**
	 * 获取controller 注解的类
	 * @return
	 */
	private static Set<Class<?>> getControllerClassSet()
	{
		Set<Class<?>> set = new HashSet();
		if(getClassSet().size()>0)
		{
			for(Class cls : getClassSet())
			{
				if(cls.isAnnotationPresent(Controller.class))
					set.add(cls);
			}
		}
		
		return set;
	
	}
	
	/**
	 * 返回所有的bean类
	 * =service + controller
	 * @return
	 */
	public static Set<Class<?>> getBeanClassSet()
	{
		Set<Class<?>> set = new HashSet();
		set.addAll(getControllerClassSet());
		set.addAll(getServiceClassSet());
		return set;
	}
	
	/**
	 * 获取父类(接口)的所有子类（实现类）
	 * @param superClass
	 * @return
	 */
	public static  Set<Class<?>> getClassSetBySuper(Class<?> superClass)
	{
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> cls : CLASS_SET)
		{
			if(superClass.isAssignableFrom(cls) && !cls.equals(superClass)){
				classSet.add(cls);
			}
		}
		
		return classSet;
	}
	
	/**
	 * 获取带有某种注解的类
	 * @param annotationClass
	 * @return
	 */
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass)
	{
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> cls : CLASS_SET)
		{
			if(cls.isAnnotationPresent(annotationClass))
			{
				classSet.add(cls);
			}
		}
		return classSet;
		
	}
	
}

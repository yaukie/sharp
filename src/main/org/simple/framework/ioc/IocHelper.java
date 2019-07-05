package org.simple.framework.ioc;

import java.lang.reflect.Field;
import java.util.Map;

import org.simple.framework.annotation.Inject;
import org.simple.framework.helper.BeanHelper;
import org.simple.framework.reflect.ReflectionUtil;
import org.simple.framework.util.ArrayUtil;
import org.simple.framework.util.CollectionUtil;

/**
 * 
 * @author yebn 
 * 实现service注解成员变量的实例化助手
 * 也即:依赖注入助手
 * 通过获取所有的bean 实例对象,通过反射获取
 * 类的成员变量,实现实例化过程
 */
public final class IocHelper {

	static
	{
		Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
		
		if(CollectionUtil.isNotEmpty(beanMap))
		{
			for(Map.Entry<Class<?>, Object> entry : beanMap.entrySet())
			{
				Class<?> cls = entry.getKey();
				Object beanInstance = entry.getValue();
				//获取该实例的成员变量
				Field[] fields = cls.getDeclaredFields();
				//遍历该类的成员变量
				if(ArrayUtil.isNotEmpty(fields)){
					for(Field field : fields)
					{
						//判断该变量是否被inject注解
						if(field.isAnnotationPresent(Inject.class))
						{
							//
							Class<?> fieldClass=field.getType();
							//获取注解实例
							Object fieldInstance = beanMap.get(fieldClass);
							if(fieldInstance !=null )
							{
								//通过反射初始化 该成员变量的值
								ReflectionUtil.setFiled(beanInstance, field, fieldInstance);
							}
						}
						
					}
					
				}
			}
		}
	}
}

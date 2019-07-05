package org.simple.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.simple.framework.annotation.Action;
import org.simple.framework.annotation.Controller;
import org.simple.framework.bean.Handler;
import org.simple.framework.bean.Request;
import org.simple.framework.util.ArrayUtil;
import org.simple.framework.util.CollectionUtil;

/**
 * 控制器助手
 * @author yebn
 *
 */
@SuppressWarnings("unchecked")
public final class ControllerHelper {

	//定义请求处理映射对象
private static final Map<String,Handler> ACTION_MAP = new HashMap();

private static final Logger log = Logger.getLogger(ControllerHelper.class);
//初始化对象

static
{
	if(log.isDebugEnabled())
	{
		log.debug("ControllerHelper 控制器助手开始初始化");
	}
	Set<Class<?>> set =ClassHelper.getBeanClassSet();
	log.debug("ControllerHelper SET [ "+ set+" ]" );
	if(CollectionUtil.isNotEmpty(set))
	{
		for(Class cls : set )
		{
			//找到注解为Controler的类型
			if(cls.isAnnotationPresent(Controller.class))
			{
				//获取所有定义的方法
				Method[] methods = cls.getDeclaredMethods();
				if(ArrayUtil.isNotEmpty(methods))
				{
					for(Method method : methods)
					{
						//判断该方法是否被Action 注解
						if(method.isAnnotationPresent(Action.class))
						{
							Action action = method.getAnnotation(Action.class);
							String value = action.value();
							//获取url的映射规则,,,
							if(value.matches("\\w+:/\\w*"))//与“[A-Za-z0-9_]”等效
							{
//								String[] array = value.split(":");
								Handler handler = new Handler(cls,method);
								ACTION_MAP.put(value, handler);
//								if(ArrayUtil.isNotEmpty(array) && array.length==2)
//								{
//									String requestMethod = array[0];
//									String requestUrl = array[1];
////									Request request = new Request(requestMethod,requestUrl);
//									Handler handler = new Handler(cls,method);
//									ACTION_MAP.put(value, handler);
//								}
							}
						}
					}
				}
			}
		}
		log.debug("ControllerHelper ACTION_MAP "+ACTION_MAP);
	}
	
	if(log.isDebugEnabled())
	{
		log.debug("ControllerHelper 控制器助手初始化完成");
	}
}


/**
 * 根据请求获取具体的处理方法
 * @param requestMethod
 * @param requestUrl
 * @return
 */
 public static Handler getHandler(String requestMethod,String requestUrl)
 {
	 return ACTION_MAP.get(requestMethod+":"+requestUrl);
 }
	
}

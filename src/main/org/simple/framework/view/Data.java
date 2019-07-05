package org.simple.framework.view;

/**
 * 框架Action注解返回的
 * 有可能是json数据,
 * 因此需要封装一下返回类型,
 * 返回的为数据对象
 * @author yebn
 *
 */
public class Data {

private Object model;

public Data(Object model)
{
	this.model=model;
}

public Object getModel()
{
	return model;
}

	
}

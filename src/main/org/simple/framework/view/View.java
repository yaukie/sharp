package org.simple.framework.view;

import java.util.HashMap;
import java.util.Map;

/**
 * 提供Action方法返回的两种类型
 * View 为视图类型,解析之后为具体的jsp
 * 返回jsp视图
 * @author yebn
 *
 */
public class View {

	//jsp具体的路径
	private String path;
	
	//该视图封装的数据模型
	private Map<String,Object> model;
	
	public View(String path)
	{
		this.path=path;
		model= new HashMap<String,Object>();
	}
	
	
	public String getPath()
	{
		return path;
	}
	
	public Map<String,Object> getModel()
	{
		return model;
	}
	
	/**
	 * 封装视图用到的
	 * 数据模型,,
	 * @param key
	 * @param value
	 * @return
	 */
	public View addModel(String key,Object value)
	{
		model.put(key, value);
		return this;
	}
	
}

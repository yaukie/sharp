package org.simple.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json数据处理转换工具
 * @author YEBN
 *
 */
public final class JsonUtil {

	private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	public static <T> String toJson( T obj){
		String json = null;
		try {
			json = OBJECT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			if(log.isErrorEnabled())
			{
				log.error("json transform exception ",e);
			}
		}
		return json;
	}
	
	public static <T> T  fromJson(String json,Class<T> type)
	{
		T pojo = null;
		
		try {
			pojo = OBJECT_MAPPER.readValue(json, type);
		} catch (Exception e) {
			if(log.isInfoEnabled())
			{
				log.error("transform from json failure ",e);
			}
		} 
		return pojo;
	}
	
	
	
}

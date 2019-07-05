package org.simple.framework.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.simple.framework.param.FormParam;
import org.simple.framework.param.Params;
import org.simple.framework.util.ArrayUtil;
import org.simple.framework.util.StreamUtil;
import org.simple.framework.util.StringUtil;


/**
 * 请求助手类
 * @author yuenbin
 *
 */
public class RequestHelper {
	
	public static Params createParams(HttpServletRequest request ){
		 List<FormParam> formParamList = new ArrayList<FormParam>();
		 formParamList.addAll(parseParameterNames(request));
		 formParamList.addAll(parseInputStreamNames(request));
		 
		 return new Params(formParamList);
	}
	
	/**
	 * 解析 request 请求
	 * @param request
	 * @return
	 */
	private static List<FormParam> parseParameterNames(HttpServletRequest request ){
		
		 List<FormParam> formParamList = new ArrayList<FormParam>();
		 Enumeration<String> paramNames = request.getParameterNames();
		 while(paramNames.hasMoreElements()){
			 String fieldName = paramNames.nextElement();
			 String fieldValue="";
			 String[] fieldValues = request.getParameterValues(fieldName);
			 if(ArrayUtil.isNotEmpty(fieldValues))
			 {
				 if(fieldValues.length==1){
					 fieldValue=fieldValues[0];
				 }else
				 {
					 StringBuilder sb = new StringBuilder("");
					 for(int i=0;i<fieldValues.length;i++){
						 sb.append(fieldValues[i]);
						 if(i != fieldValues.length-1){
						  sb.append(StringUtil.SEPARATOR);
						 }
					 }
					 fieldValue = sb.toString();
				 }
				 formParamList.add(new FormParam(fieldName,fieldValue));
			 }
		 }
		 return formParamList;
	}
	
	/**
	 * 解析 request 输入流的参数
	 * @param request
	 * @return
	 */
	private static List<FormParam> parseInputStreamNames(HttpServletRequest request ){
		 List<FormParam> formParamList = new ArrayList<FormParam>();
			String body;
			try {
				body = StreamUtil.getBody(request.getInputStream());
				if(body !=null )
				{
					String[] arrays = StringUtils.split(body, "&");
					if(ArrayUtil.isNotEmpty(arrays))
					{
						for(int i=0;i<arrays.length;i++)
						{
							String[] temp = StringUtils.split(arrays[i],"=");
						 if(ArrayUtil.isNotEmpty(temp) && temp.length==2)
						 {
							 formParamList.add(new FormParam(temp[0],temp[1]));
						 }
						}
					}
				}
			} catch (IOException e) {
				
			}
		 return formParamList;
	}
}

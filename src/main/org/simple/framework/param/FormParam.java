package org.simple.framework.param;


/**
 * 
 * @author yuenbin
 * 表单参数封装
 */
public class FormParam {
	
	private String fieldName;//表单域名
	private String fieldValue;//域值
	
	public FormParam(String fieldName, String fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue=fieldValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

 
}

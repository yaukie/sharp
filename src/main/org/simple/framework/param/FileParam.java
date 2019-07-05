package org.simple.framework.param;

import java.io.InputStream;

/**
 * 
 * @author yuenbin
 * 文件上传参数封装
 */
public class FileParam {
	
	private String fieldName;//表单域名
	private String fileName;//文件名
	private long fileSize;//文件大小
	private String contentType;//文件类型
	private String filePath;//文件路径

	private InputStream inputStream;//文件流
	 
	
	
	public FileParam(String fieldName, String fileName, long fileSize,
			String contentType, String filePath, InputStream inputStream) {
		this.fieldName = fieldName;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.filePath = filePath;
		this.inputStream = inputStream;
	}



	public String getFieldName() {
		return fieldName;
	}



	public String getFileName() {
		return fileName;
	}



	public long getFileSize() {
		return fileSize;
	}



	public String getContentType() {
		return contentType;
	}



	public String getFilePath() {
		return filePath;
	}



	public InputStream getInputStream() {
		return inputStream;
	}
	
	
	
	
}

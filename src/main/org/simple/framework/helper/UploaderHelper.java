  package org.simple.framework.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.simple.framework.param.FileParam;
import org.simple.framework.param.FormParam;
import org.simple.framework.param.Params;
import org.simple.framework.util.CollectionUtil;
import org.simple.framework.util.FileUtil;
import org.simple.framework.util.StreamUtil;
import org.simple.framework.util.StringUtil;

/**
 * 文件上传功能
 * @author yuenbin
 *
 */
public class UploaderHelper {

	private static  final Logger log = LoggerFactory.getLogger(UploaderHelper.class);
	
	//文件上传对象
	private static ServletFileUpload serlvetFileUpload ; 
	
	public static void init (ServletContext servletContext ){
		//临时目录
		File repository = (File)servletContext.getAttribute("javax.servlet.context.tempdir");
		serlvetFileUpload =new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,
				repository));
		//上传最大限制
		int uploadLimit = ConfigHelper.getAppUploadLimit();
		if(uploadLimit !=0 ){
			serlvetFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024 );
		}
		
	}
	
	/**
	 * 核心方法,解析请求参数
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static Params createParam(HttpServletRequest request ) throws IOException {
		List<FormParam> formParamList = new ArrayList<FormParam>();
		List<FileParam> fileParamList = new ArrayList<FileParam>();
		//解析上传请求,判断是何种类型 普通form 还是 上传文件请求
		try {
			Map<String,List<FileItem>> fileItemParamMap = serlvetFileUpload.parseParameterMap(request);
			if(CollectionUtil.isNotEmpty(fileItemParamMap)){
				for(Map.Entry<String , List<FileItem>> entry : fileItemParamMap.entrySet() )
				{
					String fieldName = entry.getKey();
					List<FileItem> fileItemList = entry.getValue();
					if(CollectionUtil.isNotEmpty(fileItemList))
					{
						for(FileItem fileItem : fileItemList)
						{
							//如果是普通表单请求
							if(fileItem.isFormField()){
								String fieldValue = fileItem.getString("UTF-8");
								formParamList.add(new FormParam(fieldName, fieldValue));
							}else{
									String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(),
											"UTF-8"));
									if(StringUtil.isNotEmpty(fileName)){
										long  fileSize = fileItem.getSize();
										String contentType = fileItem.getContentType();
										InputStream input = fileItem.getInputStream();
										fileParamList.add(new FileParam(fieldName,fileName,fileSize,contentType,"",input));
									}
									
							}
						}
					}
				}
			}
		} catch (FileUploadException e) {
			log.error("parse file upload request failure ",e);
		}
		
		return new Params(fileParamList,formParamList);
	}
	
	/**
	 * 文件上传方法
	 * @param basePath
	 * @param fileParam
	 */
	public static void uploadFile(String basePath,FileParam fileParam){
			
		if(fileParam != null ){
			String filePath = null ;
			try {
				filePath = basePath + fileParam.getFileName();
				FileUtil.createFile(filePath);
				InputStream input = new BufferedInputStream(fileParam.getInputStream());
				OutputStream output = new BufferedOutputStream(new FileOutputStream(filePath));
				StreamUtil.copyStream(input, output);
			} catch (Exception  e) {
				log.error("upload file failure  ",e);
			}
		}
		
	}
	
	/**
	 * 批量上传文件
	 * @param basePath
	 * @param fileParamList
	 */
	public static void uploadFile(String basePath,List<FileParam>  fileParamList){
		try
		{
			if(CollectionUtil.isNotEmpty(fileParamList)){
				for(FileParam fileParam : fileParamList ){
					uploadFile(basePath, fileParam);
				}
			}
		
		}catch(Exception e ){
			log.error("batch upload file failure ",e);
		}

	}
	
	public static boolean isMultiRequest(HttpServletRequest request ){
		return  serlvetFileUpload.isMultipartContent(request);
	}
	
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
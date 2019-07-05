package org.simple.framework.param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simple.framework.util.CastUtil;
import org.simple.framework.util.CollectionUtil;
import org.simple.framework.util.StringUtil;

/**
 * 封装参数类
 * @author YEBN
 *
 */
public class Params {

	//上传文件参数
	private   List<FileParam> fileParamList;
	
	//普通表单参数
	private   List<FormParam> formParamList;
	private static Map<String,Object> paramMap;
	
	public Params(Map<String,Object> paramMap)
	{
		this.paramMap=paramMap;
	}
	
	public Params(List<FormParam> formParamList)
	{
		this.formParamList=formParamList;
	}
	
	public Params( List<FileParam> fileParamList, List<FormParam> formParamList)
	{
		this.fileParamList=fileParamList;
		this.formParamList=formParamList;
	}
	
	/**
	 *  表单参数
	 * @return
	 */
	public Map<String,Object> getFiledMap(){
		Map<String,Object> fieldMap = new HashMap<String,Object>();
		if(CollectionUtil.isNotEmpty(formParamList)){
			for(FormParam formParam : formParamList){
				String fieldName = formParam.getFieldName();
				Object fieldVal = formParam.getFieldValue();
				if(fieldMap.containsKey(fieldName)){
					fieldVal=fieldMap.get(fieldName)+StringUtil.SEPARATOR+fieldVal;
				}
				fieldMap.put(fieldName, fieldVal);
			}
			
		}
		
		return fieldMap;
	}
	
	/**
	 *  上传文件参数
	 * @return
	 */
	public Map<String,List<FileParam>> getFileMap(){
		Map<String,List<FileParam>> fileMap = new HashMap<String,List<FileParam>>();
		if(CollectionUtil.isNotEmpty(fileParamList)){
			for(FileParam fileParam : fileParamList){
				String fieldName = fileParam.getFieldName();
				List<FileParam> fileTmpParamList ;
				if(fileMap.containsKey(fieldName)){
					fileTmpParamList=fileMap.get(fieldName);
				}else{
					fileTmpParamList=new ArrayList<FileParam>();
				}
				
				fileTmpParamList.add(fileParam);
				fileMap.put(fieldName, fileTmpParamList);
			}
			
		}
		
		return fileMap;
	}
	
	/**
	 *  获取所有的上传文件
	 * @param fieldName
	 * @return
	 */
	public List<FileParam> getFileList(String fieldName){
		return getFileMap().get(fieldName);
	}
	
	/**
	 * 获取唯一的上传文件
	 * @param fieldName
	 * @return
	 */
	public FileParam getFile(String fieldName){
		List<FileParam> fileParam = getFileList(fieldName);
		if(CollectionUtil.isNotEmpty(fileParam) && fileParam.size()==1){
			return getFileMap().get(fieldName).get(0);
		}
		return null;
	}
	
	
	
	public long getLong(String name)
	{
		return (long) CastUtil.castLong(paramMap.get(name));
	}
	
	public int getInt(String name)
	{
		return CastUtil.castInt(paramMap.get(name));
	}
	
	public Map<String,Object> getMap()
	{
		return paramMap;
	}
	
	public boolean isEmpty()
	{
		return CollectionUtil.isEmpty(paramMap);
	}
	
	public static void main(String[] args) {
		System.out.println(String.valueOf((char)29));
	}
}

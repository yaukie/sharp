package org.simple.framework.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 文件操作工具
 * @author yuenbin
 *
 */
public class FileUtil {

	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
	
	public static String getRealFileName(String fileName ){
		return FilenameUtils.getName(fileName);
	}
	
	/**
	 * 创建文件目录
	 * @param filePath
	 * @return
	 */
	public static  File createFile (String filePath){
		File file ;
		file = new File(filePath);
		File parentDir = file.getParentFile();
		try {
			if(!parentDir.exists()){
				FileUtils.forceMkdir(parentDir);
			}
			} catch (IOException e) {
				log.error("create file failure ",e);
				throw new RuntimeException(e);
			}
			
			return file;
		}
}

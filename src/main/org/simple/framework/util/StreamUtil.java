package org.simple.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取输入流桉树
 * @author YEBN
 */
public final class StreamUtil {

	private static Logger log = LoggerFactory.getLogger(StreamUtil.class);
	
	public static final String getBody(InputStream is )
	{
		
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		StringBuilder  readLine=new StringBuilder();
		String  line;
		try {
			while((line=buf.readLine()) !=null )
			{
				readLine.append(buf.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally
		{
			try {
				buf.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		return readLine.toString();
	}
	
	/**
	 *  复制输入流到输出流
	 * @param inputStream
	 * @param outputStream
	 */
	public static void  copyStream(InputStream inputStream,OutputStream outputStream ){
		 int length =0 ;
		 byte[] buffer = new byte[4*1024];
		 try {
			while((length=inputStream.read(buffer, 0, buffer.length))!=-1){
				 outputStream.write(buffer, 0, length);
			 }
		} catch (IOException e) {
			log.error("copy stream failure ",e);
			throw new RuntimeException(e);
		}finally{
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}

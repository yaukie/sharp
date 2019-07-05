package org.simple.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yebn
 * 类加载工具,
 * 主要通过此实现类加载器的功能
 */
public final class ClassUtil {

	private static final Logger log = LoggerFactory.getLogger(ClassUtil.class);
	
	/**
	 * 获取类加载器
	 * 当前线程的类加载器
	 * @return
	 */
	public static ClassLoader getClassLoader(){
		return  Thread.currentThread().getContextClassLoader();
	}
	
	/**
	 * 根据类的名称
	 * 获取类
	 * @param className
	 * @param isInitialized
	 * @return
	 */
	public static Class<?> loadClass(String className,boolean isInitialized )
	{
		Class cls = null ;
		try {
			cls = Class.forName(className, isInitialized, getClassLoader());
		} catch (ClassNotFoundException e) {
			if(log.isErrorEnabled())
			{
				log.error("load class error .......");
				throw new RuntimeException(e);
			}
		}//isInitialized 表示是否执行该类的静态代码块
		return cls;
	}
	
	/**
	 * 根据包名获取该包下面的所有类
	 * @param packageName
	 * @return
	 */
	public static Set<Class<?>> getClassSet(String packageName)
	{
		Set<Class<?>> set = new HashSet();
		Enumeration<URL> urls;
		try {
			urls = getClassLoader().getResources(packageName.replaceAll("\\.","/"));
			while(urls.hasMoreElements())
			{
				URL url = urls.nextElement();
				if(url != null)
				{
					String protocol = url.getProtocol();
					if(protocol.equals("file"))
					{
						String packagePath = url.getPath().replaceAll("%20", " ");
						addClass(set,packagePath,packageName);
					}else
					{
						JarURLConnection jarUrl = (JarURLConnection) url.openConnection();
						if(jarUrl !=null )
						{
							JarFile jarFile = jarUrl.getJarFile();
							Enumeration<JarEntry> jarEntries = jarFile.entries();
							while(jarEntries.hasMoreElements())
							{
								JarEntry je = jarEntries.nextElement();
								String jarEntryName = je.getName();
								if(jarEntryName.endsWith(".class"))
								{
									String className = jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
									doAddClass(set,className);
								}
							}
						}
					}
				}
				
			}
		} catch (IOException e) {
			if(log.isErrorEnabled())
			{
				log.error("get class set failure ....",e);
				throw new RuntimeException(e);
			}
		}
		return set;
	}
	
	 // 获取指定包名下指定父类的所有类
    public static Set<Class<?>> getClassListBySuper(String packageName, Class<?> superClass){
    	Set<Class<?>> set = new HashSet();
		Enumeration<URL> urls;
		try {
			urls=getClassLoader().getResources(packageName.replaceAll("\\.", "\\/"));
			while(urls.hasMoreElements())
			{
					URL url = urls.nextElement();
					if(url !=null )
					{
					String protocol = url.getProtocol();
					if(protocol.equals("file"))
					{
						String packagePath = url.getPath();
						addClassBySuper( set,  packagePath,  packageName, superClass);
					}else if(protocol.equals("jar"))
					{
						JarURLConnection jarUrl = (JarURLConnection)url.openConnection();
						  JarFile jarFile = jarUrl.getJarFile();
	                     Enumeration<JarEntry> jarEntries = jarFile.entries();
	                     while(jarEntries.hasMoreElements())
	                     {
	                    	   JarEntry jarEntry = jarEntries.nextElement();
	                            String jarEntryName = jarEntry.getName();
	                            if (jarEntryName.endsWith(".class")) {
	                                String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
	                                Class<?> cls = Class.forName(className);
	                                if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
	                                    doAddClass(set,className);
	                                }
	                            }
	                     }
					}
					}
			}
		} catch (IOException e) {
			if(log.isErrorEnabled())
			{
				log.error("get class set failure ....",e);
				throw new RuntimeException(e);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return set;
    }
	
    private static void addClassBySuper(Set<Class<?>> classList, String packagePath, String packageName, Class<?> superClass) {
        try {
            File[] files = getClassFiles(packagePath);
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isFile()) {
                        String className = getClassName(packageName, fileName);
                        Class<?> cls = Class.forName(className);
                        if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                            classList.add(cls);
                        }
                    } else {
                        String subPackagePath = getSubPackagePath(packagePath, fileName);
                        String subPackageName = getSubPackageName(packageName, fileName);
                        addClassBySuper(classList, subPackagePath, subPackageName, superClass);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static File[] getClassFiles(String packagePath)
    {
		File[] files = new File(packagePath).listFiles(new FileFilter(){
			public boolean accept(File file) {
				return (file.isFile() && file.getName().endsWith(".class") || file.isDirectory());
			}
		});
		return files;
    }
	

    private static String getClassName(String packageName, String fileName) {
        String className = fileName.substring(0, fileName.lastIndexOf("."));
        if (StringUtil.isNotEmpty(packageName)) {
            className = packageName + "." + className;
        }
        return className;
    }

    private static String getSubPackagePath(String packagePath, String filePath) {
        String subPackagePath = filePath;
        if (StringUtil.isNotEmpty(packagePath)) {
            subPackagePath = packagePath + "/" + subPackagePath;
        }
        return subPackagePath;
    }

    private static String getSubPackageName(String packageName, String filePath) {
        String subPackageName = filePath;
        if (StringUtil.isNotEmpty(packageName)) {
            subPackageName = packageName + "." + subPackageName;
        }
        return subPackageName;
    }
 
	
	/**
	 * 根据包路径,
	 * 包名添加类
	 * @param classSet
	 * @param packagePath
	 * @param packageName
	 */
	private static void addClass(Set<Class<?>> classSet,String packagePath,String packageName)
	{
		//根据包路径列出下面所有的文件
		File[] files = new File(packagePath).listFiles(new FileFilter(){
			public boolean accept(File file) {
				return (file.isFile() && file.getName().endsWith(".class") || file.isDirectory());
			}
			
		});
		
		for(File file : files)
		{
			String fileName = file.getName();
			if(file.isFile())
			{
				String className = fileName.substring(0,fileName.lastIndexOf("."));
				if(StringUtil.isNotEmpty(packageName))
				{
					className = packageName+"."+className;
				}
				doAddClass(classSet,className);
			}else
			{
					String subPackagePath = fileName;
					if(StringUtil.isNotEmpty(packagePath))
					{
						subPackagePath=packagePath +"/"+subPackagePath;
					}
					String subPackageName = fileName;
					if(StringUtil.isNotEmpty(packageName))
					{
						subPackageName=packageName+"."+subPackageName;
					}
					addClass(classSet,subPackagePath,subPackageName);
			}
			
		}
	}
	
	/**
	 * 添加类的具体实现
	 * @param classSet
	 * @param className
	 */
	private static void doAddClass(Set<Class<?>> classSet,String className)
	{
		Class<?> cls = loadClass(className, false);
		classSet.add(cls);
	}
 
}

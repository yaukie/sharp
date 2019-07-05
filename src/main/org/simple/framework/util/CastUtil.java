package  org.simple.framework.util;
/**
 * 类型转换工具
 * @author yebn
 *
 */

public class CastUtil {

	/**
	 * 转换int
	 * @param obj
	 * @return
	 */
	public static int castInt(Object obj)
	{
		return castInt(obj);
		
	}
	
	/**
	 * 转换int 指定d默认值
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static int castInt(Object obj,int defaultValue){
		int intValue = defaultValue;
		if(obj !=null )
		{
			String strValue = castString(obj);
			if(StringUtil.isNotEmpty(strValue))
			{
				try 
				{
					intValue = Integer.parseInt(strValue);
				}catch(NumberFormatException e)
				{
					intValue = defaultValue;
				}
				
			}
		}
		
		return intValue;
	}
	
	/**
	 * 转成 string
	 * @param obj
	 * @return
	 */
	public static String castString(Object obj)
	{
		return castString(obj,"");
		
	}
	
	/**
	 * 转成 string 指定默认值
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static String castString(Object obj,String defaultValue)
	{
		return obj !=null ?String.valueOf(obj):defaultValue;
	}
	
	/**
	 * 转换为 boolean
	 * @param obj
	 * @return
	 */
	public static boolean castBoolean (Object obj)
	{
		return castBoolean(obj,true);
	}
	/**
	 * 转为boolean 设置默认值
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static boolean castBoolean(Object obj,boolean defaultValue)
	{
		boolean v = defaultValue;
		
		if(obj != null)
		{
			v = Boolean.parseBoolean(castString(obj));
		}
		
		return v;
		
	}
	
	
	/**
	 *转成 double 类型
	 * @param obj
	 * @return
	 */
	public static double castDouble(Object obj )
	{
		return castDouble(obj,0);
	}
	
	/**
	 * 转成 double 类型,指定默认值
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static double castDouble(Object obj,double defaultValue)
	{
		
		double v = defaultValue;
		
		if(obj !=null )
		{
			String strValue = castString(obj);
			if(StringUtil.isNotEmpty(strValue))
			{
				try
				{
					v = Double.parseDouble(strValue); 
				}catch(NumberFormatException e)
				{
					v = defaultValue;
				}
				
			}
		}
		
		return v;
	}
	
	
	
	
	/**
	 *转成 long 类型
	 * @param obj
	 * @return
	 */
	public static double castLong(Object obj )
	{
		return castLong(obj,0l);
	}
	
	/**
	 * 转成 long 类型,指定默认值
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static double castLong(Object obj,long defaultValue)
	{
		
		long v = defaultValue;
		
		if(obj !=null )
		{
			String strValue = castString(obj);
			if(StringUtil.isNotEmpty(strValue))
			{
				try
				{
					v = Long.parseLong(strValue); 
				}catch(NumberFormatException e)
				{
					v = defaultValue;
				}
				
			}
		}
		
		return v;
	}
	
	public static void main(String[] args) {
		System.out.println("dds");
	}
}

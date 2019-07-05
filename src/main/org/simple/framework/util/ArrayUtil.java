package  org.simple.framework.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 * @author yebn
 *
 */
public class ArrayUtil {

	/**
	 * 判断数组是否为空
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Object[] array)
	{
		return ArrayUtils.isEmpty(array);
	}
	
	/**
	 * 判断数组是否为非空
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Object[]  array)
	{
		return !ArrayUtils.isEmpty(array);
	}
	
}

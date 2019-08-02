package com.ejet.core.kernel.utils;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 对象工具类
 */
public class ObjectUtil{

	/**
	 * 判断元素不为空
	 * @param obj object
	 * @return boolean
	 */
	public static boolean isNotEmpty( Object obj) {
		return !ObjectUtil.isEmpty(obj);
	}

	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof Optional) {
			return !((Optional)obj).isPresent();
		} else if (obj instanceof CharSequence) {
			return ((CharSequence)obj).length() == 0;
		} else if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		} else if (obj instanceof Collection) {
			return ((Collection)obj).isEmpty();
		} else {
			return obj instanceof Map ? ((Map)obj).isEmpty() : false;
		}
	}
}

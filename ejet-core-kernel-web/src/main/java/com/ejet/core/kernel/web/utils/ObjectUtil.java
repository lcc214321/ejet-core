package com.ejet.core.kernel.web.utils;


/**
 * 对象工具类
 */
public class ObjectUtil extends org.springframework.util.ObjectUtils {

	/**
	 * 判断元素不为空
	 * @param obj object
	 * @return boolean
	 */
	public static boolean isNotEmpty(Object obj) {

		return !isEmpty(obj);
	}

//	public static boolean isEmpty(Object obj) {
//		if (obj == null) {
//			return true;
//		} else if (obj instanceof Optional) {
//			return !((Optional)obj).isPresent();
//		} else if (obj instanceof CharSequence) {
//			return ((CharSequence)obj).length() == 0;
//		} else if (obj.getClass().isArray()) {
//			return Array.getLength(obj) == 0;
//		} else if (obj instanceof Collection) {
//			return ((Collection)obj).isEmpty();
//		} else {
//			return obj instanceof Map ? ((Map)obj).isEmpty() : false;
//		}
//	}

}

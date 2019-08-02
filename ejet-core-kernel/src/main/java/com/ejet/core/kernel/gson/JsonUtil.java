//package com.ejet.core.kernel.gson;
//
//import com.ejet.core.kernel.utils.Exceptions;
//import com.ejet.core.kernel.utils.StringPool;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.lang.reflect.Type;
//import java.util.List;
//import java.util.Map;
//
///**
// * Jackson工具类
// *
// * @author Chill
// */
//@Slf4j
//public class JsonUtil {
//
//	/**
//	 * 将对象序列化成json字符串
//	 *
//	 * @param value javaBean
//	 * @param <T>   T 泛型标记
//	 * @return jsonString json字符串
//	 */
//	public static <T> String toJson(T value) {
//		try {
//			return getInstance().toJson(value);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	/**
//	 * 将对象序列化成 gson byte 数组
//	 *
//	 * @param object javaBean
//	 * @return jsonString json字符串
//	 */
//	public static byte[] toJsonAsBytes(Object object) {
//		try {
//			return getInstance().toJson(object).getBytes();
//		} catch (Exception e) {
//			throw Exceptions.unchecked(e);
//		}
//	}
//
//	/**
//	 * 将json反序列化成对象
//	 *
//	 * @param content   content
//	 * @param valueType class
//	 * @param <T>       T 泛型标记
//	 * @return Bean
//	 */
//	public static <T> T parse(String content, Class<T> valueType) {
//		try {
//			return getInstance().fromJson(content, valueType);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	/**
//	 * 将json反序列化成对象
//	 *
//	 * @param content       content
//	 * @param typeReference 泛型类型
//	 * @param <T>           T 泛型标记
//	 * @return Bean
//	 */
//	public static <T> T parse(String content, TypeReference<?> typeReference) {
//		try {
//			return getInstance().fromJson(content, typeReference.getType());
//		} catch (Exception e) {
//			throw Exceptions.unchecked(e);
//		}
//	}
//
//	/**
//	 * 将json byte 数组反序列化成对象
//	 *
//	 * @param bytes     gson bytes
//	 * @param valueType class
//	 * @param <T>       T 泛型标记
//	 * @return Bean
//	 */
//	public static <T> T parse(byte[] bytes, Class<T> valueType) {
//		try {
//			return getInstance().fromJson(new String(bytes), valueType);
//		} catch (Exception e) {
//			throw Exceptions.unchecked(e);
//		}
//	}
//
//
//	/**
//	 * 将json反序列化成对象
//	 *
//	 * @param bytes         bytes
//	 * @param typeReference 泛型类型
//	 * @param <T>           T 泛型标记
//	 * @return Bean
//	 */
//	public static <T> T parse(byte[] bytes, TypeReference<?> typeReference) {
//		return parse(bytes, typeReference);
//	}
//
//	/**
//	 * 将json反序列化成对象
//	 *
//	 * @param in        InputStream
//	 * @param valueType class
//	 * @param <T>       T 泛型标记
//	 * @return Bean
//	 */
//	public static <T> T parse(InputStream in, Class<T> valueType) {
//		try {
//			return getInstance().fromJson(new InputStreamReader(in), valueType);
//		} catch (Exception e) {
//			throw Exceptions.unchecked(e);
//		}
//	}
//
//	/**
//	 * 将json反序列化成对象
//	 *
//	 * @param in            InputStream
//	 * @param typeReference 泛型类型
//	 * @param <T>           T 泛型标记
//	 * @return Bean
//	 */
//	public static <T> T parse(InputStream in, TypeReference<?> typeReference) {
//		try {
//			return getInstance().fromJson(new InputStreamReader(in), typeReference.getType());
//		} catch (Exception e) {
//			throw Exceptions.unchecked(e);
//		}
//	}
//
//	/**
//	 * 将json反序列化成List对象
//	 * @param content content
//	 * @param valueTypeRef class
//	 * @param <T> T 泛型标记
//	 * @return List
//	 */
//	public static <T> List<T> parseArray(String content, Class<T> valueTypeRef) {
//		try {
//			if (!StringUtil.startsWithIgnoreCase(content, StringPool.LEFT_SQ_BRACKET)) {
//				content = StringPool.LEFT_SQ_BRACKET + content + StringPool.RIGHT_SQ_BRACKET;
//			}
//			Type type = new TypeToken<List<T>>(){}.getType();
//			List<T> result = getInstance().fromJson(content,type);
//			return result;
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	public static Map<String, Object> toMap(String content) {
//		try {
//			return getInstance().fromJson(content, Map.class);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	public static <T> Map<String, T> toMap(String content, Class<T> valueTypeRef) {
//		try {
//			Type type = new TypeToken<Map<String,T>>(){}.getType();
//			Map<String, T> result = getInstance().fromJson(content,type);
//			return result;
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	public static <T> T toPojo(Map fromValue, Class<T> toValueType) {
//		try {
//			String content = getInstance().toJson(fromValue);
//			return getInstance().fromJson(content, toValueType);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	/**
//	 * 将json字符串转成 JsonNode
//	 *
//	 * @param jsonString jsonString
//	 * @return jsonString json字符串
//	 */
//	public static JsonNode readTree(String jsonString) {
//		try {
//			return getInstance().fromJson(jsonString, JsonNode.class);
//		} catch (Exception e) {
//			throw Exceptions.unchecked(e);
//		}
//	}
//
//	/**
//	 * 将json字符串转成 JsonNode
//	 *
//	 * @param in InputStream
//	 * @return jsonString json字符串
//	 */
//	public static JsonNode readTree(InputStream in) {
//		try {
//			return getInstance().fromJson(new InputStreamReader(in), JsonNode.class);
//		} catch (Exception e) {
//			throw Exceptions.unchecked(e);
//		}
//	}
//
//	/**
//	 * 将json字符串转成 JsonNode
//	 *
//	 * @param content content
//	 * @return jsonString json字符串
//	 */
//	public static JsonNode readTree(byte[] content) {
//		try {
//			return getInstance().fromJson(new String(content), JsonNode.class);
//		} catch (Exception e) {
//			throw Exceptions.unchecked(e);
//		}
//	}
//
////	/**
////	 * 将json字符串转成 JsonNode
////	 *
////	 * @param jsonParser JsonParser
////	 * @return jsonString json字符串
////	 */
////	public static JsonNode readTree(JsonParser jsonParser) {
////		try {
////			return getInstance().readTree(jsonParser);
////		} catch (IOException e) {
////			throw Exceptions.unchecked(e);
////		}
////	}
//
//	public static Gson getInstance() {
//		return GsonHolder.INSTANCE;
//	}
//
//	private static class GsonHolder {
//		private static Gson INSTANCE = new Gson();
//	}
//
//
//
//}

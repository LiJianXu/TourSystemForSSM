package com.ssm.jsonuntil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * Json 转换工具
 * 
 * @author dyw770
 * @date 2017年10月15日 上午11:15:32
 *
 */
public class JsonUtils {

	/**
	 * json 转换器
	 */
	private static ObjectMapper objectMapper;

	/**
	 * 支持过滤的json转换器
	 */
	private static DyncJsonSerializer dyncJsonSerializer;

	static {
		objectMapper = new ObjectMapper();
		dyncJsonSerializer = new DyncJsonSerializer();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	}

	/**
	 * 根据包含字段来序列化
	 * 
	 * @param obj
	 * @param include
	 * @return
	 */
	public static String obj2jsonWithInclude(Object obj, Map<Class<?>, String> include) {
		try {
			return dyncJsonSerializer.include(obj, include);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据排除字段来序列化
	 * 
	 * @param obj
	 * @param include
	 * @return
	 */
	public static String obj2jsonWithFilter(Object obj, Map<Class<?>, String> include) {
		try {
			return dyncJsonSerializer.filter(obj, include);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 序列化bean但是不序列化任何集合属性
	 * @param obj
	 * @return
	 */
	public static String obj2jsonWithNoSet(Object obj) {
		try {
			return dyncJsonSerializer.noSet(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * javaBean、列表数组转换为json字符串
	 */
	public static String obj2json(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * javaBean、列表数组转换为json字符串,忽略空值
	 */
	public static String obj2jsonIgnoreNull(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json 转JavaBean
	 */
	public static <T> T json2pojo(String jsonString, Class<T> clazz) {
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		try {
			return objectMapper.readValue(jsonString, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json字符串转换为map
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> json2map(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		try {
			return mapper.readValue(jsonString, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json字符串转换为map
	 */
	public static <T> Map<String, T> json2map(String jsonString, Class<T> clazz) {
		Map<String, Map<String, Object>> map = null;
		Map<String, T> result = new HashMap<String, T>(16);
		try {
			map = objectMapper.readValue(jsonString, new TypeReference<Map<String, T>>() {
			});
			for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
				result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 深度转换json成map
	 *
	 * @param json
	 * @return
	 */
	public static Map<String, Object> json2mapDeeply(String json) throws Exception {
		return json2MapRecursion(json, objectMapper);
	}

	/**
	 * 把json解析成list，如果list内部的元素存在jsonString，继续解析
	 *
	 * @param json
	 * @param mapper
	 *            解析工具
	 * @return
	 * @throws Exception
	 */
	private static List<Object> json2ListRecursion(String json, ObjectMapper mapper) {
		if (json == null) {
			return null;
		}
		try {
			@SuppressWarnings("unchecked")
			List<Object> list = mapper.readValue(json, List.class);
			for (Object obj : list) {
				if (obj != null && obj instanceof String) {
					String str = (String) obj;
					if (str.startsWith("[")) {
						obj = json2ListRecursion(str, mapper);
					} else if (obj.toString().startsWith("{")) {
						try {
							obj = json2MapRecursion(str, mapper);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			return list;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把json解析成map，如果map内部的value存在jsonString，继续解析
	 *
	 * @param json
	 * @param mapper
	 * @return
	 * @throws Exception
	 */
	private static Map<String, Object> json2MapRecursion(String json, ObjectMapper mapper) {
		if (json == null) {
			return null;
		}
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = mapper.readValue(json, Map.class);
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				Object obj = entry.getValue();
				if (obj != null && obj instanceof String) {
					String str = ((String) obj);

					if (str.startsWith("[")) {
						List<?> list = json2ListRecursion(str, mapper);
						map.put(entry.getKey(), list);
					} else if (str.startsWith("{")) {
						Map<String, Object> mapRecursion = json2MapRecursion(str, mapper);
						map.put(entry.getKey(), mapRecursion);
					}
				}
			}
			return map;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 与javaBean json数组字符串转换为列表
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) {

		JavaType javaType = getCollectionType(ArrayList.class, clazz);
		List<T> lst = null;
		try {
			lst = (List<T>) objectMapper.readValue(jsonArrayStr, javaType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lst;
	}

	/**
	 * 获取泛型的Collection Type
	 *
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return objectMapper.getTypeFactory().constructParametrizedType(collectionClass, collectionClass,
				elementClasses);
	}

	/**
	 * map转JavaBean
	 */
	public static <T> T map2pojo(Map<?, ?> map, Class<T> clazz) {
		return objectMapper.convertValue(map, clazz);
	}

	/**
	 * map转json
	 *
	 * @param map
	 * @return
	 */
	public static String mapToJson(Map<?, ?> map) {
		try {
			return objectMapper.writeValueAsString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * map转JavaBean
	 */
	public static <T> T obj2pojo(Object obj, Class<T> clazz) {
		return objectMapper.convertValue(obj, clazz);
	}
}
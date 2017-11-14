package com.ssm.jsonuntil;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json 动态过滤
 * 
 * @author dyw770
 * @date 2017/10/15 11:28:16
 *
 */
public class DyncJsonSerializer {

	private static final String DYNC_INCLUDE = "DYNC_INCLUDE";

	private static final String DYNC_FILTER = "DYNC_FILTER";
	
	private static final String DYNC_NO_SET = "DYNC_NO_SET";

	private ObjectMapper mapper;

	private JacksonJsonFilterProvider jacksonFilter;
	
	private JacksonNoSetFilterProvider noSetFilter;

	@JsonFilter(DYNC_FILTER)
	interface DynamicFilter {
	}

	@JsonFilter(DYNC_INCLUDE)
	interface DynamicInclude {
	}
	
	@JsonFilter(DYNC_NO_SET)
	interface DynamicNoSet {
	}

	public DyncJsonSerializer() {
		this.mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
		mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * @param clazz
	 *            需要设置规则的Class
	 * @param include
	 *            转换时包含哪些字段
	 * @param filter
	 *            转换时过滤哪些字段
	 */
	private void filter(Class<?> clazz, String include, String filter) {
		if (clazz == null) {
			return;
		}
		if (!StringUtils.isEmpty(include)) {
			jacksonFilter.include(clazz, include.split(","));
			mapper.addMixIn(clazz, DynamicInclude.class);
		} else if (!StringUtils.isEmpty(filter)) {
			jacksonFilter.filter(clazz, filter.split(","));
			mapper.addMixIn(clazz, DynamicFilter.class);
		} else {
			jacksonFilter.filter(clazz, "".split(","));
		}
	}

	/**
	 * 包含
	 * 
	 * @param clazz
	 * @param include
	 * @throws JsonProcessingException
	 */
	public String include(Object object, Map<Class<?>, String> include) throws JsonProcessingException {
		jacksonFilter = new JacksonJsonFilterProvider();
		mapper.setFilterProvider(jacksonFilter);
		if(include != null) {
			for(Map.Entry<Class<?>, String> entry : include.entrySet()) {
				filter(entry.getKey(), entry.getValue(), null);
			}
		}
		jacksonFilter = null;
		return toJson(object);
	}

	/**
	 * 排除
	 * 
	 * @param clazz
	 * @param filter
	 * @throws JsonProcessingException
	 */
	public String filter(Object object, Map<Class<?>, String> filter) throws JsonProcessingException {
		jacksonFilter = new JacksonJsonFilterProvider();
		mapper.setFilterProvider(jacksonFilter);
		if(filter != null) {
			for(Map.Entry<Class<?>, String> entry : filter.entrySet()) {
				filter(entry.getKey(), null, entry.getValue());
			}
		}
		jacksonFilter = null;
		return toJson(object);
	}

	/**
	 * 不序列化任何set属性
	 * @param object
	 * @return
	 * @throws JsonProcessingException 
	 */
	public String noSet(Object object) throws JsonProcessingException {
		noSetFilter = new JacksonNoSetFilterProvider();
		mapper.setFilterProvider(noSetFilter);
		mapper.addMixIn(object.getClass(), DynamicNoSet.class);
		noSetFilter = null;
		return toJson(object);
	}
	
	/**
	 * 转换成json字符
	 * @param object
	 * @return
	 * @throws JsonProcessingException
	 */
	private String toJson(Object object) throws JsonProcessingException {
		return mapper.writeValueAsString(object);
	}
}

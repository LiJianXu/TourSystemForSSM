package com.ssm.jsonuntil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

/**
 * 实现了 json 的字段过滤
 * @author dyw770
 * @date 2017/10/15 11:28:43
 *
 */
@SuppressWarnings("deprecation")
public class JacksonJsonFilterProvider extends FilterProvider{

	private Map<Class<?>, Set<String>> includeMap = new HashMap<>();

    private Map<Class<?>, Set<String>> filterMap = new HashMap<>();

    public void include(Class<?> type, String[] fields) {
        addToMap(includeMap, type, fields);
        mapRemove(filterMap, type);
    }

    public void filter(Class<?> type, String[] fields) {
        addToMap(filterMap, type, fields);
        mapRemove(includeMap, type);
    }

    private void addToMap(Map<Class<?>, Set<String>> map, Class<?> type, String[] fields) {
        Set<String> fieldSet = map.getOrDefault(type, new HashSet<>());
        fieldSet.removeAll(fieldSet);
        fieldSet.addAll(Arrays.asList(fields));
        map.put(type, fieldSet);
    }

    private void mapRemove(Map<Class<?>, Set<String>> map, Class<?> type) {
        Set<String> fieldSet = map.get(type);
        if (fieldSet != null) {
			map.remove(type);
		}
    }
    
    @Override
    public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {

        return new SimpleBeanPropertyFilter() {

            @Override
            public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider prov, PropertyWriter writer)
                    throws Exception {
                if (apply(pojo.getClass(), writer.getName())) {
                    writer.serializeAsField(pojo, jgen, prov);
                } else if (!jgen.canOmitFields()) {
                    writer.serializeAsOmittedField(pojo, jgen, prov);
                }
            }
        };
    }

    public boolean apply(Class<?> type, String name) {
        Set<String> includeFields = includeMap.get(type);
        Set<String> filterFields = filterMap.get(type);
        if (includeFields != null && includeFields.contains(name)) {
            return true;
        } else if (filterFields != null && !filterFields.contains(name)) {
            return true;
        } else if (includeFields == null && filterFields == null) {
            return true;
        }
        return false;
    }

	@Override
	public BeanPropertyFilter findFilter(Object filterId) {
		return null;
	}

}

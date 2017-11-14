package com.ssm.jsonuntil;

import org.hibernate.collection.internal.PersistentSet;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

/**
 * 实现了 json 的字段过滤 该过滤器不允许任何集合类属性序列化
 * 
 * @author dyw770
 * @date 2017/10/15 11:28:43
 *
 */
@SuppressWarnings("deprecation")
public class JacksonNoSetFilterProvider extends FilterProvider {

	@Override
	public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {

		return new SimpleBeanPropertyFilter() {

			@Override
			public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider prov,
					PropertyWriter writer) throws Exception {
				if (apply(pojo.getClass(), writer.getType())) {
					writer.serializeAsField(pojo, jgen, prov);
				} else if (!jgen.canOmitFields()) {
					writer.serializeAsOmittedField(pojo, jgen, prov);
				}
			}
		};
	}

	public boolean apply(Class<?> type, JavaType javaType) {
		if (javaType.getRawClass().getName().equals(PersistentSet.class.getName())) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public BeanPropertyFilter findFilter(Object filterId) {
		return null;
	}

}

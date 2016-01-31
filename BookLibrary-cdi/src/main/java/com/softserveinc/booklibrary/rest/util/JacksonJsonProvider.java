package com.softserveinc.booklibrary.rest.util;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class JacksonJsonProvider implements ContextResolver<ObjectMapper> {

	private static final ObjectMapper MAPPER = new ObjectMapper();
//
//	static {
//		MAPPER.setSerializationInclusion(Include.NON_EMPTY);
//		MAPPER.disable(MapperFeature.USE_GETTERS_AS_SETTERS);
//		// MAPPER.disable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
//	}
//
//	public JacksonJsonProvider() {
//		System.out.println("Instantiate MyJacksonJsonProvider");
//	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		System.out.println("MyJacksonProvider.getContext() called with type: " + type);
		return MAPPER;
	}

}

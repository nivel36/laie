package ged.rest.controller;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Provider
public class JacksonHibernateProvider implements ContextResolver<ObjectMapper> {

	@Override
	public ObjectMapper getContext(final Class<?> type) {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate5Module());
		return mapper;
	}
}
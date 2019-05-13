package ged.ejb.core.util;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Resources {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Produces
	@PersistenceContext
	private EntityManager em;

	private Properties properties;

	@PostConstruct
	public void init() {
		try {
			this.properties = new Properties();
			final ClassLoader cl = Thread.currentThread().getContextClassLoader();
			this.properties.load(cl.getResourceAsStream("/config.properties"));
		}
		catch (final IOException e) {
			logger.error("Property file not found", e);
		}
	}

	@Produces
	@ConfigurationProperty
	public String produceProperty(final InjectionPoint ip) {
		final ConfigurationProperty annotation = ip.getAnnotated().getAnnotation(ConfigurationProperty.class);
		final String key = annotation.value();
		final String value = this.properties.getProperty(key);
		if (value == null) {
			final boolean valueRequired = annotation.required();
			if (valueRequired) {
				throw new IllegalStateException("Property not found: " + key);
			}
		}
		return value;
	}
}
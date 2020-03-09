package ged.ejb.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
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

	private ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	private InputStream getFileAsStream(final String path) {
		final ClassLoader classLoader = this.getClassLoader();
		return classLoader.getResourceAsStream(path);
	}

	@PostConstruct
	public void init() {
		this.properties = this.loadProperties();
	}

	private Properties loadProperties() {
		final Properties p = new Properties();
		final String fileName = "/config.properties";
		try (final InputStream inputStream = this.getFileAsStream(fileName)) {
			p.load(inputStream);
		} catch (final IOException e) {
			logger.error("File {} not found", fileName);
			throw new UncheckedIOException(e);
		}
		return p;
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
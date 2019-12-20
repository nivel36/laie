package ged.ejb.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.fop.apps.FopFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Resources {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Produces
	@PersistenceContext
	private EntityManager em;

	private Properties properties;

	private InputStream getConfigFile(String path) {
		this.properties = new Properties();
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		return cl.getResourceAsStream(path);
	}

	@PostConstruct
	public void init() throws IOException {
		try {
			this.properties.load(getConfigFile("/config.properties"));
		} catch (IOException e) {
			logger.error("Config properties file not found");
			throw e;
		}
	}

	@Produces
	public FopFactory fopFactory() throws Exception {
		return FopFactory.newInstance(new File("C:/Temp/fop.xconf"));
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
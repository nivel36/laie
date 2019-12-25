package ged.ejb.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.fop.apps.FopFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class Resources {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Produces
	@PersistenceContext
	private EntityManager em;

	private Properties properties;

	private InputStream getFileAsStream(String path) {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return classLoader.getResourceAsStream(path);
	}

	private URL getFileAsUrl(String path) {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return classLoader.getResource(path);
	}

	@PostConstruct
	public void init() {
		this.properties = loadProperties();
	}

	private Properties loadProperties() {
		final Properties p = new Properties();
		final String fileName = "/config.properties";
		try (final InputStream inputStream = getFileAsStream(fileName)) {
			p.load(inputStream);
		} catch (IOException e) {
			logger.error("File {} not found", fileName);
			throw new UncheckedIOException(e);
		}
		return p;
	}

	@Produces
	public FopFactory fopFactory() {
		try {
			final String fileName = "/META-INF/fop.xml";
			final URL urlFile = this.getFileAsUrl(fileName);
			final URI uriFile = urlFile.toURI();
			final File file = new File(uriFile);
			return FopFactory.newInstance(file);
		} catch (URISyntaxException | SAXException | IOException e) {
			throw new IllegalStateException(e);
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
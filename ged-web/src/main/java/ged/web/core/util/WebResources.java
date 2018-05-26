package ged.web.core.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebResources {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private Properties properties;

	@PostConstruct
	public void init() {
		try {
			this.properties = new Properties();
			final ClassLoader cl = Thread.currentThread().getContextClassLoader();
			this.properties.load(cl.getResourceAsStream("/ged/config.properties"));
		}
		catch (final IOException e) {
			logger.error("Property file not found", e);
			throw new UncheckedIOException(e);
		}
	}

	@Produces
	@RequestScoped
	public Application produceApplication() {
		return FacesContext.getCurrentInstance().getApplication();
	}

	@Produces
	@RequestScoped
	public ExternalContext produceExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	@Produces
	@RequestScoped
	public FacesContext produceFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	@Produces
	@RequestScoped
	public Flash produceFlash() {
		return FacesContext.getCurrentInstance().getExternalContext().getFlash();
	}

	@Produces
	@WebConfigurationProperty
	public String produceProperty(final InjectionPoint ip) {
		final WebConfigurationProperty annotation = ip.getAnnotated().getAnnotation(WebConfigurationProperty.class);
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
package ged.web.core.util;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.util.ConfigurationProperty;

public class WebResources {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final Properties properties;

	private WebResources() {
		this.properties = new Properties();
	}

	@PostConstruct
	public void init() {
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		try {
			this.properties.load(cl.getResourceAsStream("/ged/config.properties"));
		} catch (final IOException e) {
			logger.error("No se pueden cargar las propiedades", e);
		}
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
	@ConfigurationProperty
	public String produceProperty(final InjectionPoint ip) {
		final ConfigurationProperty annotation = ip.getAnnotated().getAnnotation(ConfigurationProperty.class);
		final String key = annotation.value();
		final String value = this.properties.getProperty(key);
		if (value == null) {
			final boolean valueRequired = annotation.required();
			if (valueRequired) {
				throw new IllegalStateException("Property " + key + " not found");
			}
		}
		return value;
	}
}

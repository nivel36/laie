package ged.web.core.util;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import ged.ejb.core.util.ConfigurationProperty;

public class WebResources {

	private Properties properties;

	@PostConstruct
	public void init() throws IOException {
		this.properties = new Properties();
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		this.properties.load(cl.getResourceAsStream("/ged/config.properties"));
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
				throw new IllegalStateException("Property {} " + key + " not found");
			}
		}
		return value;
	}
}

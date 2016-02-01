package ged.web.core.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;

import ged.ejb.core.util.ConfigurationProperty;
import ged.web.core.view.ApplicationBean;

public class WebResources {
	
	@Inject
	private ApplicationBean appBean;

	@Produces
	@RequestScoped
	public FacesContext produceFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	@Produces
	@RequestScoped
	public Flash produceFlash() {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
	}
	
	@Produces
	@ConfigurationProperty
	public String produceProperty(InjectionPoint ip) {
		ConfigurationProperty annotation = ip.getAnnotated().getAnnotation(
				ConfigurationProperty.class);
		String key = annotation.value();
		String value = appBean.getProperties().getProperty(key);
		if (value == null) {
			boolean valueRequired = annotation.required();
			if (valueRequired) {
				throw new IllegalStateException("Property " + key
						+ " not found");
			}
		}
		return value;
	}
}

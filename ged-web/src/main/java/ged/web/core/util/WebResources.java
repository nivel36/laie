package ged.web.core.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class WebResources {

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
}
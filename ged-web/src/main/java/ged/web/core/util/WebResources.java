package ged.web.core.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class WebResources {

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

}

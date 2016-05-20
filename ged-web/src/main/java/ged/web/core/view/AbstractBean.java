package ged.web.core.view;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

public class AbstractBean implements Serializable {

	private static final long serialVersionUID = -2545624640193642401L;

	@Inject
	protected ExternalContext externalContext;

	@Inject
	protected FacesContext facesContext;

	@Inject
	protected HttpServletRequest httpServletRequest;

	@Inject
	protected transient Logger logger;

	public void setLogger(final Logger logger) {
		this.logger = logger;
	}

}

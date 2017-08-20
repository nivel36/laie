package ged.web.core.view;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

public class AbstractBean implements Serializable {

	private static final long serialVersionUID = -2545624640193642401L;

	@Inject
	protected transient ExternalContext externalContext;

	@Inject
	protected transient FacesContext facesContext;

	@Inject
	protected transient HttpServletRequest httpServletRequest;

}

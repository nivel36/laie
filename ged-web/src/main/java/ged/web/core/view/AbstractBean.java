package ged.web.core.view;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;

public class AbstractBean implements Serializable {

	private static final long serialVersionUID = -2545624640193642401L;

	@Inject
	protected FacesContext facesContext;

	@Inject
	protected Flash flash;

	@Inject
	protected Logger logger;

	@Inject
	protected SessionBean sessionBean;

	protected void addErrorToField(final UIComponent component, final String message) {
		final String translatedMessage = translate(message);
		final FacesMessage facesMessage = new FacesMessage(translatedMessage);
		facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
		this.facesContext.addMessage(component.getClientId(), facesMessage);
	}

	protected void addMessage(final Severity severity, final String title, final String message) {
		final String translatedTitle = translate(title);
		final String translatedMessage = translate(message);
		final FacesMessage facesMessage = new FacesMessage(severity, translatedTitle, translatedMessage);
		this.facesContext.addMessage(null, facesMessage);
	}

	private ResourceBundle getResourceBundle(final String filename) {
		final Locale locale = this.facesContext.getViewRoot().getLocale();
		final ResourceBundle bundle = ResourceBundle.getBundle(filename, locale);
		return bundle;
	}

	public void setLogger(final Logger logger) {
		this.logger = logger;
	}

	private String translate(final String message) {
		final ResourceBundle bundle = getResourceBundle("ged.i18n");
		final String translatedMessage = bundle.getString(message);
		return translatedMessage;
	}
}

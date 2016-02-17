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

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	protected void addMessage(Severity severity, String title, String message) {
		String translatedTitle = translate(title);
		String translatedMessage = translate(message);
		FacesMessage facesMessage = new FacesMessage(severity, translatedTitle,
				translatedMessage);
		facesContext.addMessage(null, facesMessage);
	}
	
	protected void addErrorToField(UIComponent component, String message) {
		String translatedMessage = translate(message);
		facesContext.addMessage(component.getClientId(), new FacesMessage(translatedMessage));
	}

	private String translate(String message) {
		ResourceBundle bundle = getResourceBundle("ged.i18n");
		String translatedMessage = bundle.getString(message);
		return translatedMessage;
	}

	private ResourceBundle getResourceBundle(String filename) {
		Locale locale = facesContext.getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(filename, locale);
		return bundle;
	}
}

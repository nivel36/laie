package ged.web.core.view;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.Flash;
import javax.inject.Inject;

import ged.web.view.ActionsBean;

public abstract class AbstractPageBean extends AbstractBean {

	private static final long serialVersionUID = -647915087403140904L;

	@Inject
	protected ActionsBean actionsBean;

	@Inject
	protected Flash flash;

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

	protected String translate(final String message) {
		final ResourceBundle bundle = getResourceBundle("ged.i18n");
		final String translatedMessage = bundle.getString(message);
		return translatedMessage;
	}
}

package ged.web.core.view;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.Flash;
import javax.inject.Inject;

import ged.web.core.util.TransaltionUtils;

public abstract class AbstractPageBean extends AbstractBean {

	private static final long serialVersionUID = -647915087403140904L;

	@Inject
	protected transient ApplicationBean applicationBean;

	@Inject
	protected transient Flash flash;

	@Inject
	protected transient SessionBean sessionBean;

	protected void addErrorToField(final UIComponent component, final String message) {
		final String translatedMessage = TransaltionUtils.translate(message);
		final FacesMessage facesMessage = new FacesMessage(translatedMessage);
		facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
		this.facesContext.addMessage(component.getClientId(), facesMessage);
	}

	protected void addMessage(final Severity severity, final String title, final String message) {
		final String translatedTitle = TransaltionUtils.translate(title);
		final String translatedMessage = TransaltionUtils.translate(message);
		final FacesMessage facesMessage = new FacesMessage(severity, translatedTitle, translatedMessage);
		this.facesContext.addMessage(null, facesMessage);
	}
}
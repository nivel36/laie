package ged.web.core.view;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;

import ged.web.core.util.Translate;

public abstract class AbstractBean implements Serializable {

	private static final long serialVersionUID = -647915087403140904L;

	@Inject
	protected transient ApplicationBean applicationBean;

	@Inject
	protected transient ExternalContext externalContext;

	@Inject
	protected transient FacesContext facesContext;

	@Inject
	protected transient Flash flash;

	@Inject
	protected transient SessionBean sessionBean;

	protected void addErrorToField(final UIComponent component, final String message) {
		this.addMessage(component, FacesMessage.SEVERITY_ERROR, message, message, null);
	}

	protected void addInfoMessage(final String message) {
		this.addMessage(null, FacesMessage.SEVERITY_INFO, message, message, null);
	}

	protected void addInfoMessage(final String message, final Object... params) {
		this.addMessage(null, FacesMessage.SEVERITY_INFO, message, message, params);
	}

	protected void addInfoMessage(final String title, final String message) {
		this.addMessage(null, FacesMessage.SEVERITY_INFO, title, message, null);
	}

	protected void addInfoMessage(final String title, final String message, final Object... params) {
		this.addMessage(null, FacesMessage.SEVERITY_INFO, title, message, params);
	}

	protected void addMessage(final Severity severity, final String title, final String message) {
		this.addMessage(null, severity, title, message, null);
	}

	protected void addMessage(final Severity severity, final String title, final String message, final Object... params) {
		this.addMessage(null, severity, title, message, params);
	}

	private void addMessage(final UIComponent component, final Severity severity, final String title, final String message, final Object[] params) {
		final String translatedTitle = Translate.message(title, params);
		final String translatedMessage = Translate.message(message, params);
		final FacesMessage facesMessage = new FacesMessage(severity, translatedTitle, translatedMessage);
		if (component == null) {
			this.facesContext.addMessage(null, facesMessage);
		}
		else {
			this.facesContext.addMessage(component.getClientId(), facesMessage);
		}
	}

	protected void addWarningMessageIfMaxSearchResultsHaveBeenReached(final Collection<?> collection) {
		final int maxResults = 150;
		if (collection.size() == maxResults) {
			this.addInfoMessage("warning.max_results_reached", maxResults);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getFromFlash(final String key) {
		if (this.flash.containsKey(key)) {
			return (T) this.flash.get(key);
		}
		else {
			return null;
		}
	}

	public void setApplicationBean(final ApplicationBean applicationBean) {
		this.applicationBean = applicationBean;
	}

	public void setFlash(final Flash flash) {
		this.flash = flash;
	}

	public void setSessionBean(final SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
}
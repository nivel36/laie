package es.nivel36.laie.web.core.view;

import static org.omnifaces.util.Faces.validationFailed;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;

import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.util.Translator;

public abstract class AbstractView implements Serializable {

	private static final long serialVersionUID = -75092582490831905L;

	@Inject
	protected transient ApplicationView applicationView;

	@Inject
	protected transient ExternalContext externalContext;

	@Inject
	protected transient FacesContext facesContext;

	@Inject
	protected transient Flash flash;

	@Inject
	protected transient SessionUser sessionUser;

	@Inject
	protected transient Translator translator;

	protected void addErrorToField(final String componentId, final String message) {
		final UIComponent component = getUIComponent(componentId);
		this.addMessage(component, FacesMessage.SEVERITY_ERROR, message, message, null);
		validationFailed();
	}

	private UIComponent getUIComponent(final String id) {
		return facesContext.getViewRoot().findComponent(id);
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

	protected void addMessage(final Severity severity, final String title, final String message,
			final Object... params) {
		this.addMessage(null, severity, title, message, params);
	}

	private void addMessage(final UIComponent component, final Severity severity, final String title,
			final String message, final Object[] params) {
		final String translatedTitle = this.translator.message(title, params);
		final String translatedMessage = this.translator.message(message, params);
		final FacesMessage facesMessage = new FacesMessage(severity, translatedTitle, translatedMessage);
		if (component == null) {
			this.facesContext.addMessage(null, facesMessage);
		} else {
			this.facesContext.addMessage(component.getClientId(), facesMessage);
		}
	}

	protected void addWarningMessageIfMaxSearchResultsHaveBeenReached(final Collection<?> collection) {
		final int maxResults = 150;
		if (collection.size() == maxResults) {
			this.addInfoMessage("warning.max_results_reached", maxResults);
		}
	}
	
	protected boolean flashContainsKey(final String key) {
		return this.flash.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getValueFromFlash(final String key) {
		if (this.flash.containsKey(key)) {
			return (T) this.flash.get(key);
		} else {
			return null;
		}
	}

	protected String getValueFromGetParameters(final String key) {
		return this.getValueFromGetParameters(key, false);
	}

	protected String getValueFromGetParameters(final String key, final boolean required) {
		final String value = this.externalContext.getRequestParameterMap().get(key);
		if (required && (value == null || value.isBlank())) {
			throw new IllegalPageStateException();
		}
		return value;
	}

	protected void putValueToFlash(final String key, final Object value) {
		this.flash.put(key, value);
	}

	public void setApplicationView(final ApplicationView applicationView) {
		this.applicationView = applicationView;
	}

	public void setFlash(final Flash flash) {
		this.flash = flash;
	}

	public void setSessionUser(final SessionUser sessionUser) {
		this.sessionUser = sessionUser;
	}

	public void setTranslator(final Translator translator) {
		this.translator = translator;
	}
}
package ged.web.core.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;

import ged.web.core.util.Translator;

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
	protected transient SessionUser sessionUser;

	@Inject
	protected transient Translator translator;

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
		final String translatedTitle = this.translator.message(title, params);
		final String translatedMessage = this.translator.message(message, params);
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

	protected Map<String, List<String>> buildDialogParameter(final String name, final String value) {
		final Map<String, List<String>> params = new HashMap<>();
		final ArrayList<String> param = new ArrayList<>();
		param.add(value);
		params.put(name, param);
		return params;
	}

	private Map<String, Object> buildDialogParameters() {
		final Map<String, Object> options = new HashMap<>();
		options.put("modal", true);
		options.put("resizable", false);
		options.put("responsive", true);
		options.put("dynamic", true);
		options.put("contentWidth", "100%");
		options.put("height", "auto");
		return options;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValueFromFlash(final String key) {
		if (this.flash.containsKey(key)) {
			return (T) this.flash.get(key);
		}
		else {
			return null;
		}
	}

	public String getValueFromGetParameters(final String key) {
		return this.externalContext.getRequestParameterMap().get(key);
	}

	protected void openBigDialog(final String name) {
		this.openBigDialog(name, null);
	}

	protected void openBigDialog(final String name, final Map<String, List<String>> params) {
		final Map<String, Object> options = this.buildDialogParameters();
		options.put("width", "1024");
		PrimeFaces.current().dialog().openDynamic(name, options, params);
	}

	protected void openDialog(final String name) {
		this.openDialog(name, null);
	}

	protected void openDialog(final String name, final Map<String, List<String>> params) {
		final Map<String, Object> options = this.buildDialogParameters();
		options.put("width", "746");
		PrimeFaces.current().dialog().openDynamic(name, options, params);
	}

	public void putValueToFlash(final String key, final Object value) {
		this.flash.put(key, value);
	}

	public void setApplicationBean(final ApplicationBean applicationBean) {
		this.applicationBean = applicationBean;
	}

	public void setFlash(final Flash flash) {
		this.flash = flash;
	}

	public void setSessionBean(final SessionUser sessionBean) {
		this.sessionUser = sessionBean;
	}

	public void setTranslator(final Translator translator) {
		this.translator = translator;
	}
}
package ged.web.core.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class Message {

	public static void addError(final String title, final String message) {
		add(FacesMessage.SEVERITY_ERROR, title, message);
	}

	public static void addInfo(final String title, final String message) {
		add(FacesMessage.SEVERITY_INFO, title, message);
	}

	public static void add(final Severity severity, final String title, final String message) {
		final String translatedTitle = Translate.message(title);
		final FacesMessage facesMessage = new FacesMessage(severity, translatedTitle, message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public static void addWarning(final String title, final String message) {
		add(FacesMessage.SEVERITY_WARN, title, message);
	}

	private Message() {
	}

}

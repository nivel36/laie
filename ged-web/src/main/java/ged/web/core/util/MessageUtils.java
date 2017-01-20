package ged.web.core.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class MessageUtils {

	public static void addErrorMessage(final String title, final String message) {
		addMessage(FacesMessage.SEVERITY_ERROR, title, message);
	}

	public static void addInfoErrorMessage(final String title, final String message) {
		addMessage(FacesMessage.SEVERITY_INFO, title, message);
	}

	public static void addMessage(final Severity severity, final String title, final String message) {
		final String translatedTitle = TransaltionUtils.translate(title);
		final FacesMessage facesMessage = new FacesMessage(severity, translatedTitle, message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public static void addWarningMessage(final String title, final String message) {
		addMessage(FacesMessage.SEVERITY_WARN, title, message);
	}

	private MessageUtils() {
	}

}

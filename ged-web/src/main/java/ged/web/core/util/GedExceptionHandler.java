package ged.web.core.util;

import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class GedExceptionHandler extends ExceptionHandlerWrapper {

	private final FacesContext facesContext;

	private final ExceptionHandler wrapped;

	public GedExceptionHandler(final ExceptionHandler wrapped) {
		this.wrapped = wrapped;
		this.facesContext = FacesContext.getCurrentInstance();
	}

	private void addMessage(final Severity severity, final String title, final String message) {
		final String translatedTitle = translate(title);
		final FacesMessage facesMessage = new FacesMessage(severity, translatedTitle, message);
		this.facesContext.addMessage(null, facesMessage);
	}

	private ResourceBundle getResourceBundle(final String filename) {
		final Locale locale = this.facesContext.getViewRoot().getLocale();
		return ResourceBundle.getBundle(filename, locale);
	}

	private ExceptionQueuedEvent getRootException() {
		ExceptionQueuedEvent lastEvent = null;
		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
		while (iterator.hasNext()) {
			lastEvent = iterator.next();
			iterator.remove();
		}
		return lastEvent;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		final ExceptionQueuedEvent event = getRootException();
		if (event != null) {
			final ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			final Throwable throwable = context.getException();
			addMessage(FacesMessage.SEVERITY_ERROR, "message.title.unexpected_error", throwable.getLocalizedMessage());
		}
		getWrapped().handle();
	}

	private String translate(final String message) {
		final ResourceBundle bundle = getResourceBundle("ged.i18n");
		return bundle.getString(message);
	}
}
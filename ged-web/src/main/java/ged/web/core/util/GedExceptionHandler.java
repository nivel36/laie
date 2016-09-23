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

	private FacesContext facesContext;

	private ExceptionHandler wrapped;

	{
		facesContext = FacesContext.getCurrentInstance();
	}

	public GedExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	private void addMessage(Severity severity, String title, String message) {
		String translatedTitle = translate(title);
		FacesMessage facesMessage = new FacesMessage(severity, translatedTitle, message);
		facesContext.addMessage(null, facesMessage);
	}

	private ResourceBundle getResourceBundle(String filename) {
		Locale locale = facesContext.getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(filename, locale);
		return bundle;
	}

	private ExceptionQueuedEvent getRootException() {
		ExceptionQueuedEvent lastEvent = null;
		Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
		while (iterator.hasNext()) {
			lastEvent = iterator.next();
			iterator.remove();
		}
		return lastEvent;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		ExceptionQueuedEvent event = getRootException();
		if (event != null) {
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			Throwable throwable = context.getException();
			addMessage(FacesMessage.SEVERITY_ERROR, "message.title.unexpected_error", throwable.getLocalizedMessage());
			// NavigationHandler navigationHandler =
			// facesContext.getApplication()
			// .getNavigationHandler();
			// navigationHandler.handleNavigation(facesContext, null,
			// "newCurriculum");
			// facesContext.renderResponse();
		}
		getWrapped().handle();
	}

	private String translate(String message) {
		ResourceBundle bundle = getResourceBundle("afersa.legion.i18n");
		String translatedMessage = bundle.getString(message);
		return translatedMessage;
	}
}
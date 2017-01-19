package ged.web.core;

import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.persistence.OptimisticLockException;

public class ApplicationExceptionHandler extends ExceptionHandlerWrapper {

	private static final Logger logger = Logger.getLogger(ApplicationExceptionHandler.class.getName());

	private final ExceptionHandler wrapped;

	ApplicationExceptionHandler(final ExceptionHandler exception) {
		this.wrapped = exception;
	}

	protected void addMessage(final Severity severity, final String title, final String message) {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final String translatedTitle = translate(title);
		final String translatedMessage = translate(message);
		final FacesMessage facesMessage = new FacesMessage(severity, translatedTitle, translatedMessage);
		facesContext.addMessage(null, facesMessage);
	}

	private ResourceBundle getResourceBundle(final String filename) {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final Locale locale = facesContext.getViewRoot().getLocale();
		return ResourceBundle.getBundle(filename, locale);
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	private void gotoPage(final String page) {
		final FacesContext fc = FacesContext.getCurrentInstance();
		final NavigationHandler nav = fc.getApplication().getNavigationHandler();
		nav.handleNavigation(fc, null, page + "?faces-redirect=true");
		fc.renderResponse();
	}

	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		while (i.hasNext()) {
			final ExceptionQueuedEvent event = i.next();
			final ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			// get the exception from context
			final Throwable t = context.getException();

			try {
				logger.log(Level.FINE, "Handling exception", t);
				handle(t);
			} finally {
				i.remove();
			}
			// parent hanle
			getWrapped().handle();
		}
	}

	private void handle(final Throwable exception) {
		if (exception instanceof EJBException) {
			handle(exception.getCause());
		} else if (exception instanceof ViewExpiredException) {
			gotoPage("login");
		} else if (exception instanceof OptimisticLockException) {
			addMessage(FacesMessage.SEVERITY_ERROR, "warning.optimistick_lock.title",
					"warning.optimistick_lock.message");
		} else {
			// do nothing
		}
	}

	protected String translate(final String message) {
		final ResourceBundle bundle = getResourceBundle("ged.i18n");
		return bundle.getString(message);
	}
}

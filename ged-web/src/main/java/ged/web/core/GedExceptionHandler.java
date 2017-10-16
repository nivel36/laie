package ged.web.core;

import java.lang.invoke.MethodHandles;
import java.util.Iterator;

import javax.ejb.EJBException;
import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.web.core.util.MessageUtils;
import ged.web.core.util.Navigate;

public class GedExceptionHandler extends ExceptionHandlerWrapper {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final ExceptionHandler wrapped;

	public GedExceptionHandler(final ExceptionHandler wrapped) {
		this.wrapped = wrapped;
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
			logger.debug("Handling exception", throwable);
			handle(throwable);
		}
		getWrapped().handle();
	}

	private void handle(final Throwable exception) {
		if (exception instanceof FacesException) {
			final Throwable cause = exception.getCause();
			if (cause != null) {
				handle(exception.getCause());
			} else {
				MessageUtils.addErrorMessage("message.title.unexpected_error", exception.getLocalizedMessage());
			}
		} else if (exception instanceof EJBException) {
			handle(exception.getCause());
		} else if (exception instanceof ViewExpiredException) {
			Navigate.toPage("login");
		} else if (exception instanceof OptimisticLockException) {
			MessageUtils.addErrorMessage("warning.optimistick_lock.message", "warning.optimistick_lock.message");
		} else {
			MessageUtils.addErrorMessage("message.title.unexpected_error", exception.getLocalizedMessage());
		}
	}
}
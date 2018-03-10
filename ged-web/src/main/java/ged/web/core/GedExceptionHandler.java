package ged.web.core;

import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.LOGIN;

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

import ged.web.core.util.Message;

public class GedExceptionHandler extends ExceptionHandlerWrapper {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	public GedExceptionHandler(final ExceptionHandler wrapped) {
		super(wrapped);
	}

	private ExceptionQueuedEvent getRootException() {
		ExceptionQueuedEvent lastEvent = null;
		final Iterator<ExceptionQueuedEvent> iterator = this.getUnhandledExceptionQueuedEvents().iterator();
		while (iterator.hasNext()) {
			lastEvent = iterator.next();
			iterator.remove();
		}
		return lastEvent;
	}

	@Override
	public void handle() {
		final ExceptionQueuedEvent event = this.getRootException();
		if (event != null) {
			final ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			final Throwable throwable = context.getException();
			logger.debug("Handling exception", throwable);
			this.handle(throwable);
		}
		this.getWrapped().handle();
	}

	private void handle(final Throwable exception) {
		if (exception instanceof FacesException) {
			final Throwable cause = exception.getCause();
			if (cause != null) {
				this.handle(exception.getCause());
			}
			else {
				Message.addError("message.title.unexpected_error", exception.getLocalizedMessage());
			}
		}
		else if (exception instanceof EJBException) {
			this.handle(exception.getCause());
		}
		else if (exception instanceof ViewExpiredException) {
			to(LOGIN).doPost();
		}
		else if (exception instanceof OptimisticLockException) {
			Message.addError("warning.optimistick_lock.message", "warning.optimistick_lock.message");
		}
		else {
			Message.addError("message.title.unexpected_error", exception.getLocalizedMessage());
		}
	}
}
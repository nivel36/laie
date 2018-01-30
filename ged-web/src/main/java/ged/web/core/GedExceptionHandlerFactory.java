package ged.web.core;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class GedExceptionHandlerFactory extends ExceptionHandlerFactory {

	private final ExceptionHandlerFactory parent;

	public GedExceptionHandlerFactory(final ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new GedExceptionHandler(this.parent.getExceptionHandler());
	}
}
package ged.web.core;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class GedExceptionHandlerFactory extends ExceptionHandlerFactory {

	public GedExceptionHandlerFactory(final ExceptionHandlerFactory parent) {
		super(parent);
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new GedExceptionHandler(this.getWrapped().getExceptionHandler());
	}
}
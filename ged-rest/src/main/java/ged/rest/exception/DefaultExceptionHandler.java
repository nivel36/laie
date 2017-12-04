package ged.rest.exception;

import javax.ejb.EJBException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ged.rest.ErrorResponseDto;

@Provider
public class DefaultExceptionHandler extends AbstractExceptionMapper implements ExceptionMapper<Exception> {

	// TODO: https://samaxes.com/2014/04/jaxrs-beanvalidation-javaee7-wildfly/

	private Response mapContraintViolationException(final ConstraintViolationException e) {
		Response.ResponseBuilder builder;
		builder = this.createViolationResponse(e.getConstraintViolations());
		return builder.build();
	}

	private Response mapException(final Exception e) {
		Response.ResponseBuilder builder;
		final ErrorResponseDto response = new ErrorResponseDto();
		response.setMessage(e.getMessage());
		builder = Response.status(Response.Status.BAD_REQUEST).entity(response);
		return builder.build();
	}

	private Response mapValidationException(final ValidationException e) {
		Response.ResponseBuilder builder;
		final ErrorResponseDto response = new ErrorResponseDto();
		response.setMessage(e.getMessage());
		builder = Response.status(Response.Status.CONFLICT).entity(response);
		return builder.build();
	}

	@Override
	public Response toResponse(final Exception e) {
		Response response;
		if (e instanceof ConstraintViolationException) {
			response = this.mapContraintViolationException((ConstraintViolationException) e);
		} else if (e instanceof ValidationException) {
			response = this.mapValidationException((ValidationException) e);
		} else if (e instanceof EJBException) {
			final Exception rootException = ((EJBException) e).getCausedByException();
			if (rootException != null) {
				response = this.toResponse(rootException);
			} else {
				response = this.mapException(e);
			}
		} else {
			response = this.mapException(e);
		}
		return response;
	}
}

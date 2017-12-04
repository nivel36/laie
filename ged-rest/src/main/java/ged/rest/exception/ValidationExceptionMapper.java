package ged.rest.exception;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ged.rest.ErrorResponseDto;

@Provider
public class ValidationExceptionMapper extends AbstractExceptionMapper implements ExceptionMapper<ValidationException> {

	@Override
	public Response toResponse(final ValidationException e) {
		Response.ResponseBuilder builder;
		final ErrorResponseDto response = new ErrorResponseDto();
		response.setMessage(e.getMessage());
		builder = Response.status(Response.Status.CONFLICT).entity(response);
		return builder.build();
	}

}

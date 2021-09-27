package es.nivel36.laie.api.v1.exception;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper extends AbstractExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(final ConstraintViolationException e) {
		Response.ResponseBuilder builder;
		builder = this.createViolationResponse(e.getConstraintViolations());
		return builder.build();
	}
}

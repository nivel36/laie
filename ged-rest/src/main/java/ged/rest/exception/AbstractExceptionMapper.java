package ged.rest.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;

public abstract class AbstractExceptionMapper {

	protected Response.ResponseBuilder createViolationResponse(final Set<ConstraintViolation<?>> violations) {
		final Map<String, String> responseObj = new HashMap<>();
		for (final ConstraintViolation<?> violation : violations) {
			responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
		}
		return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	}
}
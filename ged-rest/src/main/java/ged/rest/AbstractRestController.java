package ged.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.core.Response;

public abstract class AbstractRestController {

	@Inject
	private Validator validator;

	protected Response.ResponseBuilder createViolationResponse(final Set<ConstraintViolation<?>> violations) {
		final Map<String, String> responseObj = new HashMap<>();
		for (final ConstraintViolation<?> violation : violations) {
			responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
		}
		return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	}

	public void setValidator(final Validator validator) {
		this.validator = validator;
	}

	protected void validate(final Dto dto) throws ConstraintViolationException, ValidationException {
		final Set<ConstraintViolation<Dto>> violations = this.validator.validate(dto);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<>(violations));
		}
	}
}
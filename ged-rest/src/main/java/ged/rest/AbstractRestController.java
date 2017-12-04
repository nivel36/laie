package ged.rest;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

public abstract class AbstractRestController {

	@Inject
	private Validator validator;

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
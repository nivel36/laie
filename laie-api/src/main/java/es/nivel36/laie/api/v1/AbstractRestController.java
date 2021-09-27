package es.nivel36.laie.api.v1;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

public abstract class AbstractRestController {

	@Inject
	private Validator validator;

	public void setValidator(final Validator validator) {
		this.validator = validator;
	}

	protected void validate(final Dto dto) {
		final Set<ConstraintViolation<Dto>> violations = this.validator.validate(dto);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<>(violations));
		}
	}
}
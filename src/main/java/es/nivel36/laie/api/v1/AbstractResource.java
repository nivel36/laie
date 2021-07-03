package es.nivel36.laie.api.v1;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public abstract class AbstractResource {

	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	// The limit of the maximum number of results that can be searched.
	private static final int MAX_RESULT_SIZE = 100;

	////////////////////////////////////////////////////////////////////////////
	// PROTECTED
	////////////////////////////////////////////////////////////////////////////

	protected void validatePagination(final int page, final int pageSize) {
		if (page < 0) {
			final String message = String.format("Bad page number: %d", page);
			throw new WebApplicationException(message, Response.Status.BAD_REQUEST);
		}
		if (pageSize <= 0) {
			final String message = String.format("Bad page size number: %d", pageSize);
			throw new WebApplicationException(message, Response.Status.BAD_REQUEST);
		}
		if (pageSize > MAX_RESULT_SIZE) {
			final String message = String.format("Page size number %d is out of limits. Maximum allowed value is %d",
					pageSize, MAX_RESULT_SIZE);
			throw new WebApplicationException(message, Response.Status.BAD_REQUEST);
		}
	}
}

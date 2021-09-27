package es.nivel36.laie.ejb.user;

public class BadManagerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadManagerException() {
	}

	public BadManagerException(final String message) {
		super(message);
	}

	public BadManagerException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public BadManagerException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BadManagerException(final Throwable cause) {
		super(cause);
	}

}

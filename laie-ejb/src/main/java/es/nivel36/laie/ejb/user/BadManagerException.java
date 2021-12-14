package es.nivel36.laie.ejb.user;

public class BadManagerException extends Exception {

	private static final long serialVersionUID = 7899553794707186512L;

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

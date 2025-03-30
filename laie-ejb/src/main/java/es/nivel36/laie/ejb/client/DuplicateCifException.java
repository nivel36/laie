package es.nivel36.laie.ejb.client;

public class DuplicateCifException extends Exception {

	private static final long serialVersionUID = 4657385949308821378L;

	public DuplicateCifException() {
	}

	public DuplicateCifException(final String message) {
		super(message);
	}

	public DuplicateCifException(final Throwable cause) {
		super(cause);
	}

	public DuplicateCifException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DuplicateCifException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

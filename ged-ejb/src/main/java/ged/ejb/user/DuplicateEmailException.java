package ged.ejb.user;

public class DuplicateEmailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateEmailException() {
	}

	public DuplicateEmailException(final String message) {
		super(message);
	}

	public DuplicateEmailException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DuplicateEmailException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicateEmailException(final Throwable cause) {
		super(cause);
	}

}

package es.nivel36.laie.ejb.user;

public class DuplicateEmailException extends Exception {

	private static final long serialVersionUID = 4901899794757264099L;

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
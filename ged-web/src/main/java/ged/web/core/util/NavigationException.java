package ged.web.core.util;

public class NavigationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NavigationException() {
	}

	public NavigationException(final String message) {
		super(message);
	}

	public NavigationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public NavigationException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NavigationException(final Throwable cause) {
		super(cause);
	}
}
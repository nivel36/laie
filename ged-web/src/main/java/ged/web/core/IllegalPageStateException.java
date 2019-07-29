package ged.web.core;

public class IllegalPageStateException extends RuntimeException {

	private static final long serialVersionUID = -6986707115344212432L;

	public IllegalPageStateException() {
	}

	public IllegalPageStateException(final String message) {
		super(message);
	}

	public IllegalPageStateException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public IllegalPageStateException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalPageStateException(final Throwable cause) {
		super(cause);
	}
}

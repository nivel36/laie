package ged.web.core;

public class PageNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6986707115344212432L;

	public PageNotFoundException() {
	}

	public PageNotFoundException(final String message) {
		super(message);
	}

	public PageNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public PageNotFoundException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PageNotFoundException(final Throwable cause) {
		super(cause);
	}
}

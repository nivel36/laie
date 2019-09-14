package ged.web.core;

public class GedPermissionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GedPermissionException() {
	}

	public GedPermissionException(final String message) {
		super(message);
	}

	public GedPermissionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public GedPermissionException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GedPermissionException(final Throwable cause) {
		super(cause);
	}

}

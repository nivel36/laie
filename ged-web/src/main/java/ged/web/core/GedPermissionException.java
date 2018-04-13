package ged.web.core;

public class GedPermissionException extends RuntimeException {

	private static final long serialVersionUID = -865352372697083874L;

	public GedPermissionException() {
	}

	public GedPermissionException(final String message) {
		super(message);
	}

	public GedPermissionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public GedPermissionException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GedPermissionException(final Throwable cause) {
		super(cause);
	}

}

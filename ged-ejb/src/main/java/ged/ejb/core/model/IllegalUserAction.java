package ged.ejb.core.model;

public class IllegalUserAction extends Exception {

	private static final long serialVersionUID = -8868241330246138625L;

	public IllegalUserAction() {
	}

	public IllegalUserAction(String message) {
		super(message);
	}

	public IllegalUserAction(Throwable cause) {
		super(cause);
	}

	public IllegalUserAction(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalUserAction(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

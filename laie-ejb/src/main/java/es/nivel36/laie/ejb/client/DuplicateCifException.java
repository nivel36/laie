package es.nivel36.laie.ejb.client;

public class DuplicateCifException extends Exception {

	private static final long serialVersionUID = 4657385949308821378L;

	public DuplicateCifException() {
	}

	public DuplicateCifException(String message) {
		super(message);
	}

	public DuplicateCifException(Throwable cause) {
		super(cause);
	}

	public DuplicateCifException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateCifException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

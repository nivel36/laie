package es.nivel36.laie.ejb.core.file;

public class FileUploadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileUploadException() {
	}

	public FileUploadException(final String message) {
		super(message);
	}

	public FileUploadException(final Throwable cause) {
		super(cause);
	}

	public FileUploadException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FileUploadException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

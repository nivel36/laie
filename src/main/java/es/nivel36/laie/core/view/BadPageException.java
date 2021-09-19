/**
 * 
 */
package es.nivel36.laie.core.view;

/**
 * @author aferrer
 *
 */
public class BadPageException extends RuntimeException {

	public BadPageException() {
	}

	/**
	 * @param message
	 */
	public BadPageException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public BadPageException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BadPageException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public BadPageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

package ged.ejb.user;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = -6308219096019877240L;

	public UserException() {
	}

	public UserException(final String arg0) {
		super(arg0);
	}

	public UserException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public UserException(final String arg0, final Throwable arg1, final boolean arg2, final boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public UserException(final Throwable arg0) {
		super(arg0);
	}

}

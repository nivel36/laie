package ged.ejb.core.bookmark;

public class BookmarkFullExpcetion extends Exception {

	private static final long serialVersionUID = -7189651788345900176L;

	public BookmarkFullExpcetion() {
	}

	public BookmarkFullExpcetion(final String arg0) {
		super(arg0);
	}

	public BookmarkFullExpcetion(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public BookmarkFullExpcetion(final String arg0, final Throwable arg1, final boolean arg2, final boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public BookmarkFullExpcetion(final Throwable arg0) {
		super(arg0);
	}

}

package ged.ejb.core.model;

public class Page {

	public static final Page ALL = Page.of(0, 150);

	public static Page of(final int offset, final int limit) {
		return new Page(offset, limit);
	}

	private final int limit;

	private final int offset;

	public Page(final int offset, final int limit) {
		if (offset < 0) {
			throw new IllegalArgumentException("offset: " + offset);
		}
		if (limit < 0) {
			throw new IllegalArgumentException("limit: " + limit);
		}
		if (offset >= limit) {
			throw new IllegalArgumentException("offset greater than limit");
		}
		this.offset = offset;
		this.limit = limit;
	}

	public int getLimit() {
		return this.limit;
	}

	public int getOffset() {
		return this.offset;
	}

}
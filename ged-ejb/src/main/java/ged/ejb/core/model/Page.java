package ged.ejb.core.model;

public class Page {

	public static final Page ALL = new Page (0, 150);

	private final int offset;

	private final int limit;

	public Page(final int offset,final int limit) {
		if (offset < 1) {
			throw new IllegalArgumentException("offset: " + offset);
		}
		if (limit < 1) {
			throw new IllegalArgumentException("limit: " + limit);
		}
		this.offset = offset;
		this.limit = limit;
	}

	public int getLimit() {
		return limit;
	}

	public int getOffset() {
		return offset;
	}
}
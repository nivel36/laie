package ged.ejb.core.model;

public class Page {

	public static final Page ALL = Page.of(1, 150);

	public static Page of(final int pageNumber, final int pageSize) {
		return new Page(pageNumber, pageSize);
	}

	private final int pageNumber;

	private final int pageSize;

	public Page(final int pageNumber, final int pageSize) {
		if (pageNumber < 1) {
			throw new IllegalArgumentException("pageNum: " + pageNumber);
		}
		if (pageSize < 1) {
			throw new IllegalArgumentException("pageSize: " + pageSize);
		}
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public int getLimit() {
		return pageNumber * pageSize;
	}

	public int getOffSet() {
		return (pageNumber - 1) * pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}
}
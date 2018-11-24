package ged.ejb.core.model;

public class Page {

	public final static Page ALL = Page.of(1, 150);

	// max page size
	private final static int PAGE_LIMIT = 150;

	public static Page of(final int pageNumber, final int pageSize) {
		return new Page(pageNumber, pageSize);
	}

	private final int pageNumber;

	private final int pageSize;

	public Page(final int pageNumber, final int pageSize) {
		if (pageNumber < 0) {
			throw new IllegalArgumentException("pageNum: " + pageNumber);
		}
		if (pageSize < 0) {
			throw new IllegalArgumentException("pageSize: " + pageSize);
		}
		this.pageNumber = pageNumber;
		if (pageSize == 0) {
			this.pageSize = PAGE_LIMIT;
		}
		else {
			this.pageSize = pageSize;
		}
	}

	public int getLimit() {
		return (pageNumber - 1) * pageSize;
	}

	public int getOffSet() {
		return pageNumber * pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}
}
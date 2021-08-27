package es.nivel36.laie.core.service.search;

public class SortField {

	public enum SortType {
		ASCENDING, DESCENDING
	}

	private final String field;

	private SortType sortType;

	public SortField(final String field) {
		this.field = field;
	}

	public SortField(final String field, SortType sortType) {
		this.field = field;
		this.sortType = sortType;
	}

	public String getField() {
		return field;
	}

	public SortType getSortType() {
		return sortType;
	}
}

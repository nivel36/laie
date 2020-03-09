package ged.ejb.core.model.search;

public class SortField {

	private boolean ascending;

	private final String field;

	public SortField(final String field) {
		super();
		this.field = field;
	}

	public SortField(final String field, final boolean ascending) {
		super();
		this.field = field;
		this.ascending = ascending;
	}

	public String getField() {
		return field;
	}

	public boolean isAscending() {
		return ascending;
	}
}

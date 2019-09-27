package ged.ejb.core.model.search;

public class SortField {

	private boolean descending;

	private final String field;

	public SortField(final String field) {
		super();
		this.field = field;
	}

	public SortField(final String field, final boolean descending) {
		super();
		this.field = field;
		this.descending = descending;
	}

	public String getField() {
		return field;
	}

	public boolean isDescending() {
		return descending;
	}
}

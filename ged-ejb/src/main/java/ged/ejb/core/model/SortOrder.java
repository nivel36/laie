package ged.ejb.core.model;

public class SortOrder {

	private boolean descending;

	private final String field;

	public SortOrder(final String field) {
		super();
		this.field = field;
	}

	public SortOrder(final String field, final boolean descending) {
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

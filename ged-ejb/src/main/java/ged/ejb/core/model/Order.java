package ged.ejb.core.model;

public class Order {

	private final boolean descending;

	private final String field;

	public Order(final String field) {
		this(field, false);
	}

	public Order(final String field, final boolean descending) {
		this.field = field;
		this.descending = false;
	}

	public String getField() {
		return field;
	}

	public boolean isDescending() {
		return descending;
	}
}
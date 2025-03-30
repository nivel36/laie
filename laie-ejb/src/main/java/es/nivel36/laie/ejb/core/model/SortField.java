package es.nivel36.laie.ejb.core.model;

public final class SortField {

	private final String field;

	private final boolean asc;

	public SortField(final String field, final boolean isAsc) {
		this.field = field;
		this.asc = isAsc;
	}

	public String getField() {
		return field;
	}

	public boolean isAsc() {
		return asc;
	}
}

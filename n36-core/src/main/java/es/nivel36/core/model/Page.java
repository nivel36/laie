package es.nivel36.core.model;

import java.util.Objects;

public class Page {

	public static final Page ALL_RESULTS = Page.of(0, 150);
	public static final Page TEN_RESULTS_PER_PAGE = Page.of(0, 10);

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
		this.offset = offset;
		this.limit = limit;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Page other = (Page) obj;
		return Objects.equals(other.limit, this.limit) && Objects.equals(other.offset, this.offset);
	}

	public int getLimit() {
		return this.limit;
	}

	public int getOffset() {
		return this.offset;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.offset, this.limit);
	}

	@Override
	public String toString() {
		return String.format("Offset %d - Limit %d", this.offset, this.limit);
	}
}
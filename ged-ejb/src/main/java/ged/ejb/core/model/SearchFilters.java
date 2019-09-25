package ged.ejb.core.model;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;

public class SearchFilters implements Iterable<SearchFilter> {

	private final Queue<SearchFilter> filters;

	public SearchFilters() {
		this.filters = new ArrayDeque<SearchFilter>();
	}

	public void addFilter(final SearchFilter searchFilter) {
		Objects.requireNonNull(searchFilter);
		this.filters.add(searchFilter);
	}

	@Override
	public Iterator<SearchFilter> iterator() {
		return this.filters.iterator();
	}
}

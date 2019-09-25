package ged.ejb.core.model;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public class SearchFilters {

	private Queue<SearchFilter> filters;

	public SearchFilters() {
		filters = new ArrayDeque<SearchFilter>();
	}

	public void addSearchFilter(SearchFilter searchFilter) {
		Objects.requireNonNull(searchFilter);
		this.filters.add(searchFilter);
	}

	public Queue<SearchFilter> getFilters() {
		return filters;
	}
}

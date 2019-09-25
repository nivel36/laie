package ged.ejb.core.model;

import java.util.Objects;

public class SearchFilter {

	public enum SearchCondition {
		AND, OR
	};

	private final SearchCondition searchCondition;

	private final String name;

	private final String value;

	public SearchFilter(String name, String value) {
		this(SearchCondition.AND, name, value);
	}

	public SearchFilter(SearchCondition searchCondition, String name, String value) {
		Objects.requireNonNull(searchCondition);
		Objects.requireNonNull(name);
		Objects.requireNonNull(value);
		this.searchCondition = searchCondition;
		this.name = name;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public SearchCondition getSearchCondition() {
		return searchCondition;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, searchCondition, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchFilter other = (SearchFilter) obj;
		return Objects.equals(name, other.name) && searchCondition == other.searchCondition
				&& Objects.equals(value, other.value);
	}

}

package ged.ejb.core.model.search;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;

public class SearchFacets implements Iterable<SearchFacet> {

	private final Queue<SearchFacet> facets;

	public SearchFacets() {
		this.facets = new ArrayDeque<SearchFacet>();
	}

	public void addFacet(final SearchFacet searchFacet) {
		Objects.requireNonNull(searchFacet);
		this.facets.add(searchFacet);
	}

	@Override
	public Iterator<SearchFacet> iterator() {
		return this.facets.iterator();
	}
	
	public void clear() {
		facets.clear();
	}
}

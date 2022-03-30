package es.nivel36.core.model.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.search.query.facet.Facet;

public class SearchResult<T> {
	
	private final int count;
	
	private final List<T> resultData;
	
	private final Map<String,List<Facet>> facets;
	
	public SearchResult(List<T> resultData, int count,  Map<String,List<Facet>> facets) {
		this.resultData = resultData;
		this.count = count;
		this.facets = facets;
	}
	
	public SearchResult(List<T> resultData, int count) {
		this.resultData = resultData;
		this.count = count;
		this.facets = new HashMap<>();
	}
	
	public int getCount() {
		return count;
	}

	public List<T> getResultData() {
		return resultData;
	}
	
	public Map<String,List<Facet>> getAllFacets() {
		return facets;
	}
	
	public List<Facet> getFacets(String facetName) {
		return facets.get(facetName);
	}
}

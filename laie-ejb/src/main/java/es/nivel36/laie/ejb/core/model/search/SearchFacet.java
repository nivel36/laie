package es.nivel36.laie.ejb.core.model.search;

import java.util.Objects;

public class SearchFacet {
	
	private final String field;
	
	private final String name;
	
	private String[] selectedFactes;

	public SearchFacet(final String name, final String field) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(field);
		this.name = name;
		this.field = field;
	}
	
	public boolean hasSelectedFacets() {
		return selectedFactes != null && selectedFactes.length > 0;
	}

	public String getField() {
		return field;
	}

	public String getName() {
		return name;
	}

	public String[] getSelectedFactes() {
		return selectedFactes;
	}

	public void selectFacets(String[] facets) {
		this.selectedFactes = facets;
	}
	
	public boolean hasFacet(String facet) {
		Objects.requireNonNull(facet);
		for(String selectedFacet: selectedFactes ) {
			if(selectedFacet.equals(facet)) {
				return true;
			}
		}
		return false;
	}
}

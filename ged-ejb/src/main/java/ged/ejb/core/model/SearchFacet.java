package ged.ejb.core.model;

public class SearchFacet {
	
	private final String field;
	
	private final String name;
	
	private int[] selectedFactes;

	public SearchFacet(final String name, final String field) {
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

	public int[] getSelectedFactes() {
		return selectedFactes;
	}

	public void selectFacets(int[] facets) {
		this.selectedFactes = facets;
	}

}

package es.nivel36.laie.ejb.core.tag;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;

import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.core.util.Parameters;


public class TagDao extends AbstractDao {
	
	@Inject
	private SearchFacade searchFacade;

	public Tag findByLabel(final String label) {
		Objects.requireNonNull(label);
		final String namedQuery = "Tag.findByLabel";
		final Parameters parameters = map("label", label);
		try {
			return this.findByQuery(Tag.class, namedQuery, parameters);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public SearchResult<Tag> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		final String[] searchFields = new String[] { "_label" };
		return searchFacade.search(Tag.class, page, sortOrder, searchFacets, searchText, searchFields);
	}
}
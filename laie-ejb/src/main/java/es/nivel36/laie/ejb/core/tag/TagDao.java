package es.nivel36.laie.ejb.core.tag;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.engine.search.sort.SearchSort;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.util.Parameters;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;


public class TagDao extends AbstractDao {
	
	
	private @Inject SearchFacade searchFacade;

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
	
	public SearchResult<Tag> search(final String searchText, final Page page, final SearchSort sortOrder,
			final String[] searchFacets) {
		final String[] searchFields = new String[] { "_label" };
		return searchFacade.search(Tag.class, page, sortOrder, searchFacets, searchText, searchFields);
	}
}
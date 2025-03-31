package es.nivel36.laie.ejb.core.tag;

import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.model.SortField;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class TagDao extends AbstractDao {

	private @Inject SearchFacade searchFacade;

	public Tag findByLabel(final String label) {
		Objects.requireNonNull(label);
		try {
			final String jpql = """
					SELECT t
					FROM Tag t
					WHERE t.label = :label
						""";
			final TypedQuery<Tag> query = this.em.createQuery(jpql, Tag.class);
			query.setParameter("label", label);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	public SearchResult<Tag> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		Objects.requireNonNull(page);
		final String[] searchFields = new String[] { "_label" };
		return searchFacade.search(Tag.class, page, sortField, searchFacets, searchText, searchFields);
	}

	public void setSearchFacade(final SearchFacade searchFacade) {
		this.searchFacade = Objects.requireNonNull(searchFacade);
	}
}
package es.nivel36.laie.ejb.core.model;

import java.util.List;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.engine.search.query.dsl.SearchQuerySelectStep;
import org.hibernate.search.engine.search.sort.SearchSort;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.common.EntityReference;
import org.hibernate.search.mapper.orm.search.loading.dsl.SearchLoadingOptionsStep;
import org.hibernate.search.mapper.orm.session.SearchSession;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class SearchFacade {

	protected @Inject EntityManager em;

	public <T extends Identifiable> List<T> search(final Class<T> type, final Page page, final SearchSort sortField,
			final String searchText, final String... fields) {
		final SearchSession searchSession = Search.session(this.em);
		final SearchQuerySelectStep<?, EntityReference, T, SearchLoadingOptionsStep, ?, ?> search = searchSession
				.search(type);
		SearchResult<T> searchResult = search.where(f -> f.match().field(fields[0]).matching(searchText))
				.sort(sortField).fetch(page.getOffset(), page.getLimit());
		return searchResult.hits();
	}

	public <T extends Identifiable> SearchResult<T> search(Class<T> type, Page page, SearchSort sortField,
			String[] searchFacets, String searchText, String... fields) {
		final SearchSession searchSession = Search.session(this.em);
		final SearchQuerySelectStep<?, EntityReference, T, SearchLoadingOptionsStep, ?, ?> search = searchSession
				.search(type);
		SearchResult<T> searchResult = search.where(f -> f.match().field(fields[0]).matching(searchText)).sort(f->f.field(searchText).asc())
				.fetch(page.getOffset(), page.getLimit());
		return searchResult;
	}
}

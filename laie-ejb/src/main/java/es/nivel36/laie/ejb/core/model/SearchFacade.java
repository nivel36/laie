package es.nivel36.laie.ejb.core.model;

import java.util.List;

import org.hibernate.search.engine.search.predicate.SearchPredicate;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.engine.search.sort.SearchSort;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class SearchFacade {

	protected @Inject EntityManager em;

	public <T extends Identifiable> List<T> search(final Class<T> type, final Page page, final SortField sortField,
			final String searchText, final String... fields) {

		final SearchSession searchSession = Search.session(this.em);
		final SearchScope<T> scope = searchSession.scope(type);

		final SearchPredicate predicate;
		if (searchText != null) {
			predicate = scope.predicate().match().fields(fields).matching(searchText).toPredicate();
		} else {
			predicate = scope.predicate().matchAll().toPredicate();
		}

		final SearchResult<T> result;
		if (sortField != null) {
			final SearchSort sort;
			if (sortField.isAsc()) {
				sort = scope.sort().field(sortField.getField()).asc().toSort();
			} else {
				sort = scope.sort().field(sortField.getField()).desc().toSort();
			}
			result = searchSession.search(scope).where(predicate).sort(sort).fetch(page.getOffset(), page.getLimit());
		} else {
			result = searchSession.search(scope).where(predicate).fetch(page.getOffset(), page.getLimit());
		}
		return result.hits();
	}

	public <T extends Identifiable> SearchResult<T> search(final Class<T> type, final Page page,
			final SortField sortField, final String[] searchFacets, final String searchText, final String... fields) {
		final SearchSession searchSession = Search.session(this.em);
		final SearchScope<T> scope = searchSession.scope(type);

		final SearchPredicate predicate;
		if (searchText != null) {
			predicate = scope.predicate().match().fields(fields).matching(searchText).toPredicate();
		} else {
			predicate = scope.predicate().matchAll().toPredicate();
		}

		final SearchResult<T> result;
		if (sortField != null) {
			final SearchSort sort;
			if (sortField.isAsc()) {
				sort = scope.sort().field(sortField.getField()).asc().toSort();
			} else {
				sort = scope.sort().field(sortField.getField()).desc().toSort();
			}
			result = searchSession.search(scope).where(predicate).sort(sort).fetch(page.getOffset(), page.getLimit());
		} else {
			result = searchSession.search(scope).where(predicate).fetch(page.getOffset(), page.getLimit());
		}
		return result;
	}
}

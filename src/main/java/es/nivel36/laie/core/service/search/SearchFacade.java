package es.nivel36.laie.core.service.search;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Sort;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortFieldContext;

import es.nivel36.laie.core.service.Repository;
import es.nivel36.laie.core.service.search.SortField.SortType;

@Repository
public class SearchFacade {

	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("rawtypes")
	private org.apache.lucene.search.Query createLuceneQuery(final QueryBuilder qb,
			final BooleanJunction<BooleanJunction> bj) {
		org.apache.lucene.search.Query luceneQuery;
		if (bj.isEmpty()) {
			luceneQuery = qb.all().createQuery();
		} else {
			luceneQuery = bj.createQuery();
		}
		return luceneQuery;
	}

	@SuppressWarnings("rawtypes")
	private BooleanJunction<BooleanJunction> createPredicate(final QueryBuilder qb, final String searchText,
			final String... fields) {
		final BooleanJunction<BooleanJunction> bj = qb.bool();

		if (searchText != null) {
			final List<String> searchValues = Arrays.asList(searchText.split("\\s"));
			for (final String searchValue : searchValues) {
				if (searchValue == null) {
					continue;
				}
				final BooleanJunction<BooleanJunction> fieldBj = qb.bool();
				fieldBj.should(qb.keyword().onFields(fields).matching(searchValue).createQuery());
				bj.must(fieldBj.createQuery());
			}
		}
		return bj;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends Indexable> List<T> search(final Class<T> type, final int firstResult, final int maxResults,
			final SortField sortField, final String searchText, final String... fields) {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.entityManager);
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(type).get();

		final BooleanJunction<BooleanJunction> bj = this.createPredicate(qb, searchText, fields);
		final FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(this.createLuceneQuery(qb, bj),
				type);
		fullTextQuery.setFirstResult(firstResult);
		fullTextQuery.setMaxResults(maxResults);
		this.sortQuery(sortField, qb, fullTextQuery);
		return fullTextQuery.getResultList();
	}

	private void sortQuery(final SortField sortField, final QueryBuilder qb, final FullTextQuery fullTextQuery) {
		if (sortField == null) {
			return;
		}
		final SortFieldContext sfc = qb.sort().byField(sortField.getField());
		if (sortField.getSortType().equals(SortType.ASCENDING)) {
			sfc.asc();
		} else {
			sfc.desc();
		}
		final Sort sort = sfc.createSort();
		fullTextQuery.setSort(sort);
	}
}

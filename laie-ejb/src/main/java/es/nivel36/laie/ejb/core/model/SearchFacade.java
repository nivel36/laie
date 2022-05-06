package es.nivel36.laie.ejb.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortFieldContext;
import org.hibernate.search.query.engine.spi.FacetManager;
import org.hibernate.search.query.facet.Facet;
import org.hibernate.search.query.facet.FacetCombine;
import org.hibernate.search.query.facet.FacetSelection;
import org.hibernate.search.query.facet.FacetingRequest;

import es.nivel36.laie.ejb.core.model.search.SearchFacet;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;

public class SearchFacade {

	@Inject
	protected EntityManager em;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends Identifiable> SearchResult<T> search(final Class<T> type, final Page page,
			final SortField sortField, final SearchFacets searchFacets, final String searchText,
			final String... fields) {
		final FullTextEntityManager fullTextEM = Search.getFullTextEntityManager(this.em);
		final QueryBuilder qb = fullTextEM.getSearchFactory().buildQueryBuilder().forEntity(type).get();
		final BooleanJunction<BooleanJunction> bj = this.createPredicate(qb, searchText, fields);
		final FullTextQuery fullTextQuery = fullTextEM.createFullTextQuery(this.createLuceneQuery(qb, bj), type);
		this.paginate(page, fullTextQuery);
		this.sortQuery(sortField, qb, fullTextQuery);
		this.enableFaceting(searchFacets, qb, fullTextQuery);
		final Map<String, List<Facet>> allFacets = this.selectFacets(searchFacets, fullTextQuery);
		if ((searchFacets != null) && !searchFacets.isEmpty()) {
			boolean hasFacet = false;
			for (final Entry<String, List<Facet>> entry : allFacets.entrySet()) {
				if (hasFacet) {
					break;
				}
				for (final Facet facet : entry.getValue()) {
					if (searchFacets.containsFacet(facet.getFieldName(), entry.getKey(), facet.getValue())) {
						hasFacet = true;
						break;
					}
				}
			}
			if (!hasFacet) {
				return this.buildSearchResult(new ArrayList<>(), 0, allFacets);
			}
		}
		return this.buildSearchResult(fullTextQuery.getResultList(), fullTextQuery.getResultSize(), allFacets);
	}

	@SuppressWarnings("rawtypes")
	private Query createLuceneQuery(final QueryBuilder qb, final BooleanJunction<BooleanJunction> bj) {
		Query luceneQuery;
		if (bj.isEmpty()) {
			luceneQuery = qb.all().createQuery();
		} else {
			luceneQuery = bj.createQuery();
		}
		return luceneQuery;
	}

	private void enableFaceting(final SearchFacets searchFacets, final QueryBuilder qb,
			final FullTextQuery fullTextQuery) {
		if (searchFacets == null) {
			return;
		}

		final FacetManager facetManager = fullTextQuery.getFacetManager();
		for (final SearchFacet searchFacet : searchFacets) {
			final String facetName = searchFacet.getName();
			final String facetField = searchFacet.getField();
			final FacetingRequest facetingRequest = qb.facet().name(facetName).onField(facetField).discrete()
					.createFacetingRequest();
			facetManager.enableFaceting(facetingRequest);
		}
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

	private <T extends Identifiable> SearchResult<T> buildSearchResult(final List<T> results, final int resultsSize,
			final Map<String, List<Facet>> allFacets) {
		if (results instanceof ArrayList) {
			return new SearchResult<>(results, resultsSize, allFacets);
		} else {
			return new SearchResult<>(new ArrayList<>(results), resultsSize, allFacets);
		}
	}

	private Map<String, List<Facet>> selectFacets(final SearchFacets searchFacets, final FullTextQuery fullTextQuery) {
		if (searchFacets == null) {
			return new HashMap<>();
		}
		final Map<String, List<Facet>> allFacets = new HashMap<>();

		final FacetManager facetManager = fullTextQuery.getFacetManager();
		for (final SearchFacet searchFacet : searchFacets) {
			final String facetName = searchFacet.getName();
			allFacets.put(facetName, fullTextQuery.getFacetManager().getFacets(facetName));
			if (searchFacet.hasSelectedFacets()) {
				selectFacet(facetManager, searchFacet, facetName);
			}
		}
		return allFacets;
	}

	private void selectFacet(final FacetManager facetManager, final SearchFacet searchFacet, final String facetName) {
		final FacetSelection facetSelection = facetManager.getFacetGroup(facetName);
		final List<Facet> facets = facetManager.getFacets(facetName);
		final int facetsLength = searchFacet.getSelectedFactes().length;
		final List<Facet> facetList = new ArrayList<>();
		for (int i = 0; i < facetsLength; i++) {
			for (final String selectedFacet : searchFacet.getSelectedFactes()) {
				for (final Facet facet : facets) {
					if (facet.getValue().equals(selectedFacet)) {
						facetList.add(facet);
					}
				}
			}
		}
		final Facet[] selectedMatchedFacets = facetList.toArray(new Facet[0]);
		facetSelection.selectFacets(FacetCombine.OR, selectedMatchedFacets);
	}

	private void sortQuery(final SortField sortField, final QueryBuilder qb, final FullTextQuery fullTextQuery) {
		if (sortField == null) {
			return;
		}
		final SortFieldContext sfc = qb.sort().byField(sortField.getField());
		if (sortField.isAscending()) {
			sfc.asc();
		} else {
			sfc.desc();
		}
		final Sort sort = sfc.createSort();
		fullTextQuery.setSort(sort);
	}

	private void paginate(final Page page, final FullTextQuery query) {
		query.setFirstResult(page.getOffset());
		query.setMaxResults(page.getLimit());
	}
}

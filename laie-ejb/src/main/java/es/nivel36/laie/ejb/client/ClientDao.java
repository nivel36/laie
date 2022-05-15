package es.nivel36.laie.ejb.client;

import java.util.Objects;

import javax.inject.Inject;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;

import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;


public class ClientDao extends AbstractDao {
	
	@Inject
	private SearchFacade searchFacade;

	public boolean checkDuplicatedCif(final String cif) {
		Objects.requireNonNull(cif);
		return this.checkDuplicateField(Client.class, "cif", cif);
	}

	public SearchResult<Client> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		Objects.requireNonNull(page);
		final String[] fields = new String[] { "_name", "_cif" };
		return searchFacade.search(Client.class, page, sortOrder, searchFacets, searchText, fields);
	}
}

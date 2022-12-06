package es.nivel36.laie.ejb.client;

import java.util.Objects;

import org.apache.lucene.search.SortField;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.engine.search.sort.SearchSort;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SearchFacade;
import jakarta.inject.Inject;


public class ClientDao extends AbstractDao {
	
	@Inject
	private SearchFacade searchFacade;

	public boolean checkDuplicatedCif(final String cif) {
		Objects.requireNonNull(cif);
		return this.checkDuplicateField(Client.class, "cif", cif);
	}

	public SearchResult<Client> search(final String searchText, final Page page,  final SearchSort sortOrder,
			final String[] searchFacets) {
		Objects.requireNonNull(page);
		final String[] fields = new String[] { "_name", "_cif" };
		return searchFacade.search(Client.class, page, sortOrder, searchFacets, searchText, fields);
	}
}

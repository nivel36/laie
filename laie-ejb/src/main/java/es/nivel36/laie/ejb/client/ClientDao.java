package es.nivel36.laie.ejb.client;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.core.util.Parameters;

@Repository
public class ClientDao extends AbstractDao {
	
	public Client findAllData(final Long clientId) {
		Objects.requireNonNull(clientId);
		final String namedQuery = "Client.findAllData";
		final Parameters parameters = map("clientId", clientId);
		return this.findByQuery(Client.class, namedQuery, parameters);
	}

	public boolean checkDuplicatedCif(final String cif) {
		Objects.requireNonNull(cif);
		return this.checkDuplicateField(Client.class, "cif", cif);
	}

	public SearchResult<Client> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		Objects.requireNonNull(page);
		final String[] fields = new String[] { "_name", "_cif" };
		return this.search(Client.class, page, sortOrder, searchFacets, searchText, fields);
	}
}

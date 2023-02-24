package es.nivel36.laie.ejb.client;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.model.SortField;
import es.nivel36.laie.ejb.core.util.Parameters;
import jakarta.inject.Inject;


public class ClientDao extends AbstractDao {
	
	@Inject
	private SearchFacade searchFacade;

	public boolean checkDuplicatedCif(final String cif) {
		Objects.requireNonNull(cif);
		return this.checkDuplicateField(Client.class, "cif", cif);
	}

	public SearchResult<Client> search(final String searchText, final Page page,  final SortField sortField,
			final String[] searchFacets) {
		Objects.requireNonNull(page);
		final String[] fields = new String[] { "_name", "_cif" };
		return searchFacade.search(Client.class, page, sortField, searchFacets, searchText, fields);
	}

	public Client findAllClientData(Long clientId) {
		Objects.requireNonNull(clientId);
		final String namedQuery = "Client.findAllData";
		final Parameters parameters = map("clientId", clientId);
		return this.findByQuery(Client.class, namedQuery, parameters);
	}
}

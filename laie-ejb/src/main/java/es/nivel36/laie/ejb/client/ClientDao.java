package es.nivel36.laie.ejb.client;

import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClientDao extends AbstractDao {

	public boolean cifExists(final String cif) {
		Objects.requireNonNull(cif);
		return this.fieldExists(Client.class, "cif", cif);
	}

	public SearchResult<Client> searchClients(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		Objects.requireNonNull(page);
		final String[] fields = new String[] { "_name", "_cif" };
		return this.searchFacade.search(Client.class, page, sortField, searchFacets, searchText, fields);
	}
}

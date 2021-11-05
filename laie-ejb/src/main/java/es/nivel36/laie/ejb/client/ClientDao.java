package es.nivel36.laie.ejb.client;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.UidGenerator;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.core.util.Parameters;

@Repository
public class ClientDao extends AbstractDao {

	public void insert(final Client client) {
		Objects.requireNonNull(client);
		this.setUid(client);
		em.persist(client);
	}

	private void setUid(final Client client) {
		String uid;
		do {
			uid = UidGenerator.generate(Client.class);
			client.setUid(uid);
		} while (!this.checkDuplicateUid(uid));
	}

	private boolean checkDuplicateUid(final String uid) {
		final String namedQuery = "Client.checkDuplicateUid";
		final Parameters parameters = map("uid", uid);
		return this.findByQuery(Boolean.class, namedQuery, parameters);
	}

	public Client findClientByUid(final String uid) {
		Objects.requireNonNull(uid);
		final String namedQuery = "Client.findByUid";
		final Parameters parameters = map("uid", uid);
		return this.findByQuery(Client.class, namedQuery, parameters);
	}

	public Client findClientByCif(final String cif) {
		Objects.requireNonNull(cif);
		final String namedQuery = "Client.findByCif";
		final Parameters parameters = map("cif", cif);
		return this.findByQuery(Client.class, namedQuery, parameters);
	}

	public SearchResult<Client> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		Objects.requireNonNull(searchText);
		Objects.requireNonNull(page);
		final String[] fields = new String[] { "_name" };
		return this.search(Client.class, page, sortOrder, searchFacets, searchText, fields);
	}
}
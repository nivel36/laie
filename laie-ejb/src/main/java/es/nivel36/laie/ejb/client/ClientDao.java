package es.nivel36.laie.ejb.client;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractIndexedDao;
import es.nivel36.laie.ejb.core.model.Repository;

@Repository
public class ClientDao extends AbstractIndexedDao<Client> {

	public Client findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(Client.class, "Client.findByUid", map("clientUid", uid));
	}

	public Client findByCif(final String cif) {
		Objects.requireNonNull(cif);
		return this.findByQuery(Client.class, "Client.findByCif", map("cif", cif));
	}

	@Override
	public Class<Client> getType() {
		return Client.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "_name" };
	}
}
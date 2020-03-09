package ged.ejb.client;

import static ged.ejb.core.util.Parameters.map;

import java.util.Objects;

import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Repository;

@Repository
public class ClientDao extends AbstractIndexedDao<Client> {

	public Client findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(Client.class, "Client.findByUid", map("uid", uid));
	}

	public Client findClientByCif(final String cif) {
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
package ged.ejb.client;

import static ged.ejb.core.util.Parameters.map;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class ClientDao extends AbstractDao<Client> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	public Client findAllClientDataByClientId(final long clientId) {
		if (clientId < 0) {
			logger.warn("Bad client id {}", clientId);
			throw new IllegalArgumentException();
		}
		return this.findByQuery(Client.class, "Client.findByClientId", map("clientId", clientId));
	}

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
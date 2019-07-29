package ged.ejb.client;

import static ged.ejb.core.util.Parameters.map;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class ClientDao extends AbstractDao<Client> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	public boolean clientExists(final String clientName) {
		Objects.requireNonNull(clientName);
		return (boolean) this.findByQuery("Client.clientExist", map("name", clientName));
	}

	public Client findAllClientDataByClientId(final long clientId) {
		if (clientId < 0) {
			logger.warn("Bad client id {}", clientId);
			throw new IllegalArgumentException();
		}
		return this.findByQuery(Client.class, "Client.findByClientId", map("clientId", clientId));
	}

	public Client findClientByCif(final String cif) {
		Objects.requireNonNull(cif);
		try {
			return this.findByQuery(Client.class, "Client.findByCif", map("cif", cif));
		} catch (final NoResultException e) {
			logger.debug("No client found", e);
			return null;
		}
	}

	public Client findClientByName(final String clientName) {
		Objects.requireNonNull(clientName);
		try {
			return this.findByQuery(Client.class, "Client.findByName", map("name", clientName));
		} catch (final NoResultException e) {
			logger.debug("No client found", e);
			return null;
		}
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
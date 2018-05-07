package ged.ejb.client;

import static ged.ejb.core.util.Parameters.map;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class ClientDaoJpa extends AbstractDaoJpa<Client> implements ClientDao {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Override
	public boolean clientExists(final String clientName) {
		Objects.requireNonNull(clientName);
		return (boolean) this.findByQuery("Client.clientExist", map("name", clientName));
	}

	@Override
	public Client findByName(final String clientName) {
		try {
			Objects.requireNonNull(clientName);
			return this.findByQuery(Client.class, "Client.findByName", map("name", clientName));
		}
		catch (final NoResultException e) {
			logger.debug("No client found", e);
			return null;
		}
	}

	@Override
	public Class<Client> getType() {
		return Client.class;
	}

	@Override
	public List<Client> search(final String searchText) {
		return this.getPersistenceFacade().search(Client.class, searchText, "name");
	}
}
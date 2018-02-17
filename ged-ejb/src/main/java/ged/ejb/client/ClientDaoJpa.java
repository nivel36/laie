package ged.ejb.client;

import static ged.ejb.core.model.FluentHashMap.map;
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
	public boolean clientExist(final String clientName) {
		Objects.requireNonNull(clientName);
		return (boolean) this.findByQuery("Client.clientExist", map("name", clientName));
	}

	@Override
	public Client findByName(final String clientName) {
		Objects.requireNonNull(clientName);
		Client client;
		try {
			client = this.findByQuery(Client.class, "Client.findByName", map("name", clientName));
		} catch (final NoResultException e) {
			logger.debug("No client found", e);
			client = null;
		}
		return client;
	}

	@Override
	public Class<Client> getType() {
		return Client.class;
	}

	@Override
	public List<Client> search(final String searchText) {
		return getPersistenceFacade().search(Client.class, searchText, "name");
	}
}
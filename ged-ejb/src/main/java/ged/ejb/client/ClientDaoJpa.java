package ged.ejb.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;

@Repository
public class ClientDaoJpa extends AbstractDao<Long, Client> implements ClientDao {

	private final Logger logger = Logger.getLogger(ClientDaoJpa.class.getName());

	@Inject
	public ClientDaoJpa(@Repository final PersistenceFacade persistenceFacade) {
		super(persistenceFacade);
	}

	@Override
	public Client findByName(final String clientName) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", clientName);
		Client client;
		try {
			client = this.persistenceFacade.findByTypedQuery(Client.class, "Client.findByName", parameters);
		} catch (final NoResultException e) {
			this.logger.log(Level.FINE, "No client found with that name", e);
			client = null;
		}
		return client;
	}

	@Override
	public Class<Client> getClazz() {
		return Client.class;
	}

	@Override
	public List<Client> searchByName(final String clientName) {
		return null;
	}
}
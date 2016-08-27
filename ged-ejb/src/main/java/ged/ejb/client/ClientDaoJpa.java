package ged.ejb.client;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.core.model.Repository;

@Repository
public class ClientDaoJpa extends AbstractDao<Long, Client> implements ClientDao {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	@Override
	public Client findByName(final String clientName) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", clientName);
		Client client = null;
		try {
			client = this.persistenceFacade.findByTypedQuery(Client.class, "Client.findByName", parameters);
		} catch (final NoResultException e) {
			client = null;
		}
		return client;
	}

	@Override
	public Class<Client> getClazz() {
		return Client.class;
	}
}
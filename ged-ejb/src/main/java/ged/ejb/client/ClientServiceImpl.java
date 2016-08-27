package ged.ejb.client;

import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

public class ClientServiceImpl extends AbstractService<Long, Client> implements ClientService {

	@Inject
	@Repository
	private ClientDao clientDao;

	@Override
	public Client findByName(final String clientName) {
		return this.clientDao.findByName(clientName);
	}

	@Override
	public Dao<Long, Client> getDao() {
		return this.clientDao;
	}
}
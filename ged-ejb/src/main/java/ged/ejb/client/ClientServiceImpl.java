package ged.ejb.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class ClientServiceImpl extends AbstratctAuditedService<Client> implements ClientService {

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

	@Override
	public List<Client> searchByName(final String clientName) {
		final List<Client> clients;
		if (clientName == null) {
			clients = this.clientDao.findAll();
		} else {
			clients = this.clientDao.searchByName(clientName);
		}
		return clients;
	}

	public void setClientDao(final ClientDao clientDao) {
		this.clientDao = clientDao;
	}
}
package ged.ejb.client;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class ClientServiceImpl extends AbstratctAuditedService<Client> implements ClientService {

	private final ClientDao clientDao;

	@Inject
	public ClientServiceImpl(final Logger logger, @Repository final ClientDao clientDao) {
		super(logger);
		this.clientDao = clientDao;
	}

	@Override
	public Client findByName(final String clientName) {
		if (clientName == null) {
			throw new NullPointerException("El nombre del cliente no puede ser nulo");
		}
		this.logger.log(Level.FINE, "Buscando cliente con nombre {}", clientName);
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
			this.logger.log(Level.FINE, "Buscando todos los clientes");
			clients = this.clientDao.findAll();
		} else {
			this.logger.log(Level.FINE, "Buscando clientes con nombre {}", clientName);
			clients = this.clientDao.searchByName(clientName);
		}
		return clients;
	}
}
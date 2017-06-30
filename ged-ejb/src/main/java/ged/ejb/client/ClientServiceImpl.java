package ged.ejb.client;

import java.util.List;
import java.util.Objects;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class ClientServiceImpl extends AbstratctAuditedService<Client> implements ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class.getName());

	private final ClientDao clientDao;

	@Inject
	public ClientServiceImpl(@Repository final ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	@Override
	public boolean existsClient(final String clientName) {
		// TODO arreglar
		return findByName(clientName) != null;
	}

	@Override
	public Client findByName(final String clientName) {
		Objects.requireNonNull(clientName, "El nombre del cliente no puede ser nulo");
		ClientServiceImpl.logger.debug("Buscando cliente con nombre {}", clientName);
		return this.clientDao.findByName(clientName);
	}

	@Override
	public Dao<Client> getDao() {
		return this.clientDao;
	}

	@Override
	public List<Client> searchByName(final String clientName) {
		logger.debug("Buscando clientes con nombre {}", clientName);
		return this.clientDao.searchByName(clientName, false);
	}
}
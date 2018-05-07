package ged.ejb.client;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class ClientServiceImpl extends AbstratctAuditedService<Client> implements ClientService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final ClientDao clientDao;

	@Inject
	public ClientServiceImpl(@Repository final ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	@Override
	public boolean clientExist(final String clientName) {
		Objects.requireNonNull(clientName);
		logger.debug("Look for client {} in database", clientName);
		return this.clientDao.clientExists(clientName);
	}

	@Override
	public Client findByName(final String clientName) {
		Objects.requireNonNull(clientName);
		ClientServiceImpl.logger.debug("Find client with name {}", clientName);
		return this.clientDao.findByName(clientName);
	}

	@Override
	public Dao<Client> getDao() {
		return this.clientDao;
	}
}
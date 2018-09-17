package ged.ejb.client;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractAuditedService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class ClientService extends AbstractAuditedService<Client> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private ClientDao clientDao;

	public boolean clientExists(final String clientName) {
		Objects.requireNonNull(clientName);
		logger.debug("Testing if client {} exists in database", clientName);
		return this.clientDao.clientExists(clientName);
	}

	public Client findAllClientDataByClientId(final long clientId) {
		if (clientId < 0) {
			logger.warn("Bad client id {}", clientId);
			throw new IllegalArgumentException();
		}
		logger.debug("Finding all client data by id {}", clientId);
		return this.clientDao.findAllClientDataByClientId(clientId);
	}

	public Client findClientByName(final String clientName) {
		Objects.requireNonNull(clientName);
		logger.debug("Finding client by name {}", clientName);
		return this.clientDao.findClientByName(clientName);
	}

	@Override
	public AbstractDao<Client> getDao() {
		return this.clientDao;
	}

	public void setClientDao(final ClientDao clientDao) {
		this.clientDao = clientDao;
	}
}
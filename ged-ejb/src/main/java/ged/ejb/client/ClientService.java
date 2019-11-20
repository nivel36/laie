package ged.ejb.client;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class ClientService extends AbstractService<Client> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private ClientDao clientDao;

	public Client findAllClientDataByClientId(final long clientId) {
		if (clientId < 0) {
			logger.warn("Bad client id {}", clientId);
			throw new IllegalArgumentException();
		}
		logger.debug("Finding all client data by id {}", clientId);
		return this.clientDao.findAllClientDataByClientId(clientId);
	}
	
	public Client findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.clientDao.findByUid(uid);
	}

	public Client findClientByCif(final String cif) {
		Objects.requireNonNull(cif);
		logger.debug("Finding client by cif {}", cif);
		return this.clientDao.findClientByCif(cif);
	}

	@Override
	public AbstractDao<Client> getDao() {
		return this.clientDao;
	}

	public void setClientDao(final ClientDao clientDao) {
		this.clientDao = clientDao;
	}
}
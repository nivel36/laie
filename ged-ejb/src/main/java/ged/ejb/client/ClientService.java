package ged.ejb.client;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractIndexedService;
import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Repository;

@Stateless
public class ClientService extends AbstractIndexedService<Client> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private ClientDao clientDao;

	public Client findByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find client by uid {}", uid);
		return this.clientDao.findByUid(uid);
	}

	public Client findClientByCif(final String cif) {
		Objects.requireNonNull(cif);
		logger.debug("Find client by cif {}", cif);
		return this.clientDao.findClientByCif(cif);
	}

	@Override
	public AbstractIndexedDao<Client> getDao() {
		return this.clientDao;
	}

	public void setClientDao(final ClientDao clientDao) {
		Objects.requireNonNull(clientDao);
		this.clientDao = clientDao;
	}
}
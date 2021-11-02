package es.nivel36.laie.ejb.client;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Repository;

@Stateless
public class ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

	@Inject
	@Repository
	private ClientDao clientDao;

	private ClientMapper clientMapper = new ClientMapper();

	private ClientMerger clientMerger = new ClientMerger();

	public void addClient(final ClientDto client) {
		Objects.requireNonNull(client);
		logger.debug("Add new client {}", client);
		Client entity = new Client();
		clientMerger.merge(entity, client);
		this.clientDao.insert(entity);
	}

	public void updateClient(final ClientDto client) {
		Objects.requireNonNull(client);
		logger.debug("Update client {}", client);
		final String uid = client.getUid();
		final Client entity = this.clientDao.findClientByUid(uid);
		clientMerger.merge(entity, client);
	}

	public ClientDto findClientByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find client by uid {}", uid);
		final Client client = this.clientDao.findClientByUid(uid);
		return this.clientMapper.map(client);
	}

	public ClientDto findClientByCif(final String cif) {
		Objects.requireNonNull(cif);
		logger.debug("Find client by cif {}", cif);
		final Client client = this.clientDao.findClientByCif(cif);
		return this.clientMapper.map(client);
	}

	public void setClientDao(final ClientDao clientDao) {
		Objects.requireNonNull(clientDao);
		this.clientDao = clientDao;
	}
}
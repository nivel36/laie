package es.nivel36.laie.ejb.client;

import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;
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

	private ClientMapper clientMapper;

	private ClientMerger clientMerger;

	@PostConstruct
	public void init() {
		this.clientMapper = new ClientMapper();
		this.clientMerger = new ClientMerger();
	}

	public void insert(final ClientDto client) {
		Objects.requireNonNull(client);
		Client entity = new Client();
		clientMerger.merge(entity, client);
		this.clientDao.insert(entity);
	}

	public void update(final ClientDto client) {
		Objects.requireNonNull(client);
		final Client entity = this.clientDao.findContactByUid(client.getUid());
		clientMerger.merge(entity, client);
	}

	public ClientDto findByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find client by uid {}", uid);
		final Client client = this.clientDao.findContactByUid(uid);
		return this.clientMapper.map(client);
	}

	public ClientDto findByCif(final String cif) {
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
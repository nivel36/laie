package es.nivel36.laie.ejb.client;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hibernate.search.query.facet.Facet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.core.model.Page;
import es.nivel36.core.model.Repository;
import es.nivel36.core.model.search.SearchFacets;
import es.nivel36.core.model.search.SearchResult;
import es.nivel36.core.model.search.SortField;
import es.nivel36.files.FileService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;

@Stateless
public class ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

	@Inject
	@Repository
	private ClientDao clientDao;
	
	@Inject
	@Repository
	private UserDao userDao;
	
	@EJB
	private FileService fileService;

	private ClientMapper clientMapper;

	private ClientMerger clientMerger;
	
	@PostConstruct
	public void init() {
		clientMapper = new ClientMapper(fileService);
		clientMerger = new ClientMerger();
	}
	
	public ClientDto addClient(final ClientDto client, final String ownerUid) {
		Objects.requireNonNull(client);
		Objects.requireNonNull(ownerUid);
		logger.debug("Add new client {} with owner {}", client, ownerUid);
		final Client entity = new Client();
		clientMerger.merge(entity, client);
		this.changeOwner(entity, ownerUid);
		this.clientDao.insert(entity);
		return clientMapper.map(entity);
	}
	
	public void changeOwner(final String clientUid, final String ownerUid) {
		Objects.requireNonNull(clientUid);
		Objects.requireNonNull(ownerUid);
		logger.debug("Change clients {} owner {}", clientUid, ownerUid);
		final Client entity = this.clientDao.findClientByUid(clientUid);
		changeOwner(entity, ownerUid);
	}

	private void changeOwner(final Client entity, final String ownerUid) {
		final User owner = this.userDao.findUserByUid(ownerUid);
		entity.setOwner(owner);
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
	
	public SearchResult<ClientDto> search(final String searchText, final Page page) {
		return this.search(searchText, page, null, null);
	}

	public SearchResult<ClientDto> search(final String searchText, final Page page, final SortField sortField,
			final SearchFacets searchFacets) {
		Objects.requireNonNull(page);
		final SearchResult<Client> restul = this.clientDao.search(searchText, page, sortField, searchFacets);
		final List<Client> resultData = restul.getResultData();
		final List<ClientDto> mapList = clientMapper.mapList(resultData);
		final Map<String, List<Facet>> allFacets = restul.getAllFacets();
		final int count = restul.getCount();
		return new SearchResult<ClientDto>(mapList, count, allFacets);
	}

	public void setClientDao(final ClientDao clientDao) {
		Objects.requireNonNull(clientDao);
		this.clientDao = clientDao;
	}
	
	public void setUserDao(final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}
}
package es.nivel36.laie.ejb.client;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hibernate.search.query.facet.Facet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;

@Stateless
public class ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

	@Inject
	@Repository
	private ClientDao clientDao;

	private ClientMapper clientMapper = new ClientMapper();

	private ClientMerger clientMerger = new ClientMerger();

	public ClientDto addClient(final ClientDto client) {
		Objects.requireNonNull(client);
		logger.debug("Add new client {}", client);
		Client entity = new Client();
		clientMerger.merge(entity, client);
		this.clientDao.insert(entity);
		return clientMapper.map(entity);
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
		Objects.requireNonNull(searchText);
		Objects.requireNonNull(page);
		final SearchResult<Client> restul = this.clientDao.search(searchText, page, sortField, searchFacets);
		final List<Client> resultData = restul.getResultData();
		final List<ClientDto> mapList = new ClientMapper().mapList(resultData);
		final Map<String, List<Facet>> allFacets = restul.getAllFacets();
		final int count = restul.getCount();
		return new SearchResult<ClientDto>(mapList, count, allFacets);
	}

	public void setClientDao(final ClientDao clientDao) {
		Objects.requireNonNull(clientDao);
		this.clientDao = clientDao;
	}
}
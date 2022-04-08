package es.nivel36.laie.ejb.client;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

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

	public void addClient(final Client client) {
		Objects.requireNonNull(client);
		logger.debug("Add new client {}", client);
		this.clientDao.insert(client);
	}

	public Client updateClient(final Client client) {
		Objects.requireNonNull(client);
		logger.debug("Update client {}", client);
		return clientDao.update(client);
	}

	public Client findClientById(final Long clientId) {
		Objects.requireNonNull(clientId);
		logger.debug("Find client by id {}", clientId);
		return this.clientDao.find(Client.class, clientId);
	}

	public SearchResult<Client> search(final String searchText, final Page page) {
		return this.search(searchText, page, null, null);
	}

	public SearchResult<Client> search(final String searchText, final Page page, final SortField sortField,
			final SearchFacets searchFacets) {
		Objects.requireNonNull(page);
		return this.clientDao.search(searchText, page, sortField, searchFacets);
	}

	public void setClientDao(final ClientDao clientDao) {
		Objects.requireNonNull(clientDao);
		this.clientDao = clientDao;
	}
}
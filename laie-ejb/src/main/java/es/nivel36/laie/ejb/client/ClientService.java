package es.nivel36.laie.ejb.client;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.action.ActionType;
import es.nivel36.laie.ejb.core.action.Audited;
import es.nivel36.laie.ejb.core.model.Page;

import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;

@Stateless
public class ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

	@Inject
	
	private ClientDao clientDao;

	@Audited(action = ActionType.CREATE)
	public void addClient(final Client client) throws DuplicateCifException {
		Objects.requireNonNull(client);
		logger.debug("Add new client {}", client);
		if (clientDao.checkDuplicatedCif(client.getCif())) {
			throw new DuplicateCifException();
		}
		this.clientDao.insert(client);
	}
	
	@Audited(action = ActionType.UPDATE)
	public Client updateClient(final Client client) throws DuplicateCifException {
		Objects.requireNonNull(client);
		logger.debug("Update client {}", client);
		final Client clientInDatabase = clientDao.find(Client.class, client.getId());
		if (!clientInDatabase.getCif().equals(client.getCif())) {
			if (clientDao.checkDuplicatedCif(client.getCif())) {
				throw new DuplicateCifException();
			}
		}
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
package es.nivel36.laie.ejb.client;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.core.action.Create;
import es.nivel36.laie.ejb.core.action.Update;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

	private @Inject ClientDao clientDao;

	private @Inject @Create Event<Auditable> createClientEvent;

	private @Inject @Update Event<Auditable> updateClientEvent;

	public void addClient(final Client client) throws DuplicateCifException {
		Objects.requireNonNull(client);
		logger.debug("Add new client {}", client);
		if (client.getCif() != null && this.clientDao.checkDuplicatedCif(client.getCif())) {
			throw new DuplicateCifException();
		}
		this.clientDao.insert(client);
		this.createClientEvent.fireAsync(client);
	}

	public Client updateClient(final Client client) throws DuplicateCifException {
		Objects.requireNonNull(client);
		logger.debug("Update client {}", client);
		final Client clientInDatabase = clientDao.find(Client.class, client.getId());
		if (client.getCif() != null && !client.getCif().equals(clientInDatabase.getCif())) {
			if (clientDao.checkDuplicatedCif(client.getCif())) {
				throw new DuplicateCifException();
			}
		}
		final Client updatedClient = clientDao.update(client);
		this.updateClientEvent.fireAsync(updatedClient);
		return updatedClient;
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
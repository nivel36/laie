package es.nivel36.laie.ejb.client;

import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.core.action.Create;
import es.nivel36.laie.ejb.core.action.Update;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

/**
 * Stateless EJB service for managing Client entities.
 * <p>
 * Provides methods for adding, updating, finding and searching Clients. For
 * data consistency reasons, there is no way to delete a client. <br/>
 * This class triggers client creation and update events.
 */
@Stateless
public class ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

	private @Inject ClientDao clientDao;
	private @Inject @Create Event<Auditable> createClientEvent;
	private @Inject @Update Event<Auditable> updateClientEvent;

	/**
	 * Adds a new Client to the system.
	 * <p>
	 * A client can only be added if its CIF is not in use. <br/>
	 * Fires a creation event after successful insertion.
	 *
	 * @param client the {@link Client} to add; must not be null
	 * @throws NullPointerException  if client is null
	 * @throws DuplicateCifException if client's CIF is not null and already exists
	 */
	public void addClient(final Client client) throws DuplicateCifException {
		Objects.requireNonNull(client, "Client must not be null");
		logger.debug("Adding new Client: {}", client);
		if ((client.getCif() != null) && this.clientDao.cifExists(client.getCif())) {
			throw new DuplicateCifException("CIF already exists: " + client.getCif());
		}
		this.clientDao.insert(client);
		this.createClientEvent.fire(client);
		logger.trace("Client {} added successfully.", client);
	}

	/**
	 * Updates an existing Client.
	 * <p>
	 * Checks for CIF duplication if changed.<br/>
	 * Fires an update event.
	 *
	 * @param client the {@link Client} to update; must not be null
	 * @return the updated {@link Client}
	 * @throws NullPointerException  if client is null
	 * @throws DuplicateCifException if new CIF already exists
	 */
	public Client updateClient(final Client client) throws DuplicateCifException {
		Objects.requireNonNull(client, "Client must not be null");
		logger.debug("Updating Client: {}", client);
		final Client clientInDatabase = this.clientDao.find(Client.class, client.getId());
		if ((client.getCif() != null) && !client.getCif().equals(clientInDatabase.getCif())) {
			if (this.clientDao.cifExists(client.getCif())) {
				throw new DuplicateCifException("CIF already exists: " + client.getCif());
			}
		}
		final Client updatedClient = this.clientDao.update(client);
		this.updateClientEvent.fire(updatedClient);
		logger.trace("Client {} updated successfully.", updatedClient);
		return updatedClient;
	}

	/**
	 * Finds a Client by its unique identifier.
	 *
	 * @param clientId the ID of the Client to find
	 * @return the {@link Client} with the given ID, or null if not found
	 */
	public Client findClientById(final long clientId) {
		logger.debug("Finding Client by ID: {}", clientId);
		final Client client = this.clientDao.find(Client.class, clientId);
		logger.trace("Client {} found with ID {}.", client, clientId);
		return client;
	}

	/**
	 * Searches for Clients matching the given text with pagination.
	 *
	 * @param searchText the text to search for; may be null to retrieve all Clients
	 * @param page       the {@link Page} object containing pagination settings;
	 *                   must not be null
	 * @return a {@link SearchResult} of matching Clients
	 */
	public SearchResult<Client> search(final String searchText, final Page page) {
		return this.search(searchText, page, null, null);
	}

	/**
	 * Searches for Clients matching the given text with pagination, sorting, and
	 * facets.
	 *
	 * @param searchText   the text to search for; may be null to retrieve all
	 *                     Clients
	 * @param page         the {@link Page} object containing pagination settings;
	 *                     must not be null
	 * @param sortField    the {@link SortField} to order results by; may be null
	 * @param searchFacets an array of facets to apply; may be null
	 * @return a {@link SearchResult} of matching Clients
	 * @throws NullPointerException if page is null
	 */
	public SearchResult<Client> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		Objects.requireNonNull(page, "Page must not be null");
		final SearchResult<Client> result = this.clientDao.searchClients(searchText, page, sortField, searchFacets);
		logger.trace("Search result {} found for text '{}'.", result, searchText);
		return result;
	}

	/**
	 * Sets the {@link ClientDao} instance, primarily for testing.
	 *
	 * @param clientDao the dao to set; must not be null
	 * @throws NullPointerException if clientDao is null
	 */
	public void setClientDao(final ClientDao clientDao) {
		this.clientDao = Objects.requireNonNull(clientDao, "ClientDao must not be null");
	}

	/**
	 * Sets the create event for auditing, primarily for testing.
	 *
	 * @param createClientEvent the create event to set; must not be null
	 * @throws NullPointerException if createClientEvent is null
	 */
	public void setCreateClientEvent(final Event<Auditable> createClientEvent) {
		this.createClientEvent = Objects.requireNonNull(createClientEvent, "CreateClientEvent must not be null");
	}

	/**
	 * Sets the update event for auditing, primarily for testing.
	 *
	 * @param updateClientEvent the update event to set; must not be null
	 * @throws NullPointerException if updateClientEvent is null
	 */
	public void setUpdateClientEvent(final Event<Auditable> updateClientEvent) {
		this.updateClientEvent = Objects.requireNonNull(updateClientEvent, "UpdateClientEvent must not be null");
	}
}
package es.nivel36.laie.ejb.client;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.core.action.Update;
import es.nivel36.laie.ejb.core.model.Page;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

/**
 * Stateless EJB service for managing Contact entities.
 * <p>
 * Provides methods for adding, updating, finding and deleting Contacts related
 * to a specific Client. <br/>
 * Each modification on a contact fires an update event on the associated
 * client.
 */
@Stateless
public class ContactService {

	private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

	private @Inject ContactDao contactDao;
	private @Inject @Update Event<Auditable> updateClientEvent;

	/**
	 * Adds a new Contact to a Client.
	 * <p>
	 * Fires an update event on the associated client.
	 *
	 * @param contact the {@link Contact} to add; must not be null
	 * @throws NullPointerException if contact is null
	 */
	public void addContact(final Contact contact) {
		Objects.requireNonNull(contact, "Contact must not be null");
		logger.debug("Adding contact {}", contact);
		this.contactDao.insert(contact);
		this.updateClientEvent.fire(contact.getClient());
		logger.trace("Contact {} added successfully.", contact);
	}

	/**
	 * Updates an existing Contact.
	 * <p>
	 * Fires an update event on the associated client.
	 *
	 * @param contact the {@link Contact} to update; must not be null
	 * @return the updated {@link Contact}
	 * @throws NullPointerException if contact is null
	 */
	public Contact updateContact(final Contact contact) {
		Objects.requireNonNull(contact, "Contact must not be null");
		logger.debug("Updating contact {}", contact);
		final Contact updatedContact = this.contactDao.update(contact);
		this.updateClientEvent.fire(contact.getClient());
		logger.trace("Contact {} updated successfully.", updatedContact);
		return updatedContact;
	}

	/**
	 * Deletes a Contact from a Client.
	 * <p>
	 * Fires an update event on the associated client.
	 *
	 * @param contact the {@link Contact} to delete; must not be null
	 * @throws NullPointerException if contact is null
	 */
	public void deleteContact(final Contact contact) {
		Objects.requireNonNull(contact, "Contact must not be null");
		logger.debug("Deleting contact {}", contact);
		this.contactDao.deleteContactByIdAndClientId(contact, contact.getClient());
		this.updateClientEvent.fire(contact.getClient());
		logger.trace("Contact {} deleted successfully.", contact);
	}

	/**
	 * Finds a Contact by its email address.
	 *
	 * @param email the email address to search by; must not be null
	 * @return the {@link Contact} with the given email, or null if not found
	 * @throws NullPointerException if email is null
	 */
	public Contact findContactByEmail(final String email) {
		Objects.requireNonNull(email, "Email must not be null");
		logger.debug("Finding contact by email {}", email);
		final Contact contact = this.contactDao.findContactByEmail(email);
		logger.trace("Contact {} found with email '{}'.", contact, email);
		return contact;
	}

	/**
	 * Finds a Contact by its unique identifier.
	 *
	 * @param contactId the ID of the Contact to find
	 * @return the {@link Contact} with the given ID, or null if not found
	 */
	public Contact findContactById(final long contactId) {
		logger.debug("Finding contact by id {}", contactId);
		final Contact contact = this.contactDao.find(Contact.class, contactId);
		logger.trace("Contact {} found with ID {}.", contact, contactId);
		return contact;
	}

	/**
	 * Retrieves a paginated list of Contacts associated with a Client.
	 *
	 * @param client the {@link Client} to search contacts for; must not be null
	 * @param page   the {@link Page} object containing pagination settings; must
	 *               not be null
	 * @return a list of {@link Contact} objects
	 * @throws NullPointerException if client or page is null
	 */
	public List<Contact> findContactsByClient(final Client client, final Page page) {
		Objects.requireNonNull(client, "Client must not be null");
		Objects.requireNonNull(page, "Page must not be null");
		logger.debug("Finding contacts by client {}, offset {} limit of {}", client, page.getOffset(), page.getLimit());
		final List<Contact> contacts = this.contactDao.findContactsByClient(client, page);
		logger.trace("Found {} contacts for client {}.", contacts.size(), client);
		return contacts;
	}

	/**
	 * Counts the number of Contacts associated with a given Client.
	 *
	 * @param client the {@link Client} to count contacts for; must not be null
	 * @return the number of contacts
	 * @throws NullPointerException if client is null
	 */
	public long countContactsByClient(final Client client) {
		Objects.requireNonNull(client, "Client must not be null");
		logger.debug("Counting contacts by client {}", client);
		final long count = this.contactDao.countContactsByClient(client);
		logger.trace("Found {} contacts for client {}.", count, client);
		return count;
	}

	/**
	 * Sets the {@link ContactDao} instance, primarily for testing.
	 *
	 * @param contactDao the dao to set; must not be null
	 * @throws NullPointerException if contactDao is null
	 */
	public void setContactDao(final ContactDao contactDao) {
		this.contactDao = Objects.requireNonNull(contactDao, "ContactDao must not be null");
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

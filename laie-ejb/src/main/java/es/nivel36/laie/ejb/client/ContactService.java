package es.nivel36.laie.ejb.client;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.core.action.Create;
import es.nivel36.laie.ejb.core.action.Update;
import es.nivel36.laie.ejb.core.model.Page;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Stateless
public class ContactService {

	private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

	private @Inject ContactDao contactDao;

	private @Inject ClientDao clientDao;

	private @Inject @Create Event<Auditable> createClientEvent;

	private @Inject @Update Event<Auditable> updateClientEvent;

	public void addContact(final Contact contact) {
		logger.debug("Add contact {}", contact);
		contactDao.insert(contact);
		this.createClientEvent.fireAsync(contact.getClient());
	}

	public Contact updateContact(final Contact contact) {
		Objects.requireNonNull(contact);
		logger.debug("Update contact {}", contact);
		final Contact updatedContact = this.contactDao.update(contact);
		this.updateClientEvent.fireAsync(contact.getClient());
		return updatedContact;
	}

	public Client deleteContact(final Contact contact) {
		Objects.requireNonNull(contact);
		logger.debug("Delete contact {}", contact);
		contact.getClient().getContacts().remove(contact);
		final Client updatedClient = this.clientDao.update(contact.getClient());
		this.updateClientEvent.fireAsync(updatedClient);
		return updatedClient;
	}

	public Contact findContactByEmail(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Find contact by email {}", email);
		return this.contactDao.findContactByEmail(email);
	}

	public Contact findContactById(final Long contactId) {
		Objects.requireNonNull(contactId);
		logger.debug("Find contact by id {}", contactId);
		return this.contactDao.find(Contact.class, contactId);
	}

	public List<Contact> findContactsByClient(final Client client, final Page page) {
		Objects.requireNonNull(client);
		Objects.requireNonNull(page);
		logger.debug("Find contacts by client {}, offset {} limit of {}", client, page.getOffset(), page.getLimit());
		return this.contactDao.findContactsByClient(client, page);
	}

	public void setContactDao(final ContactDao contactDao) {
		Objects.requireNonNull(contactDao);
		this.contactDao = contactDao;
	}
}
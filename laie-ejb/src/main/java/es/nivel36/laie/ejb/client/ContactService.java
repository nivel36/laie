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

@Stateless
public class ContactService {

	private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

	private @Inject ContactDao contactDao;

	private @Inject @Update Event<Auditable> updateClientEvent;

	public void addContact(final Contact contact) {
		logger.debug("Add contact {}", contact);
		contactDao.insert(contact);
		this.updateClientEvent.fireAsync(contact.getClient());
	}

	public Contact updateContact(final Contact contact) {
		Objects.requireNonNull(contact);
		logger.debug("Update contact {}", contact);
		final Contact updatedContact = this.contactDao.update(contact);
		this.updateClientEvent.fireAsync(contact.getClient());
		return updatedContact;
	}

	public void deleteContact(final Contact contact) {
		Objects.requireNonNull(contact);
		logger.debug("Delete contact {}", contact);
		this.contactDao.deleteContactByIdAndClientId(contact, contact.getClient());
		this.updateClientEvent.fireAsync(contact.getClient());
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

	public long countContactsByClient(final Client client) {
		Objects.requireNonNull(client);
		logger.debug("Count contacts by client {}", client);
		return this.contactDao.countContactsByClient(client);
	}

	public void setContactDao(final ContactDao contactDao) {
		Objects.requireNonNull(contactDao);
		this.contactDao = contactDao;
	}
}
package es.nivel36.laie.ejb.client;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;

@Stateless
public class ContactService {

	private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

	@Inject
	@Repository
	private ContactDao contactDao;

	@Inject
	@Repository
	private ClientDao clientDao;

	public void addContact(final Contact contact) {
		logger.debug("Add contact {}", contact);
		contactDao.insert(contact);
	}

	public Contact updateContact(final Contact contact) {
		Objects.requireNonNull(contact);
		logger.debug("Update contact {}", contact);
		return this.contactDao.update(contact);
	}

	public void deleteContact(final Contact contact) {
		Objects.requireNonNull(contact);
		logger.debug("Delete contact {}", contact);
		contact.getClient().getContacts().remove(contact);
		this.clientDao.update(contact.getClient());
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
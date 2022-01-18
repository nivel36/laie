package es.nivel36.laie.ejb.client;

import java.nio.channels.IllegalSelectorException;
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

	private ContactMapper contactMapper = new ContactMapper();

	private ContactMerger contactMerger = new ContactMerger();

	public void addContact(final String clientUid, final ContactDto contactDto) {
		Objects.requireNonNull(clientUid);
		Objects.requireNonNull(contactDto);
		logger.debug("Add contact {} of client {}", contactDto, clientUid);
		final Contact contact = new Contact();
		contactMerger.merge(contact, contactDto);
		final Client client = this.clientDao.findClientByUid(clientUid);
		contact.setClient(client);
		// Tenemos que insertar el uuid. Por eso no lo hacemos por casacada
		contactDao.insert(contact);
	}

	public void updateContact(ContactDto contactDto) {
		Objects.requireNonNull(contactDto);
		logger.debug("Update contact {}", contactDto);
		final String uid = contactDto.getUid();
		final Contact contact = contactDao.findContactByUid(uid);
		contactMerger.merge(contact, contactDto);
	}

	public void deleteContact(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Delete contact {} ", uid);
		final Contact contact = contactDao.findContactByUid(uid);
		final Client client = contact.getClient();
		boolean removed = client.getContacts().remove(contact);
		//TODO: No funciona
		if (!removed) {
			throw new IllegalSelectorException();
		}
	}

	public ContactDto findContactByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find contact by uid {}", uid);
		final Contact contact = this.contactDao.findContactByUid(uid);
		return contactMapper.map(contact);
	}

	public List<ContactDto> findContactsByClient(final String clientUid, final Page page) {
		Objects.requireNonNull(clientUid);
		Objects.requireNonNull(page);
		logger.debug("Find contacts by client {}, offset {} limit of {}", clientUid, page.getOffset(), page.getLimit());
		final List<Contact> entities = this.contactDao.findContactsByClient(clientUid, page);
		return contactMapper.mapList(entities);
	}

	public void setContactDao(final ContactDao contactDao) {
		Objects.requireNonNull(contactDao);
		this.contactDao = contactDao;
	}
}
package es.nivel36.laie.ejb.client;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
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

	private ContactMapper contactMapper;

	private ContactMerger contactMerger;

	@PostConstruct
	public void init() {
		contactMapper = new ContactMapper();
		contactMerger = new ContactMerger();
	}

	public void addContact(final String clientUid, final ContactDto contact) {
		Objects.requireNonNull(clientUid);
		Objects.requireNonNull(contact);
		logger.debug("Add contact {} of client {}", contact, clientUid);
		final Contact entity = new Contact();
		contactMerger.merge(entity, contact);
		final Client client = this.clientDao.findClientByUid(clientUid);
		entity.setClient(client);
		this.contactDao.insert(entity);
	}

	public void updateContact(ContactDto contact) {
		Objects.requireNonNull(contact);
		logger.debug("Update contact {}", contact);
		final String uid = contact.getUid();
		final Contact entity = contactDao.findContactByUid(uid);
		contactMerger.merge(entity, contact);
	}

	public void deleteContact(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Delete contact {} ", uid);
		final Contact contact = contactDao.findContactByUid(uid);
		this.contactDao.delete(contact);
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
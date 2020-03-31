package ged.ejb.client;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractIndexedService;
import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Stateless
public class ContactService extends AbstractIndexedService<Contact> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private ContactDao contactDao;

	public Contact findByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find contact by uid {}", uid);
		return this.contactDao.findByUid(uid);
	}

	public List<Contact> findContactsByClient(final Client client, final Page page) {
		Objects.requireNonNull(client);
		logger.debug("Find contacts by client {}", client);
		return this.contactDao.findContactsByClient(client, page);
	}

	@Override
	protected AbstractIndexedDao<Contact> getDao() {
		return this.contactDao;
	}

	public void setContactDao(final ContactDao contactDao) {
		Objects.requireNonNull(contactDao);
		this.contactDao = contactDao;
	}
}
package es.nivel36.laie.ejb.client;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.AbstractIndexedService;
import es.nivel36.laie.ejb.core.model.AbstractIndexedDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;

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

	public List<Contact> findContactsByClient(final String clientUid, final Page page) {
		Objects.requireNonNull(clientUid);
		logger.debug("Find contacts by client {}", clientUid);
		return this.contactDao.findContactsByClient(clientUid, page);
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
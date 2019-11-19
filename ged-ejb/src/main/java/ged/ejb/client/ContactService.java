package ged.ejb.client;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobOffer;

@Stateless
public class ContactService extends AbstractService<Contact> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private ContactDao contactDao;

	public Contact findContactByEmail(final String email) {
		Objects.requireNonNull(email, "Email can't be null");
		return this.contactDao.findContactByEmail(email);
	}

	public List<Contact> findContactsByClient(final Client client) {
		Objects.requireNonNull(client);
		logger.debug("Finding contacts by client {}", client);
		return this.contactDao.findContactsByClient(client);
	}

	@Override
	protected AbstractDao<Contact> getDao() {
		return this.contactDao;
	}

	public void setContactDao(final ContactDao contactDao) {
		this.contactDao = contactDao;
	}
	
	public JobOffer findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.contactDao.findByUid(uid);
	}
}
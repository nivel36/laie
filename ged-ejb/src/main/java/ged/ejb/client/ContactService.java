package ged.ejb.client;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractAuditedService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class ContactService extends AbstractAuditedService<Contact> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private ContactDao contactDao;

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
}
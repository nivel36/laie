package ged.ejb.client;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractAuditedService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class ContactServiceImpl extends AbstractAuditedService<Contact> implements ContactService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final ContactDao contactDao;

	@Inject
	public ContactServiceImpl(@Repository final ContactDao contactDao) {
		Objects.requireNonNull(contactDao);
		this.contactDao = contactDao;
		logger.trace("ContactServiceImpl initiated");
	}

	@Override
	public List<Contact> findContactsByClient(final Client client) {
		Objects.requireNonNull(client);
		logger.debug("Find contacts by client {}", client);
		return this.contactDao.findContactsByClient(client);
	}

	@Override
	protected Dao<Contact> getDao() {
		return this.contactDao;
	}
}
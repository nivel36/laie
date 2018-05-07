package ged.ejb.client;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class ContactServiceImpl extends AbstratctAuditedService<Contact> implements ContactService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final ContactDao contactDao;

	@Inject
	public ContactServiceImpl(@Repository final ContactDao contactDao) {
		Objects.requireNonNull(contactDao);
		this.contactDao = contactDao;
	}

	@Override
	public List<Contact> findContactsByClientId(final long clientId) {
		if (clientId < 1) {
			logger.error("Bad client id {}", clientId);
			throw new IllegalArgumentException("Bad client id " + clientId);
		}
		return this.contactDao.findContactsByClientId(clientId);
	}

	@Override
	protected Dao<Contact> getDao() {
		return this.contactDao;
	}
}
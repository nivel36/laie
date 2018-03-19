package ged.ejb.client;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class ContactServiceImpl extends AbstratctAuditedService<Contact> implements ContactService {

	private final ContactDao contactDao;

	@Inject
	public ContactServiceImpl(@Repository final ContactDao contactDao) {
		Objects.requireNonNull(contactDao);
		this.contactDao = contactDao;
	}

	@Override
	public List<Contact> findByClientId(final long clientId) {
		return this.contactDao.findByClientId(clientId);
	}

	@Override
	protected Dao<Contact> getDao() {
		return this.contactDao;
	}
}
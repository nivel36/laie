package ged.ejb.client;

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
		this.contactDao = contactDao;
	}

	@Override
	protected Dao<Contact> getDao() {
		return this.contactDao;
	}
}
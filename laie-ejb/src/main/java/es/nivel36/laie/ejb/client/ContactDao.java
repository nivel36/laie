package es.nivel36.laie.ejb.client;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;

import es.nivel36.laie.ejb.core.model.AbstractIndexedDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;

@Repository
public class ContactDao extends AbstractIndexedDao<Contact> {

	public Contact findByUid(final String uid) {
		Objects.requireNonNull(uid);
		try {
			return this.findByQuery(Contact.class, "Contact.findByUid", map("uid", uid));
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Contact> findContactsByClient(final String clientUid, final Page page) {
		Objects.requireNonNull(clientUid);
		return this.getPersistenceFacade().findByQuery(Contact.class, "Contact.findByClient",
				map("clientUid", clientUid), page);
	}

	@Override
	protected Class<Contact> getType() {
		return Contact.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "_name", "_surname", "_email" };
	}
}
package ged.ejb.client;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobOffer;

@Repository
public class ContactDao extends AbstractDao<Contact> {

	protected boolean existUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(Boolean.class, "Contact.existUid", map("uid", uid));
	}

	public JobOffer findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(JobOffer.class, "Contact.findByUid", map("uid", uid));
	}
	
	public Contact findContactByEmail(final String email) {
		Objects.requireNonNull(email);
		return this.findByQuery(Contact.class, "Contact.findByEmail", map("email", email));
	}

	public List<Contact> findContactsByClient(final Client client) {
		Objects.requireNonNull(client);
		return this.getPersistenceFacade().findByQuery(Contact.class, "Contact.findByClient", map("client", client),
				Page.ALL);
	}
	
	@Override
	protected Class<Contact> getType() {
		return Contact.class;
	}

	@Override
	protected void preInsert(final Contact contact) {
		final String base64Id = generateUid();
		contact.setUid(base64Id);
	}

	@Override
	public String[] searchFields() {
		return new String[] { "name", "surname", "email" };
	}
}
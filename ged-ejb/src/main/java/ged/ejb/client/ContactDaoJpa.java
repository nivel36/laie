package ged.ejb.client;

import static ged.ejb.core.util.Parameters.map;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class ContactDaoJpa extends AbstractDaoJpa<Contact> implements ContactDao {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Override
	public List<Contact> findContactsByClientId(final long clientId) {
		if (clientId < 1) {
			logger.error("Bad clientId {}", clientId);
			throw new IllegalArgumentException();
		}
		return this.getPersistenceFacade().findByQuery(Contact.class, "Contact.findByClientId", map("clientId", clientId), 0, 0);
	}

	@Override
	protected Class<Contact> getType() {
		return Contact.class;
	}

	@Override
	public List<Contact> search(final String searchText) {
		Objects.requireNonNull(searchText);
		return this.getPersistenceFacade().search(Contact.class, searchText, "name", "email");
	}
}
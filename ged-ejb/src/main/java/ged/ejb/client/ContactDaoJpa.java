package ged.ejb.client;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class ContactDaoJpa extends AbstractDaoJpa<Contact> implements ContactDao {

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
package es.nivel36.laie.ejb.client;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.model.SortField;
import es.nivel36.laie.ejb.core.util.Parameters;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class ContactDao extends AbstractDao {

	private @Inject SearchFacade searchFacade;

	public List<Contact> findContactsByClient(final Client client, final Page page) {
		Objects.requireNonNull(client);
		Objects.requireNonNull(page);
		final String namedQuery = "Contact.findByClient";
		final Parameters parameters = map("client", client);
		return this.findByQuery(Contact.class, namedQuery, parameters, page);
	}

	public int deleteContactByIdAndClientId(final Contact contact, final Client client) {
		Objects.requireNonNull(client);
		final String namedQuery = "Contact.deleteByIdAndClientId";
		Query query = this.em.createNamedQuery(namedQuery);
		query.setParameter("client", client);
		query.setParameter("contact", contact);
		return query.executeUpdate();
	}

	public long countContactsByClient(final Client client) {
		Objects.requireNonNull(client);
		final String namedQuery = "Contact.countByClient";
		final Parameters parameters = map("client", client);
		return this.findByQuery(Long.class, namedQuery, parameters);
	}

	public Contact findContactByEmail(final String email) {
		Objects.requireNonNull(email);
		try {
			final String namedQuery = "Contact.findByEmail";
			final Parameters parameters = map("email", email);
			return this.findByQuery(Contact.class, namedQuery, parameters);
		} catch (final NoResultException e) {
			return null;
		}
	}

	public SearchResult<Contact> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		Objects.requireNonNull(searchText);
		Objects.requireNonNull(page);
		final String[] fields = new String[] { "_name", "_surname, _email" };
		return searchFacade.search(Contact.class, page, sortField, searchFacets, searchText, fields);
	}

}
package es.nivel36.laie.ejb.client;

import java.util.List;
import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class ContactDao extends AbstractDao {

	public List<Contact> findContactsByClient(final Client client, final Page page) {
		Objects.requireNonNull(client);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT c
				FROM Contact c
				LEFT JOIN FETCH c.client
				WHERE c.client = :client
				""";
		final TypedQuery<Contact> query = em.createQuery(jpql, Contact.class);
		query.setParameter("client", client);
		this.paginate(page, query);
		return query.getResultList();
	}

	public int deleteContactByIdAndClientId(final Contact contact, final Client client) {
		Objects.requireNonNull(client);
		final String jpql = """
				DELETE FROM Contact c
				WHERE c = :contact
				AND c.client = :client
				""";
		final Query query = this.em.createQuery(jpql);
		query.setParameter("client", client);
		query.setParameter("contact", contact);
		return query.executeUpdate();
	}

	public long countContactsByClient(final Client client) {
		Objects.requireNonNull(client);
		final String jpql = """
				SELECT COUNT(c)
				FROM Contact c
				WHERE c.client = :client
				""";
		final TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter("client", client);
		return query.getFirstResult();
	}

	public Contact findContactByEmail(final String email) {
		Objects.requireNonNull(email);
		try {
			final String jpql = """
					SELECT c
					FROM Contact c
					WHERE c.email=:email
					""";
			final TypedQuery<Contact> query = em.createQuery(jpql, Contact.class);
			query.setParameter("email", email);
			return query.getSingleResult();
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
package es.nivel36.laie.ejb.client;

import java.util.List;
import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
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
		final TypedQuery<Contact> query = this.em.createQuery(jpql, Contact.class);
		query.setParameter("client", client);
		this.paginate(page, query);
		return query.getResultList();
	}

	public long countContactsByClient(final Client client) {
		Objects.requireNonNull(client);
		final String jpql = """
				SELECT COUNT(c)
				FROM Contact c
				WHERE c.client = :client
				""";
		final TypedQuery<Long> query = this.em.createQuery(jpql, Long.class);
		query.setParameter("client", client);
		return query.getSingleResult();
	}

	public Contact findContactByEmail(final String email) {
		Objects.requireNonNull(email);
		final String jpql = """
				SELECT c
				FROM Contact c
				WHERE c.email=:email
				""";
		final TypedQuery<Contact> query = this.em.createQuery(jpql, Contact.class);
		query.setParameter("email", email);
		return query.getSingleResult();
	}

	public SearchResult<Contact> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		Objects.requireNonNull(searchText);
		Objects.requireNonNull(page);
		final String[] fields = new String[] { "_name", "_surname", "_email" };
		return this.searchFacade.search(Contact.class, page, sortField, searchFacets, searchText, fields);
	}
}
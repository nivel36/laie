package es.nivel36.laie.ejb.client;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.core.util.Parameters;

@Repository
public class ContactDao extends AbstractDao {
	
	@Inject
	private SearchFacade searchFacade;

	public List<Contact> findContactsByClient(final Client client, final Page page) {
		Objects.requireNonNull(client);
		Objects.requireNonNull(page);
		final String namedQuery = "Contact.findByClient";
		final Parameters parameters = map("client", client);
		return this.findByQuery(Contact.class, namedQuery, parameters, page);
	}
	
	public Contact findContactByEmail(final String email) {
		Objects.requireNonNull(email);
		final String namedQuery = "Contact.findByEmail";
		final Parameters parameters = map("email", email);
		return this.findByQuery(Contact.class, namedQuery, parameters);
	}

	public SearchResult<Contact> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		Objects.requireNonNull(searchText);
		Objects.requireNonNull(page);
		final String[] fields = new String[] { "_name", "_surname, _email" };
		return searchFacade.search(Contact.class, page, sortOrder, searchFacets, searchText, fields);
	}
}
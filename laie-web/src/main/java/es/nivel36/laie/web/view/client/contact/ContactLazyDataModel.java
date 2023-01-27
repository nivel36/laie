package es.nivel36.laie.web.view.client.contact;


import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.client.ContactService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;
import jakarta.inject.Inject;

public class ContactLazyDataModel extends AbstractLazyDataModel<Contact> {

	private static final long serialVersionUID = -4218300046788680778L;
	
	@Inject
	private transient ContactService contactService;

	@Override
	protected SearchResult<Contact> search(String searchText, Page page, SortField sortField,
			String[] searchFilter) {
		return null;
	}

	@Override
	protected Contact find(Long id) {
		return contactService.findContactById(id);
	}

}
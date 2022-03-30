package es.nivel36.laie.web.view.client.contact;

import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;

import es.nivel36.core.model.Page;
import es.nivel36.core.model.search.SearchFacets;
import es.nivel36.core.model.search.SearchResult;
import es.nivel36.core.model.search.SortField;
import es.nivel36.laie.ejb.client.ContactDto;
import es.nivel36.laie.ejb.client.ContactService;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;

public class ContactLazyDataModel extends AbstractLazyDataModel<ContactDto> {

	private static final long serialVersionUID = -4218300046788680778L;
	
	private transient ContactService contactService;

	public ContactLazyDataModel(final ContactService contactService) {
		Objects.requireNonNull(contactService, "ContactService can't be null");
		this.contactService = contactService;
	}

	@Override
	protected SearchResult<ContactDto> search(String searchText, Page page, SortField sortField,
			SearchFacets searchFilter) {
		return null;
	}

	@Override
	protected ContactDto find(String rowkey) {
		return contactService.findContactByUid(rowkey);
	}

	@Override
	protected String getKey(ContactDto entity) {
		return entity.getUid();
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 0;
	}
}
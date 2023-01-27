package es.nivel36.laie.web.view.client;

import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;
import jakarta.inject.Inject;

public class ClientLazyDataModel extends AbstractLazyDataModel<Client> {

	private static final long serialVersionUID = 4434716428350786274L;

	@Inject
	private transient ClientService clientService;

	@Override
	protected SearchResult<Client> search(String searchText, Page page, SortField sortField, String[] searchFilter) {
		return clientService.search(searchText, page, sortField, searchFilter);
	}

	@Override
	protected Client find(Long id) {
		return clientService.findClientById(id);
	}

}

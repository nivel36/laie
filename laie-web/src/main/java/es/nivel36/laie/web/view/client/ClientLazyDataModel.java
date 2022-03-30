package es.nivel36.laie.web.view.client;

import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;

import es.nivel36.core.model.Page;
import es.nivel36.core.model.search.SearchFacets;
import es.nivel36.core.model.search.SearchResult;
import es.nivel36.core.model.search.SortField;
import es.nivel36.laie.ejb.client.ClientDto;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;

public class ClientLazyDataModel extends AbstractLazyDataModel<ClientDto> {

	private static final long serialVersionUID = 4434716428350786274L;
	
	private transient ClientService clientService;

	public ClientLazyDataModel(final ClientService clientService) {
		Objects.requireNonNull(clientService);
		this.clientService = clientService;
	}

	protected SearchResult<ClientDto> search(String searchText, Page page, SortField sortField,
			SearchFacets searchFilter) {
		return clientService.search(searchText, page, sortField, searchFilter);
	}

	@Override
	protected ClientDto find(String rowkey) {
		return clientService.findClientByUid(rowkey);
	}

	@Override
	protected String getKey(ClientDto entity) {
		return entity.getUid();
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 0;
	}
}

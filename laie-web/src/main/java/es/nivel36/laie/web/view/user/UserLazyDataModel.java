package es.nivel36.laie.web.view.user;

import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;

public class UserLazyDataModel extends AbstractLazyDataModel<UserDto> {

	private static final long serialVersionUID = 6698863542667375365L;
	
	private transient UserService userService;

	public UserLazyDataModel(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	@Override
	protected SearchResult<UserDto> search(String searchText, Page page, SortField sortField,
			SearchFacets searchFilter) {
		return userService.search(searchText, page, sortField, searchFilter);
	}

	@Override
	protected UserDto find(String rowkey) {
		return userService.findUserByUid(rowkey);
	}

	@Override
	protected String getKey(UserDto entity) {
		return entity.getUid();
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 0;
	}
}
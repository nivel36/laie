package es.nivel36.laie.web.view.user;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;
import jakarta.inject.Inject;

public class UserLazyDataModel extends AbstractLazyDataModel<User> {

	private static final long serialVersionUID = 6698863542667375365L;
	
	@Inject
	private transient UserService userService;

	@Override
	protected SearchResult<User> search(String searchText, Page page, SortField sortField,
			SearchFacets searchFilter) {
		return userService.search(searchText, page, sortField, searchFilter);
	}

	@Override
	protected User find(Long id) {
		return userService.findUserById(id);
	}	
}
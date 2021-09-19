package es.nivel36.laie.view.user;

import java.util.List;

import es.nivel36.laie.core.service.search.SortField;
import es.nivel36.laie.core.view.AbstractLazyDataModel;
import es.nivel36.laie.user.UserDto;
import es.nivel36.laie.user.UserService;

public class UserLazyDataModel extends AbstractLazyDataModel<UserDto> {

	private static final long serialVersionUID = -2776957081115743226L;
	
	private UserService userService;
	
	public UserLazyDataModel( UserService userService) {
		this.userService = userService;
	}

	public List<UserDto> doSearch(final int firstResult, final int maxResults, final SortField sortField,
			final String searchText) {
		return userService.searchByName(firstResult, maxResults, sortField, searchText);
	}
	
	public String getRowKey(UserDto user) {
		return user.getUid();
	}
	
	public UserDto getRowData(String rowKey) {
		return userService.findUserByUid(rowKey);
	}
}
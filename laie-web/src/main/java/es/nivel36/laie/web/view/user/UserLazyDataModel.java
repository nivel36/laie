package es.nivel36.laie.web.view.user;

import java.util.Objects;

import es.nivel36.laie.ejb.core.AbstractIndexedService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;

public class UserLazyDataModel extends AbstractLazyDataModel<User> {

	private static final long serialVersionUID = 1L;

	private transient UserService userService;

	public UserLazyDataModel(final UserService userService) {
		Objects.requireNonNull(userService, "UserService can't be null");
		this.userService = userService;
	}

	@Override
	protected AbstractIndexedService<User> getService() {
		return this.userService;
	}
}
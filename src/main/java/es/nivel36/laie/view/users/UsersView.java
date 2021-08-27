package es.nivel36.laie.view.users;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.core.view.AbstractView;
import es.nivel36.laie.user.UserService;

@Named
@ViewScoped
public class UsersView extends AbstractView {
	
	private static final long serialVersionUID = -3578104135411777477L;

	private static final Logger logger = LoggerFactory.getLogger(UsersView.class);

	private String searchText;

	private UserLazyDataModel users;

	@Inject
	private transient UserService userService;

	public String getSearchText() {
		return this.searchText;
	}

	public UserLazyDataModel getUsers() {
		return this.users;
	}

	@PostConstruct
	public void init() {
		logger.trace("User search init");
		this.users = new UserLazyDataModel(this.userService);
	}

	public void search() {
		logger.debug("Search users action performed");
		this.users.setSearchText(this.searchText);
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}
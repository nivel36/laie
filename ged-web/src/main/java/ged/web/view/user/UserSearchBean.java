package ged.web.view.user;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class UserSearchBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private String searchText;

	private List<User> users;

	@Inject
	private transient UserService userService;

	public void clean() {
		logger.debug("Cleaning search fields");
		this.searchText = null;
		this.search();
	}

	public String getSearchText() {
		return this.searchText;
	}

	public List<User> getUsers() {
		return this.users;
	}

	@PostConstruct
	public void init() {
		logger.trace("Init UserSearchBean");
		this.search();
	}

	public void search() {
		logger.debug("Searching for users");
		this.users = this.userService.search(this.searchText);
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setUsers(final List<User> users) {
		this.users = users;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}
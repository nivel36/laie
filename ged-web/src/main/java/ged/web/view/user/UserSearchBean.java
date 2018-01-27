package ged.web.view.user;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.action.Action;
import ged.ejb.core.action.ActionService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class UserSearchBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private List<Action> actions;

	@Inject
	private ActionService actionService;

	private long numberOfUsersOfflineLastMonth;

	private long numberOfUsersOnlineLastWeek;

	private String searchText;

	private List<User> users;

	@Inject
	private transient UserService userService;

	private List<User> usersOnlineLastWeek;

	public void clean() {
		logger.debug("Cleaning search fields");
		this.searchText = null;
		search();
	}

	public void findUsersOffline() {
		this.users = this.userService.findUsersOfflineLastMonth();
	}

	public void findUsersOnline() {
		this.users = this.userService.findUsersOnlineLastWeek();
	}

	public List<Action> getActions() {
		return this.actions;
	}

	public int getNumberOfUsers() {
		if (this.users == null) {
			return 0;
		}
		return this.users.size();
	}

	public long getNumberOfUsersOfflineLastMonth() {
		return this.numberOfUsersOfflineLastMonth;
	}

	public long getNumberOfUsersOnlineLastWeek() {
		return this.numberOfUsersOnlineLastWeek;
	}

	public String getSearchText() {
		return this.searchText;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public List<User> getUsersOnlineLastWeek() {
		return this.usersOnlineLastWeek;
	}

	@PostConstruct
	public void init() {
		logger.trace("Init UserSearchBean");
		search();
		this.actions = this.actionService.findLastActions();
		this.usersOnlineLastWeek = this.userService.findUsersOnlineLastWeek();
		this.numberOfUsersOfflineLastMonth = this.userService.numberOfUsersOfflineLastMonth();
		this.numberOfUsersOnlineLastWeek = this.userService.numberOfUsersOnlineLastWeek();
	}

	public String newUser() {
		logger.debug("Creating a new user");
		return "userEdit?faces-redirect=true";
	}

	public void search() {
		logger.debug("Searching for users");
		if (this.searchText == null) {
			this.users = this.userService.findAll();
		} else {
			this.users = this.userService.search(this.searchText);
		}
	}

	public void setActionService(final ActionService actionService) {
		this.actionService = actionService;
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
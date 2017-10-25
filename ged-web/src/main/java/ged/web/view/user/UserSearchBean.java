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

@Named
@ViewScoped
public class UserSearchBean extends AbstractUserSearchBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private List<Action> actions;

	@Inject
	private ActionService actionService;

	private int numberOfUsersOfflineLastMonth;

	private int numberOfUsersOnlineLastWeek;

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

	public int getNumberOfUsersOfflineLastMonth() {
		return this.numberOfUsersOfflineLastMonth;
	}
	
	public int getNumberOfUsersOnlineLastWeek() {
		return this.numberOfUsersOnlineLastWeek;
	}
	
	@PostConstruct
	public void init() {
		logger.trace("Init UserSearchBean");
		search();
		this.actions = this.actionService.findAll();
		this.numberOfUsersOfflineLastMonth = this.userService.numberOfUsersOfflineLastMonth();
		this.numberOfUsersOnlineLastWeek = this.userService.numberOfUsersOnlineLastWeek();
	}

	public String newUser() {
		logger.debug("Creating a new user");
		return "userEdit?faces-redirect=true";
	}

	public void setActionService(final ActionService actionService) {
		this.actionService = actionService;
	}
}
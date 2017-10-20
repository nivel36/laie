package ged.web.view.user;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@ViewScoped
public class UserSearchBean extends AbstractUserSearchBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private int numberOfUsersOfflineLastMonth;

	private int numberOfUsersOnlineLastWeek;

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
		this.numberOfUsersOfflineLastMonth = this.userService.findUsersOfflineLastMonth().size();
		this.numberOfUsersOnlineLastWeek = this.userService.findUsersOnlineLastWeek().size();
	}

	public String newUser() {
		logger.debug("Creating a new user");
		return "userEdit?faces-redirect=true";
	}
}
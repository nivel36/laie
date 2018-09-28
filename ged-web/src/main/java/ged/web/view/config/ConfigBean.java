package ged.web.view.config;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
public class ConfigBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -2789492893353263506L;

	private User user;

	@Inject
	private transient UserService userService;

	private void changeSessionUser() {
		this.sessionUser.refresh();
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		this.user = this.sessionUser.get();
		logger.debug("Config user {} init", user.getEmail());
	}

	public void openChangePasswordDialog() {
		this.openDialog("/faces/config/changePasswordDialog");
	}

	public void save() {
		logger.debug("Save user action performed");
		final long userId = this.user.getId();
		if (userId == this.sessionUser.get().getId()) {
			this.changeSessionUser();
		}
		this.user = this.userService.save(this.user);
		this.sessionUser.refresh();
		this.addMessage(FacesMessage.SEVERITY_INFO, "action.save_action_performed", "action.save_action_performed");
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}
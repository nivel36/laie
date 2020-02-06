package ged.web.view.user;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.user.User;

@Named
@ViewScoped
public class AddUserView extends AbstractUserView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private String password;
	
	private String repeatPassword;
	
	private boolean sendWelcomeEmail;

	private User buildNewUser() {
		final User newUser = new User();
		newUser.setLanguage("ES");
		newUser.setRowsPerPage(25);
		newUser.setDateOfJoin(LocalDate.now());
		return newUser;
	}

	private void checkAddPermission() {
		if (!this.sessionUser.isAdmin()) {
			logger.error("User {} hasn't got priviliges to add a new user", this.sessionUser);
			throw new SecurityException();
		}
	}

	public String getPassword() {
		return password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	@PostConstruct
	public void init() {
		logger.trace("New user init");
		this.checkAddPermission();
		this.user = this.buildNewUser();
		this.sendWelcomeEmail = true;
	}

	public boolean isSendWelcomeEmail() {
		return sendWelcomeEmail;
	}

	public String save() {
		logger.debug("Create new user action performed");
		this.user = this.userService.save(this.user);
		return this.userUrl();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public void setSendWelcomeEmail(boolean sendWelcomeEmail) {
		this.sendWelcomeEmail = sendWelcomeEmail;
	}
}

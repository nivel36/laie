package es.nivel36.laie.web.view.user;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.user.DuplicateEmailException;
import es.nivel36.laie.ejb.user.UserDto;

@Named
@ViewScoped
public class AddUserView extends AbstractUserView {

	private static final long serialVersionUID = 5488456059551016365L;

	private static final Logger logger = LoggerFactory.getLogger(AbstractUserView.class);

	private String password;

	private String repeatPassword;

	private boolean sendWelcomeEmail;

	private UserDto buildNewUser() {
		final UserDto newUser = new UserDto();
		newUser.setLanguage("ES");
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
		return this.password;
	}

	public String getRepeatPassword() {
		return this.repeatPassword;
	}

	@PostConstruct
	public void init() {
		logger.trace("New user init");
		this.checkAddPermission();
		this.user = this.buildNewUser();
		this.sendWelcomeEmail = true;
	}

	public boolean isSendWelcomeEmail() {
		return this.sendWelcomeEmail;
	}

	public String save() throws DuplicateEmailException {
		logger.debug("Create new user action performed");
		try {
			this.userService.addUser(this.user);
		} catch (DuplicateEmailException e) {
			//TODO: gestionar excepcion
			throw e;
		}
		return this.userUrl();
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public void setSendWelcomeEmail(final boolean sendWelcomeEmail) {
		this.sendWelcomeEmail = sendWelcomeEmail;
	}
}

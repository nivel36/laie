package es.nivel36.laie.web.view.user;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.user.BadManagerException;
import es.nivel36.laie.ejb.user.DuplicateEmailException;
import es.nivel36.laie.ejb.user.UserDto;

@Named
@ViewScoped
public class AddUserView extends AbstractUserView {

	private static final long serialVersionUID = 2822959833449476143L;

	private static final Logger logger = LoggerFactory.getLogger(AddUserView.class);

	@PostConstruct
	public void init() {
		logger.trace("New user init");
		this.checkAddPermission();
		this.user = this.buildNewUser();
	}

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

	public void save() throws Exception {
		logger.debug("Create new user action performed");
		try {
			final String managerUid = this.manager == null ? null : this.manager.getUid();
			this.user = this.userService.addUser(this.user, managerUid);
			final String userUrl = this.userUrl();
			Faces.redirect(userUrl);
		} catch (DuplicateEmailException e) {
			this.addErrorToField("userForm:email", "user.error.email_exists");
		} catch (BadManagerException e) {
			this.addErrorToField("userForm:manager", "user.error.manager");
		}
	}
}

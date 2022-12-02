package es.nivel36.laie.web.view.user;

import javax.faces.view.ViewScoped;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.user.BadManagerException;
import es.nivel36.laie.ejb.user.DuplicateEmailException;
import es.nivel36.laie.ejb.user.User;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;

@Named
@ViewScoped
public class AddUserView extends AbstractUserView {

	private static final long serialVersionUID = -7291274460311553535L;

	private static final Logger logger = LoggerFactory.getLogger(AddUserView.class);
	
	@PostConstruct
	public void init() {
		logger.trace("New user init");
		this.checkAddPermission();
		this.user = new User();
		this.user.setLanguage(Faces.getLocale().getCountry());
	}

	private void checkAddPermission() {
		if (!this.sessionUser.isAdmin()) {
			logger.error("User {} hasn't got priviliges to add a new user", this.sessionUser);
			throw new SecurityException();
		}
	}

	public void save() {
		logger.debug("Create new user action performed");
		try {
			this.userService.addUser(this.user);
			Faces.redirect(this.viewUserUrl());
		} catch (DuplicateEmailException e) {
			this.addErrorToField("userForm:email", "user.error.email_exists");
		} catch (BadManagerException e) {
			this.addErrorToField("userForm:manager", "user.error.manager");
		}
	}
}

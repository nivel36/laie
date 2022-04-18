package es.nivel36.laie.web.view.user;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.user.BadManagerException;
import es.nivel36.laie.ejb.user.DuplicateEmailException;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class EditUserView extends AbstractUserView {

	private static final long serialVersionUID = -2231097614606592609L;

	private static final Logger logger = LoggerFactory.getLogger(EditUserView.class);
	
	public static final String URL = "/user/edit.xhtml";
	
	@Param
	private User user;

	@PostConstruct
	public void init() {
		if (user == null) {
			throw new IllegalPageStateException();
		}
		this.checkEditPermission();
		logger.trace("User {} edit init", this.user);
	}

	private void checkEditPermission() {
		if (!this.sessionUser.isAdmin()) {
			logger.error("User {} hasn't got priviliges to edit user {}", this.sessionUser.get(), this.user);
			throw new SecurityException();
		}
	}

	public void save() {
		logger.debug("Save user action performed");
		try {
			this.user = this.userService.updateUser(this.user);
			Faces.redirect(this.viewUserUrl());
		} catch (DuplicateEmailException e) {
			this.addErrorToField("userForm:email", "user.error.email_exists");
		} catch (BadManagerException e) {
			this.addErrorToField("userForm:manager", "user.error.manager");
		}
	}
	
	@Override
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}
}

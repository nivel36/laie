package es.nivel36.laie.web.view.user;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.user.DuplicateEmailException;
import es.nivel36.laie.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class EditUserView extends AbstractUserView {

	private static final long serialVersionUID = -7714492306802728830L;

	private static final Logger logger = LoggerFactory.getLogger(EditUserView.class);
	
	protected String uid;
	
	@PostConstruct
	public void init() {
		uid = this.getValueFromGetParameters("uid");
		if(uid == null) {
			throw new IllegalPageStateException();
		}
		user = this.userService.findUserByUid(uid);
		if(user== null) {
			throw new IllegalPageStateException();
		}
		this.checkEditPermission();
		logger.trace("User {} edit init", this.user.getEmail());
	}

	private void checkEditPermission() {
		if (!this.sessionUser.isAdmin()) {
			logger.error("User {} hasn't got priviliges to edit user {}", this.sessionUser.get(), this.user);
			throw new SecurityException();
		}
	}

	public String save() throws DuplicateEmailException {
		logger.debug("Save user action performed");
		try {
			this.userService.updateUser(this.user);
		} catch (DuplicateEmailException e) {
			// TODO: Gestionar excepcion
			throw e;
		}
		return this.userUrl();
	}
}

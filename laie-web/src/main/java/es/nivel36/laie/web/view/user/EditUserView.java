package es.nivel36.laie.web.view.user;

import java.lang.invoke.MethodHandles;

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

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private void checkEditPermission() {
		if (!this.sessionUser.isAdmin()) {
			logger.error("User {} hasn't got priviliges to edit user {}", this.sessionUser.get(), this.user);
			throw new SecurityException();
		}
	}

	private void checkNonNullUser() {
		if (this.user == null) {
			logger.error("User is null");
			throw new IllegalPageStateException();
		}
	}

	@PostConstruct
	public void init() {
		this.checkNonNullUser();
		this.checkEditPermission();
		logger.trace("User {} edit init", this.user.getEmail());
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

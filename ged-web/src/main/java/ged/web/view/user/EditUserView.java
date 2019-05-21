package ged.web.view.user;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class EditUserView extends AbstractUserView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -2187385732087309689L;

	private static final String USER_KEY = "user";

	private void checkEditPermission() {
		if (!this.sessionUser.hasPermissionToEdit(this.user)) {
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
		this.user = this.getValueFromFlash(USER_KEY);
		this.checkNonNullUser();
		this.checkEditPermission();
		logger.trace("User {} edit init", this.user.getEmail());
		this.putValueToFlash(USER_KEY, this.user); // prevent errors if f5/reload is pressed
	}

	public String save() {
		logger.debug("Save user action performed");
		this.checkEditPermission();
		this.user = this.userService.save(this.user);
		return this.userUrl();
	}
}

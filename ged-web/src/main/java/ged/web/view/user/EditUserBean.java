package ged.web.view.user;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.web.core.util.Navigate;
import ged.web.core.util.PageEnum;

@Named
@ViewScoped
public class EditUserBean extends AbstractUserBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -2187385732087309689L;

	@PostConstruct
	public void init() {
		this.user = this.getValueFromFlash("user");
		if (this.user == null) {
			Navigate.to(PageEnum.USER_SEARCH).doGet();
		}
		logger.debug("User {} edit init", this.user.getEmail());
		this.putValueToFlash("user", user); // prevent errors if f5/reload is pressed
		if (!this.sessionUser.hasPermissionToEdit(this.user)) {
			logger.error("User {} hasn't got priviliges to edit user {}", this.sessionUser.get(), this.user);
			throw new SecurityException();
		}
	}

	public String save() {
		logger.debug("Save user action performed");
		if (!this.sessionUser.hasPermissionToEdit(this.user)) {
			logger.error("User {} hasn't got priviliges to edit user {}", this.sessionUser.get(), this.user);
			throw new SecurityException();
		}
		this.user = this.userService.save(this.user);
		return userUrl();
	}
}

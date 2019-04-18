package ged.web.view.config;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.user.Credential;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ChangePasswordBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -7760785874121856319L;

	private String newPassword;

	private String password;

	private String repeatPassword;

	private User user;

	private Credential userCredential;

	@Inject
	private transient UserService userService;

	public String change() {
		logger.debug("Action: change password");

		if (!isValidPassword()) {
			this.addMessage(FacesMessage.SEVERITY_ERROR, "login.error.bad_password", "login.error.bad_password");
			this.facesContext.validationFailed();
			return null;
		}

		if (!inputPasswordsAreEquals()) {
			this.addMessage(FacesMessage.SEVERITY_ERROR, "login.error.password_not_equals",
					"login.error.password_not_equals");
			this.facesContext.validationFailed();
			return null;
		}

		changePassword(newPassword);
		this.sessionUser.refresh();
		return "/config.xhtml?faces-redirect=true";
	}

	private boolean inputPasswordsAreEquals() {
		return this.newPassword.equals(this.repeatPassword);
	}

	private boolean isValidPassword() {
		return this.userCredential.isValid(password);
	}

	private User changePassword(final String newPassword) {
		userCredential.setPassword(newPassword);
		user.setCredential(userCredential);
		return this.userService.save(user);
	}

	public String getNewPassword() {
		return this.newPassword;
	}

	public String getPassword() {
		return this.password;
	}

	public String getRepeatPassword() {
		return this.repeatPassword;
	}

	@PostConstruct
	public void init() {
		this.user = this.sessionUser.get();
		this.userCredential = this.userService.findUserCredential(this.user);
	}

	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}

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
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class ChangePasswordView extends AbstractView {

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
		logger.debug("AbstractAction: change password");
		if (!this.isValidPassword()) {
			this.addMessage(FacesMessage.SEVERITY_ERROR, "login.error.bad_password", "login.error.bad_password");
			this.facesContext.validationFailed();
			return null;
		}
		if (!this.inputPasswordsAreEquals()) {
			this.addMessage(FacesMessage.SEVERITY_ERROR, "login.error.password_not_equals",
					"login.error.password_not_equals");
			this.facesContext.validationFailed();
			return null;
		}
		this.userService.changePassword(this.user, this.newPassword);
		this.sessionUser.refresh();
		return "/config.xhtml?faces-redirect=true";
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
		this.user = this.userService.findUserAndCredential(this.sessionUser.get().getEmail());
		this.userCredential = this.user.getCredential();
	}

	private boolean inputPasswordsAreEquals() {
		return this.newPassword.equals(this.repeatPassword);
	}

	private boolean isValidPassword() {
		return this.userCredential.isValid(this.password);
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

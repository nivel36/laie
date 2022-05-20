package es.nivel36.laie.web.view.config;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.login.Credential;

@Named
@ViewScoped
public class ChangePasswordView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private String newPassword;

	private String password;

	private String repeatPassword;

	private User user;

	private Credential userCredential;

	@Inject
	private transient UserService userService;

	public void change() {
		logger.debug("Change password for user {} action performed", this.user);
		if (!this.isValidPassword()) {
			this.addMessage(FacesMessage.SEVERITY_ERROR, "login.error.bad_password", "login.error.bad_password");
			this.facesContext.validationFailed();
			return;
		}
		if (!this.inputPasswordsAreEquals()) {
			this.addMessage(FacesMessage.SEVERITY_ERROR, "login.error.password_not_equals",
					"login.error.password_not_equals");
			this.facesContext.validationFailed();
			return;
		}
		this.userService.changePassword(this.user.getEmail(), this.newPassword, null);
		this.sessionUser.refresh();
		Faces.redirect(ConfigView.URL);
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
		this.userCredential = this.userService.findCredential(this.user.getEmail());
		logger.debug("Change password for user {} init", this.user);
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

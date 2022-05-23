package es.nivel36.laie.web.view.config;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.login.Account;
import es.nivel36.login.AccountService;

@Named
@ViewScoped
public class ChangePasswordView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(ChangePasswordView.class);

	private static final long serialVersionUID = 8828033864577618433L;

	private String newPassword;

	private String password;

	private String repeatPassword;

	private User user;

	private Account userAccount;

	@Inject
	private transient AccountService accountService;

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
		this.accountService.changePassword(this.user.getEmail(), this.password, this.newPassword);
		sessionUser.logout();
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
		this.userAccount = this.accountService.findAccount(this.user.getEmail());
		logger.debug("Change password for user {} init", this.user);
	}

	private boolean inputPasswordsAreEquals() {
		return this.newPassword.equals(this.repeatPassword);
	}

	private boolean isValidPassword() {
		return this.userAccount.isValid(this.password);
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

	public void setAccountService(final AccountService accountService) {
		this.accountService = accountService;
	}
}

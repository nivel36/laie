package ged.web.view.config;

import java.lang.invoke.MethodHandles;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.security.CriptoUtil;
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

	public String change() throws NoSuchAlgorithmException {
		logger.debug("Action: change password");
		final char[] output = CriptoUtil.hashBase64Password(this.password, this.userCredential.getSalt());
		final User user = this.sessionUser.get();
		if (CriptoUtil.passwordMatch(this.userCredential.getPassword(), output)) {
			if (this.newPassword.equals(this.repeatPassword)) {
				changePassword(user);
				this.sessionUser.refresh();
				// Clearing the view bean of the main page because we need to
				// reload the user from database
				this.facesContext.getViewRoot().getViewMap().clear();
				this.addMessage(FacesMessage.SEVERITY_INFO, "action.save_action_performed",
						"action.save_action_performed");
			} else {
				this.addMessage(FacesMessage.SEVERITY_ERROR, "login.error.password_not_equals",
						"login.error.password_not_equals");
				this.facesContext.validationFailed();
				return null;
			}
		} else {
			this.addMessage(FacesMessage.SEVERITY_ERROR, "login.error.bad_password", "login.error.bad_password");
			this.facesContext.validationFailed();
			return null;
		}
		return "/config.xhtml?faces-redirect=true";
	}

	private User changePassword(final User user) throws NoSuchAlgorithmException {
		final char[] hash = CriptoUtil.hashBase64Password(this.newPassword, this.userCredential.getSalt());
		Objects.requireNonNull(hash);
		user.getCredential().setPassword(hash);
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

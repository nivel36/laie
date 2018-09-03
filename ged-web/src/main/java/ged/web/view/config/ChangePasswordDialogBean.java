package ged.web.view.config;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.util.Message;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class ChangePasswordDialogBean extends AbstractDialogBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -7760785874121856319L;

	private String newPassword;

	private transient UIComponent newPasswordComponent;

	private String password;

	private transient UIComponent passwordComponent;

	private String repeatPassword;

	@Inject
	private transient UserService userService;

	public void change() {
		logger.debug("Change password action performed");
		final String output = this.hashPassword(this.password);
		final User user = this.sessionBean.get();
		if (String.valueOf(user.getPassword()).equals(output)) {
			if (this.newPassword.equals(this.repeatPassword)) {
				this.sessionBean.setUser(this.changePassword(user));
				this.closeDialog();
				// Clearing the view bean of the main page because we need to
				// reload the user from database
				this.facesContext.getViewRoot().getViewMap().clear();
				this.addMessage(FacesMessage.SEVERITY_INFO, "action.save_action_performed", "action.save_action_performed");
			}
			else {
				this.addErrorToField(this.newPasswordComponent, "login.error.password_not_equals");
			}
		}
		else {
			this.addErrorToField(this.passwordComponent, "login.error.bad_password");
		}
	}

	private User changePassword(final User user) {
		final String hash = this.hashPassword(this.newPassword);
		Objects.requireNonNull(hash);
		user.setPassword(hash.toCharArray());
		return this.userService.save(user);
	}

	public String getNewPassword() {
		return this.newPassword;
	}

	public UIComponent getNewPasswordComponent() {
		return this.newPasswordComponent;
	}

	public String getPassword() {
		return this.password;
	}

	public UIComponent getPasswordComponent() {
		return this.passwordComponent;
	}

	public String getRepeatPassword() {
		return this.repeatPassword;
	}

	private String hashPassword(final String plainPassword) {
		String output = null;
		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(plainPassword.getBytes("UTF-8"));
			final byte[] digest = md.digest();
			output = DatatypeConverter.printBase64Binary(digest);
		}
		catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			Message.addError("error.unnexpected_error", "error.unnexpected_error");
			logger.error("Can't find hash algorithm", ex);
		}
		return output;
	}

	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}

	public void setNewPasswordComponent(final UIComponent newPasswordComponent) {
		this.newPasswordComponent = newPasswordComponent;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setPasswordComponent(final UIComponent passwordComponent) {
		this.passwordComponent = passwordComponent;
	}

	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}

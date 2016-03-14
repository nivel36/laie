package ged.web.view.user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.DatatypeConverter;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class ChangePasswordPopupBean extends AbstractDialogBean {

	private static final long serialVersionUID = -7760785874121856319L;

	private String newPassword;

	private UIComponent newPasswordComponent;

	private String password;

	private UIComponent passwordComponent;

	private String repeatPassword;

	@Inject
	private UserService userService;

	public void change() {
		final String output = hashPassword(this.password);
		User user = this.sessionBean.getUser();
		if (user.getPassword().equals(output)) {
			if (this.newPassword.equals(this.repeatPassword)) {
				final String hash = hashPassword(this.newPassword);
				user.setPassword(hash);
				user = this.userService.updateUser(user);
				this.sessionBean.setUser(user);
				hide();
			} else {
				addErrorToField(this.newPasswordComponent, "login.error.password_not_equals");
			}
		} else {
			addErrorToField(this.passwordComponent, "login.error.bad_password");
		}
	}

	public void changePassword() {
		this.rendered = false;
	}

	@Override
	public void clear() {
		this.password = null;
		this.repeatPassword = null;
		this.newPassword = null;
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
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			// TODO: Faces Message
			ex.printStackTrace();
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
}

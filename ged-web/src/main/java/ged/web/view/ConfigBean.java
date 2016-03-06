package ged.web.view;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.DatatypeConverter;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ConfigBean extends AbstractBean {

	private static final long serialVersionUID = -2789492893353263506L;

	private String newPassword;
	private UIComponent newPasswordComponent;

	private String password;

	private UIComponent passwordComponent;

	private String repeatPassword;

	private User user;

	@Inject
	private UserService userService;

	public String cancel() {
		return null;
	}

	public void change() {
		final String output = hashPassword(this.password);
		if (this.user.getPassword().equals(output)) {
			if (this.newPassword.equals(this.repeatPassword)) {
				final String hash = hashPassword(this.newPassword);
				this.user.setPassword(hash);
			} else {
				addErrorToField(this.passwordComponent, "login.error.bad_password");
			}
		} else {
			addErrorToField(this.newPasswordComponent, "login.error.password_not_equals");
		}
		cleanPasswordFields();
	}

	private void cleanPasswordFields() {
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

	public User getUser() {
		return this.user;
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

	@PostConstruct
	public void init() {
		this.user = this.sessionBean.getUser();
	}

	public void save() {
		this.user = this.userService.updateUser(this.user);
		this.sessionBean.setUser(this.user);
		this.sessionBean.setLocale(new Locale(this.user.getLanguage()));
		this.sessionBean.setRowsPerPage(this.user.getRowsPerPage());
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

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

}

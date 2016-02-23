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

import ged.ejb.core.user.User;
import ged.ejb.core.user.UserService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ConfigBean extends AbstractBean {

	private static final long serialVersionUID = -2789492893353263506L;

	private UIComponent passwordComponent;
	private UIComponent newPasswordComponent;

	public UIComponent getPasswordComponent() {
		return passwordComponent;
	}

	public void setPasswordComponent(UIComponent passwordComponent) {
		this.passwordComponent = passwordComponent;
	}

	public UIComponent getNewPasswordComponent() {
		return newPasswordComponent;
	}

	public void setNewPasswordComponent(UIComponent newPasswordComponent) {
		this.newPasswordComponent = newPasswordComponent;
	}

	private User user;

	private String password;

	private String newPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private String repeatPassword;

	@Inject
	private UserService userService;

	@PostConstruct
	public void init() {
		user = sessionBean.getUser();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void save() {
		user = userService.update(user);
		sessionBean.setUser(user);
		sessionBean.setLocale(new Locale(user.getLanguage()));
		sessionBean.setRowsPerPage(user.getRowsPerPage());
	}

	public void change() {
		String output = hashPassword(password);
		if (user.getPassword().equals(output)) {
			if (newPassword.equals(repeatPassword)) {
				String hash = hashPassword(newPassword);
				user.setPassword(hash);
			} else {
				addErrorToField(passwordComponent, "login.error.bad_password");
			}
		} else {
			addErrorToField(newPasswordComponent, "login.error.password_not_equals");
		}
		cleanPasswordFields();
	}

	private void cleanPasswordFields() {
		password = null;
		repeatPassword = null;
		newPassword = null;
	}

	private String hashPassword(String plainPassword) {
		String output = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(plainPassword.getBytes("UTF-8"));
			byte[] digest = md.digest();
			output = DatatypeConverter.printBase64Binary(digest);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			// TODO: Faces Message
			ex.printStackTrace();
		}
		return output;
	}

	public String cancel() {
		return null;
	}

}

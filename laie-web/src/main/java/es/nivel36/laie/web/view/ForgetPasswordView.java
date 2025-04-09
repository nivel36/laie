package es.nivel36.laie.web.view;

import java.io.Serializable;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ForgetPasswordView implements Serializable {

	private static final long serialVersionUID = 7256830981193689547L;
	private static final Logger logger = LoggerFactory.getLogger(IndexView.class);
	public static final String URL = "/forgetPassword.xhtml";

	private @Inject UserService userService;

	private boolean showBadUsername;
	private boolean showEmailSended;
	private String email;

	public void requestChangePassword() {
		logger.debug("Password change request for user {}", email);
		final User user = userService.findUserByEmail(email);
		if (user == null) {
			showBadUsername = true;
		} else {
			showEmailSended = true;
		}
	}

	public boolean isShowBadUsername() {
		return showBadUsername;
	}

	public boolean isShowEmailSended() {
		return showEmailSended;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserService(final UserService userService) {
		this.userService = Objects.requireNonNull(userService);
	}
}

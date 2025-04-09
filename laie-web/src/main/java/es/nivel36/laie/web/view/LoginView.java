package es.nivel36.laie.web.view;

import java.util.Objects;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.login.LoginService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

/**
 * View responsible for handling user login.
 * 
 * <p>This view manages authentication and redirects authenticated users to the main page.</p>
 */
@Named
@RequestScoped
public class LoginView extends AbstractView {

	private static final long serialVersionUID = 4112471805164466458L;
	private static final Logger logger = LoggerFactory.getLogger(LoginView.class);
	public static final String URL = "/login.xhtml";

	private transient @Inject LoginService loginService;

	private String username;
	private transient String password;
	private String forwardURL;

	/**
	 * Initialization method.
	 * 
	 * Checks if a user is already authenticated and redirects them to the index page if needed.
	 */
	@PostConstruct
	public void init() {
		logger.trace("Login view init");
		String remoteUser = externalContext.getRemoteUser();

		if (remoteUser != null) {
			logger.warn("User {} already logged in", remoteUser);
			gotoIndex();
			return;
		}

		if (forwardURL == null) {
			HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
			String requestURI = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
			String queryString = (String) request.getAttribute(RequestDispatcher.FORWARD_QUERY_STRING);
			forwardURL = (queryString == null) ? requestURI : requestURI + "?" + queryString;
		}
	}

	/**
	 * Handles the login process.
	 * 
	 * Authenticates the user using the login service and stores the user role in session.
	 */
	public void login() {
		if (username == null || password == null) {
			loginError();
			return;
		}

		logger.debug("User {} attempting login", username);
		AuthenticationStatus status = loginService.login(username, password);

		String role = externalContext.isUserInRole("laie.admin") ? "laie.admin" : "laie.user";

		switch (status) {
			case SEND_CONTINUE -> {
				sessionUser.load(username, role);
				facesContext.responseComplete();
			}
			case SEND_FAILURE -> {
				logger.warn("Login failed for user {}", username);
				loginError();
			}
			case SUCCESS -> {
				sessionUser.load(username, role);
				gotoIndex();
			}
			default -> {
				logger.error("Unexpected authentication status: {}", status);
				loginError();
			}
		}
	}

	/**
	 * Redirects the user to the index page or to the original requested URL.
	 */
	private void gotoIndex() {
		Faces.redirect(forwardURL != null ? forwardURL : IndexView.URL);
	}

	/**
	 * Adds an error message to the UI indicating login failure.
	 */
	private void loginError() {
		addMessage(FacesMessage.SEVERITY_ERROR, "auth.message.error", "auth.message.error");
	}

	/**
	 * Returns the requested forward URL.
	 * 
	 * @return forward URL
	 */
	public String getForwardURL() {
		return forwardURL;
	}

	/**
	 * Sets the forward URL to redirect after login.
	 * 
	 * @param forwardURL the URL to redirect to
	 */
	public void setForwardURL(String forwardURL) {
		this.forwardURL = forwardURL;
	}

	/**
	 * Returns the username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 * 
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns the user password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user password.
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets the login service instance.
	 * 
	 * @param loginService the login service to use
	 * @throws NullPointerException if loginService is null
	 */
	public void setLoginService(LoginService loginService) {
		this.loginService = Objects.requireNonNull(loginService);
	}
}

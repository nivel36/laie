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
 * <p>
 * Vista para la pantalla de login. Encargada de gestionar la autenticación de
 * usuarios y redirección a la página principal si ya está autenticado.
 * </p>
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
	 * Método inicializador. Verifica si el usuario ya está autenticado y redirige a
	 * la página principal si es necesario.
	 */
	@PostConstruct
	public void init() {
		logger.trace("Login init");
		final String remoteUser = this.externalContext.getRemoteUser();
		if (remoteUser != null) {
			logger.warn("User {} alredy logged", remoteUser);
			this.gotoIndex();
		}
		if (forwardURL == null) {
			final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
			final String requestURI = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
			final String queryString = (String) request.getAttribute(RequestDispatcher.FORWARD_QUERY_STRING);
			forwardURL = (queryString == null) ? requestURI : (requestURI + "?" + queryString);
		}
	}

	/**
	 * Realiza el inicio de sesión del usuario.
	 * 
	 * Autentica al usuario basándose en su nombre de usuario y contraseña, y carga
	 * su rol en la sesión.
	 */
	public void login() {
		if (this.username == null || this.password == null) {
			loginError();
			return;
		}
		logger.debug("User {} login", this.username);
		final AuthenticationStatus status = this.loginService.login(this.username, this.password);
		String role;
		if (this.externalContext.isUserInRole("laie.admin")) {
			role = "laie.admin";
		} else {
			role = "laie.user";
		}
		if (status == AuthenticationStatus.SEND_CONTINUE) {
			this.sessionUser.load(username, role);
			this.facesContext.responseComplete();
		} else if (AuthenticationStatus.SEND_FAILURE.equals(status)) {
			logger.warn("User {} error: bad username or password", username);
			loginError();
		} else if (AuthenticationStatus.SUCCESS.equals(status)) {
			this.sessionUser.load(username, role);
			gotoIndex();
		}
	}

	public String getForwardURL() {
		return forwardURL;
	}

	public void setForwardURL(String forwardURL) {
		this.forwardURL = forwardURL;
	}

	private void loginError() {
		this.addMessage(FacesMessage.SEVERITY_ERROR, "auth.message.error", "auth.message.error");
	}

	private void gotoIndex() {
		if (forwardURL == null) {
			Faces.redirect(IndexView.URL);
		} else {
			Faces.redirect(forwardURL);
		}
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	/**
	 * Establece el nombre de usuario.
	 * 
	 * @param username Nombre de usuario.
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * Establece la contraseña del usuario.
	 * 
	 * @param password Contraseña del usuario.
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Establece el servicio de inicio de sesión.
	 * 
	 * @param loginService El servicio de inicio de sesión.
	 * @throws NullPointerException si loginService es null.
	 */
	public void setLoginService(final LoginService loginService) {
		Objects.requireNonNull(loginService);
		this.loginService = loginService;
	}
}

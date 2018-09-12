package ged.web.view;

import static javax.security.enterprise.AuthenticationStatus.SEND_CONTINUE;
import static javax.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import static org.omnifaces.util.Faces.getRequest;
import static org.omnifaces.util.Faces.getResponse;
import static org.omnifaces.util.Faces.responseComplete;
import static org.omnifaces.util.Faces.validationFailed;
import static org.omnifaces.util.Messages.addGlobalError;

import java.lang.invoke.MethodHandles;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.LoginService;
import ged.web.core.util.Navigate;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractBean;

@Named
@RequestScoped
public class LoginBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 8364578958730650005L;

	@Inject
	private transient ExternalContext externalContext;

	private Locale locale;

	@Inject
	private LoginService loginService;

	private transient String password;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	private SecurityContext securityContext;

	private String username;

	private void authenticate(final AuthenticationParameters parameters) {
		final AuthenticationStatus status = securityContext.authenticate(getRequest(), getResponse(), parameters);

		if (status == SEND_FAILURE) {
			addGlobalError("auth.message.error.failure");
			validationFailed();
		} else if (status == SEND_CONTINUE) {
			responseComplete(); // Prevent JSF from rendering a response so authentication mechanism can
								// continue.
		}
		Navigate.to(PageEnum.INDEX).doPost();
	}

	public Locale getLocale() {
		return this.locale;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	@PostConstruct
	public void init() {
		logger.debug("LOGIN Bean init");
		final String username = externalContext.getRemoteUser();
		if (username != null) {
			logger.warn("User {} alredy logged", username);
			Navigate.to(PageEnum.INDEX);
		}
		this.locale = this.facesContext.getApplication().getDefaultLocale();
	}

	public void login() {
		logger.debug("Username {} login", this.username);
		loginService.saveLastConnection(this.username);
		authenticate(
				withParams().credential(new UsernamePasswordCredential(username, password)).newAuthentication(true));
	}

	public String logout() {
		logger.debug("Username {} logout", this.username);
		final ExternalContext externalContext = this.facesContext.getExternalContext();
		final HttpSession session = (HttpSession) externalContext.getSession(true);
		session.invalidate();
		return "/login?faces-redirect=true";
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public void setLoginService(final LoginService loginService) {
		this.loginService = loginService;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setUsername(final String username) {
		this.username = username;
	}
}
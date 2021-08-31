package es.nivel36.laie.view;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.NavigationHandler;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;

import org.omnifaces.util.Faces;

import es.nivel36.laie.core.view.AbstractView;
import es.nivel36.laie.login.LoginService;

@Named
@RequestScoped
public class LogoutView extends AbstractView  {

	private static final long serialVersionUID = 4381747115656366818L;

	@Inject
	private LoginService loginService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void logout() throws ServletException, IOException {
		final String email = this.sessionUser.get().getEmail();
		loginService.logout(email);
		Faces.logout();
		gotoLogin();
	}

	private void gotoLogin() {
		final NavigationHandler nav = this.facesContext.getApplication().getNavigationHandler();
		final String indexUrl = "login";
		nav.handleNavigation(this.facesContext, null, indexUrl);
		this.facesContext.renderResponse();
	}
}

package ged.web.core.security;

import javax.inject.Inject;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import ged.ejb.core.SessionUsers;
import ged.ejb.core.security.GedSecurityContext;
import ged.ejb.user.User;

@WebListener
public class LogoutListener implements HttpSessionListener {

	@Inject
	private GedSecurityContext gedSecurityContext;

	@Inject
	private SessionUsers sessionUsers;

	@Override
	public void sessionCreated(final HttpSessionEvent event) {
		// Nothing to do
	}

	@Override
	public void sessionDestroyed(final HttpSessionEvent event) {
		final User user = this.gedSecurityContext.getLoggedUser();
		this.sessionUsers.logout(user.getEmail());
	}

	public void setSecurityContext(final GedSecurityContext gedSecurityContext) {
		this.gedSecurityContext = gedSecurityContext;
	}

	public void setSessionUsers(final SessionUsers sessionUsers) {
		this.sessionUsers = sessionUsers;
	}

}
package es.nivel36.laie.login;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.laie.user.UserDto;
import es.nivel36.laie.user.UserService;

@Stateless
public class LaieSecurityContext {

	@Resource
	protected SessionContext sessionContext;

	@Inject
	private UserService userService;

	public UserDto getLoggedUser() {
		final String username = this.sessionContext.getCallerPrincipal().getName();
		return this.getUserByUsername(username);
	}

	private UserDto getUserByUsername(final String username) {
		return this.userService.findUserByEmail(username);
	}

	public String getUsername() {
		return this.sessionContext.getCallerPrincipal().getName();
	}

	public void setSessionContext(final SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}
}

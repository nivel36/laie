package es.nivel36.laie.login;

import javax.security.enterprise.CallerPrincipal;

import es.nivel36.laie.user.User;

public class LaieCallerPrincipal extends CallerPrincipal {

	private final User user;

	public LaieCallerPrincipal(final User user) {
		super(user.getEmail());
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}
}

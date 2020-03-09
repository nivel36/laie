package ged.ejb.core.security;

import javax.security.enterprise.CallerPrincipal;

import ged.ejb.user.User;

public class GedCallerPrincipal extends CallerPrincipal {

	private final User user;

	public GedCallerPrincipal(final User user) {
		super(user.getEmail());
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}
}

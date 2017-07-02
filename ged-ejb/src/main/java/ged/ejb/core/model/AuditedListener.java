package ged.ejb.core.model;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ged.ejb.user.User;
import ged.ejb.user.UserService;

public class AuditedListener {

	@Resource
	private EJBContext ejbContext;

	@Inject
	private UserService userService;

	private User getUser(final String username) {
		return this.userService.findUserByUsername(username);
	}

	private String getUsername() {
		return this.ejbContext.getCallerPrincipal().getName();
	}

	@PrePersist
	public void prePersist(final AbstractAuditedEntity entity) {
		entity.setUser(getUser(getUsername()));
	}

	@PreUpdate
	public void preUpdate(final AbstractAuditedEntity entity) {
		entity.setUser(getUser(getUsername()));
	}
}

package es.nivel36.laie.ejb.core.model;

import es.nivel36.laie.ejb.user.User;

public interface Auditable {

	User getUser();

	void setUser(User user);
}
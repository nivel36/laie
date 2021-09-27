package es.nivel36.laie.ejb.core.model;

import es.nivel36.laie.ejb.user.User;

public interface Ownerable {

	User getOwner();

	void setOwner(User user);
}

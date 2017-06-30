package ged.ejb.core.model;

import ged.ejb.user.User;

public interface Auditable extends Identificable {

	/**
	 * El no hace autoboxing así que Boolean es un objeto que requiere un get en
	 * lugar de un is.
	 *
	 * @return
	 */
	Boolean getDeleted();

	User getUser();

	Boolean isDeleted();

	void setDeleted(Boolean deleted);

	void setUser(User user);
}
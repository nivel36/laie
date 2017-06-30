package ged.ejb.core.model;

import ged.ejb.user.User;

public interface Auditable {

	User getUser();

	void setUser(User user);
}
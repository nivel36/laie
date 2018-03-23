package ged.ejb.core.model;

import ged.ejb.user.User;

public interface Ownerable {

	User getOwner();

	void setOwner(User user);
}

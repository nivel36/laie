package ged.web.view.user;

import java.util.List;

import ged.ejb.core.user.User;

public class UsersIndexBean {
	
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}

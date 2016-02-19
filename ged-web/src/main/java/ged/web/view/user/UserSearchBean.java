package ged.web.view.user;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.user.User;
import ged.ejb.core.user.UserService;
import ged.ejb.core.util.Log;
import ged.web.core.view.AbstractSearchBean;

@Named
@ViewScoped
public class UserSearchBean extends AbstractSearchBean<User> {

	private static final long serialVersionUID = 2434819723782902618L;

	private String email;

	private String name;

	private String surename;

	@Inject
	private UserService userService;

	public void clean() {
		email = null;
		surename = null;
		name = null;
		search();
	}

	public String edit(User user) {
		flash.put("user", user);
		return "userEdit?faces-redirect=true";
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getSurename() {
		return surename;
	}

	public String newUser() {
		return "userEdit?faces-redirect=true";
	}

	public void remove(User user) {
		userService.delete(user);
		search();
	}

	@Log
	@Override
	public void search() {
		logger.fine("Searching for Users");
		if (name != null || surename != null) {
			entities = userService.findUsers(name, surename);
		} else {
			entities = userService.findAll();
		}
		trimList();
		setPaginationSize();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurename(String surename) {
		this.surename = surename;
	}
}

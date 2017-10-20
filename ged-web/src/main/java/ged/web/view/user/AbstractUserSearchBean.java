package ged.web.view.user;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractPageBean;

abstract class AbstractUserSearchBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 3522778618602713805L;

	private String name;

	private String surename;

	protected List<User> users;

	@Inject
	protected transient UserService userService;

	public void clean() {
		logger.debug("Cleaning search fields");
		cleanSearchFields();
		search();
	}

	protected void cleanSearchFields() {
		this.surename = null;
		this.name = null;
	}

	public String getName() {
		return this.name;
	}

	public String getSurename() {
		return this.surename;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void search() {
		logger.debug("Searching for users");
		if (this.name == null && this.surename == null) {
			this.users = this.userService.findAll();
		} else {
			this.users = this.userService.searchByNameAndSurename(this.name, this.surename);
		}
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setSurename(final String surename) {
		this.surename = surename;
	}

	public void setUsers(final List<User> users) {
		this.users = users;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}

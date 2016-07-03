package ged.web.view.user;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.User;
import ged.ejb.user.service.UserService;
import ged.web.core.view.AbstractPageBean;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class UserSearchBean extends AbstractPageBean {

	private static final long serialVersionUID = 2434819723782902618L;

	@Inject
	protected transient Logger logger;

	private String name;

	private Paginator<User> paginator;

	private String surename;

	@Inject
	private transient UserService userService;

	public void clean() {
		this.logger.log(Level.FINE, "Cleaning search fields");
		cleanSearchFields();
		search();
	}

	private void cleanSearchFields() {
		this.surename = null;
		this.name = null;
	}

	public void deleteUser(final User user) {
		this.logger.log(Level.FINE, "Deleting an user");

		this.userService.delete(user);
		search();
	}

	public String editUser(final User user) {
		this.logger.log(Level.FINE, "Editing an user");
		this.flash.put("user", user);
		return "userEdit?faces-redirect=true";
	}

	public String getName() {
		return this.name;
	}

	public Paginator<User> getPaginator() {
		return this.paginator;
	}

	public String getSurename() {
		return this.surename;
	}

	@PostConstruct
	public void init() {
		this.logger.log(Level.FINER, "Init UserSearchBean");
		this.paginator = new Paginator<User>(this.sessionBean.getRowsPerPage());
		search();
	}

	public String newUser() {
		this.logger.log(Level.FINE, "Creating a new user");
		return "userEdit?faces-redirect=true";
	}

	public void search() {
		this.logger.fine("Searching for users");
		final List<User> users = this.userService.fullSearch(this.name, this.surename, null);
		this.paginator.setEntities(users);
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPaginator(final Paginator<User> paginator) {
		this.paginator = paginator;
	}

	public void setSurename(final String surename) {
		this.surename = surename;
	}
}

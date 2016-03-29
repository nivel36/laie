package ged.web.view.user;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.util.Log;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractBean;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class UserSearchBean extends AbstractBean {

	private static final long serialVersionUID = 2434819723782902618L;

	private String email;

	private String name;

	private Paginator<User> paginator;

	private String surename;

	@Inject
	private UserService userService;

	public void clean() {
		this.email = null;
		this.surename = null;
		this.name = null;
		search();
	}

	public String edit(final User user) {
		this.flash.put("user", user);
		return "userEdit?faces-redirect=true";
	}

	public String getEmail() {
		return this.email;
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
		this.paginator = new Paginator<User>(this.sessionBean.getRowsPerPage());
		search();
	}

	public String newUser() {
		return "userEdit?faces-redirect=true";
	}

	public void remove(final User user) {
		this.userService.deleteUser(user);
		search();
	}

	@Log
	public void search() {
		this.logger.fine("Searching for Users");
		if ((this.name != null) || (this.surename != null)) {
			this.paginator.setEntities(this.userService.fullSearch(this.name));
		} else {
			this.paginator.setEntities(this.userService.findAll());
		}
		this.paginator.trimList();
		this.paginator.setPaginationSize();
	}

	public void setEmail(final String email) {
		this.email = email;
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

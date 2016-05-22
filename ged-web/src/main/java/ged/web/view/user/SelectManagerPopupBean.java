package ged.web.view.user;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractPageBean;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class SelectManagerPopupBean extends AbstractPageBean {

	private static final long serialVersionUID = 9150785979243375541L;

	private String email;

	@Inject
	protected transient Logger logger;

	private String name;

	private Paginator<User> paginator;

	protected boolean rendered = false;

	private String surename;

	@Inject
	private transient UserService userService;

	public void cancelPopup() {
		this.rendered = false;
	}

	public void cleanPopup() {
		clearPopupFields();
		search();
	}

	private void clearPopupFields() {
		this.email = null;
		this.name = null;
		this.surename = null;
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

	public void hide() {
		clearPopupFields();
		this.rendered = false;
	}

	@PostConstruct
	public void init() {
		this.paginator = new Paginator<User>();
		this.paginator.setRowsPerPage(this.sessionBean.getUser().getRowsPerPage());
	}

	public boolean isRendered() {
		return this.rendered;
	}

	public void open() {
		this.rendered = true;
	}

	public void search() {
		this.logger.fine("Searching for Users");
		this.logger.fine("Searching for users");
		if ((this.name == null) && (this.surename == null)) {
			this.paginator.setEntities(this.userService.findAll());
		} else {
			this.paginator.setEntities(this.userService.fullSearch(this.name, this.surename));
		}
		clearPopupFields();
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setRendered(final boolean rendered) {
		this.rendered = rendered;
	}

	public void setSurename(final String surename) {
		this.surename = surename;
	}

	public void show() {
		this.rendered = true;
		search();
	}
}

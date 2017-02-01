package ged.web.view.user;

import java.util.List;
import java.util.Objects;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractPageBean;
import ged.web.core.view.Paginator;

abstract class AbstractUserSearchBean extends AbstractPageBean {

	private final transient static Logger logger = LoggerFactory.getLogger(AbstractPageBean.class.getName());

	private static final long serialVersionUID = 3522778618602713805L;

	private String name;

	protected Paginator<User> paginator;

	private String surename;

	protected final transient UserService userService;

	@Inject
	public AbstractUserSearchBean(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	public void clean() {
		logger.debug( "Cleaning search fields");
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

	public Paginator<User> getPaginator() {
		return this.paginator;
	}

	public String getSurename() {
		return this.surename;
	}

	@PostConstruct
	public void init() {
		logger.trace( "Init UserSearchBean");
		this.paginator = new Paginator<>(this.sessionBean.getRowsPerPage());
		search();
	}

	public void search() {
		logger.debug("Searching for users");
		final List<User> users;
		if (this.name == null && this.surename == null) {
			users = this.userService.findAll();
		} else {
			users = this.userService.searchByNameAndSurename(this.name, this.surename);
		}
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

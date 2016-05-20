package ged.web.view.user;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractPageBean;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class UserSearchBean extends AbstractPageBean {

	private static final long serialVersionUID = 2434819723782902618L;

	private String email;

	private String name;

	private Paginator<User> paginator;

	private String surename;

	@Inject
	private transient UserService userService;

	public void clean() {
		cleanSearchFields();
		search();
	}

	private void cleanSearchFields() {
		this.email = null;
		this.surename = null;
		this.name = null;
	}

	public void deleteUser(final User user) {
		this.userService.deleteUser(user);
		search();
	}

	public String editUser(final User user) {
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

	public void search() {
		this.logger.fine("Searching for Users");
		if ((this.name == null) && (this.surename == null)) {
			this.paginator.setEntities(this.userService.findAll());
		} else {
			this.paginator.setEntities(this.userService.fullSearch(this.name, this.surename));
		}
		cleanSearchFields();
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

	public void validateSearchField(final FacesContext context, final UIComponent component, final Object value)
			throws ValidatorException {
		final String searchValue = (String) value;
		if ((searchValue != null) && (searchValue.length() < 3)) {
			final String translatedMessage = translate("error.search.camp_too_short");
			final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, translatedMessage,
					translatedMessage);
			throw new ValidatorException(message);
		}
	}
}

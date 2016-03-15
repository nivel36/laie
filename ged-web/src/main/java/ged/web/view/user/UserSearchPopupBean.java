package ged.web.view.user;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractDialogBean;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class UserSearchPopupBean extends AbstractDialogBean {

	private static final long serialVersionUID = -2701760708389112615L;

	private String email;

	private String inputId;

	private String name;

	private Paginator<User> paginator;

	private String surename;

	@Inject
	private UserService userService;

	public void cancel() {
		this.rendered = false;
	}

	public void clean() {
		this.email = null;
		this.surename = null;
		this.name = null;
		search();
	}

	@Override
	public void clear() {
		this.email = null;
		this.name = null;
		this.surename = null;
	}

	private <C extends UIComponent> UIComponent findChildrenByName(final UIComponent parent) {
		UIComponent aux = null;
		for (final UIComponent component : parent.getChildren()) {
			if (component.getId().equals(this.inputId)) {
				return component;
			}
			aux = findChildrenByName(component);
			if (aux != null) {
				return aux;
			}
		}
		return null;
	}

	public String getEmail() {
		return this.email;
	}

	public String getInputId() {
		return this.inputId;
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

	@Override
	public boolean isRendered() {
		return this.rendered;
	}

	public void open() {
		this.rendered = true;
	}

	public void search() {
		this.logger.fine("Searching for Users");
		if ((this.name != null) || (this.surename != null)) {
			this.paginator.setEntities(this.userService.findUsers(this.name, this.surename));
		} else {
			this.paginator.setEntities(this.userService.findAll());
		}
		this.paginator.trimList();
		this.paginator.setPaginationSize();
	}

	public void select(final User user) {
		UIComponent foundComponent = null;
		foundComponent = findChildrenByName(this.facesContext.getViewRoot());
		((UIInput) foundComponent).setValue(user);
		this.rendered = false;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setInputId(final String inputId) {
		this.inputId = inputId;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public void setRendered(final boolean rendered) {
		this.rendered = rendered;
	}

	public void setSurename(final String surename) {
		this.surename = surename;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}

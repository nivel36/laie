package ged.web.view.user;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.user.User;
import ged.ejb.core.user.UserService;
import ged.ejb.core.util.Log;
import ged.web.core.view.AbstractSearchBean;

@Named
@ViewScoped
public class UserSearchPopupBean extends AbstractSearchBean<User> {

	private static final long serialVersionUID = -2701760708389112615L;

	private String email;

	private String inputId;

	private String name;

	private boolean rendered;

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

	public String getSurename() {
		return this.surename;
	}

	public boolean isRendered() {
		return this.rendered;
	}

	public void open() {
		this.rendered = true;
	}

	@Log
	@Override
	public void search() {
		this.logger.fine("Searching for Users");
		if ((this.name != null) || (this.surename != null)) {
			this.entities = this.userService.findUsers(this.name, this.surename);
		} else {
			this.entities = this.userService.findAll();
		}
		trimList();
		setPaginationSize();
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

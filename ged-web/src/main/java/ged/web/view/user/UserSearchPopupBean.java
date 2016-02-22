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

	private String inputId;

	public String getInputId() {
		return inputId;
	}

	public void setInputId(String inputId) {
		this.inputId = inputId;
	}

	private String email;

	private String name;

	private boolean rendered;

	private String surename;

	@Inject
	private UserService userService;

	public void clean() {
		email = null;
		surename = null;
		name = null;
		search();
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

	public boolean isRendered() {
		return rendered;
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

	public void open() {
		rendered = true;
	}

	private <C extends UIComponent> UIComponent findChildrenByName(UIComponent parent) {
		UIComponent aux = null;
		for (UIComponent component : parent.getChildren()) {
			if (component.getId().equals(inputId)) {
				return component;
			}
			aux = findChildrenByName(component);
			if ( aux != null ) {
				return aux;				
			}
		}
		return null;
	}

	public void select(User user) {
		UIComponent foundComponent = null;
		foundComponent = findChildrenByName(facesContext.getViewRoot());
		((UIInput) foundComponent).setValue(user);
		rendered = false;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public void setSurename(String surename) {
		this.surename = surename;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}

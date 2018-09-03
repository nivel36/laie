package ged.web.core.view;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.model.Ownerable;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Named
@SessionScoped
public class SessionUser implements Serializable {

	private static final long serialVersionUID = -8079836415042166193L;

	private Locale locale;

	private List<User> team;

	private User user;
	
	@Inject
	private transient FacesContext facesContext;

	@Inject
	private transient UserService userService;

	public Locale getLocale() {
		return this.locale;
	}

	private ResourceBundle getResourceBundle(final String filename) {
		final Locale facesLocale = facesContext.getViewRoot().getLocale();
		return ResourceBundle.getBundle(filename, facesLocale);
	}

	public int getRowsPerPage() {
		return this.user.getRowsPerPage();
	}

	public List<User> getTeam() {
		return this.team;
	}

	public User get() {
		return this.user;
	}
	
	public boolean isAdmin() {
		return user.isAdmin();
	}

	@PostConstruct
	public void init() {
		final String username = this.facesContext.getExternalContext().getRemoteUser();
		this.user = userService.findUserByEmail(username);
		this.locale = new Locale(this.user.getLanguage());
		this.team = userService.findSubordinateUsers(this.user);
	}
	
	public boolean hasPermissionToEdit(final Ownerable entity) {
		Objects.requireNonNull(entity);
		Objects.requireNonNull(entity.getOwner());
		
		if (isAdmin()) {
			return true;
		}
		final User owner = entity.getOwner();
		if (user.equals(owner)) {
			return true;
		}
		return getTeam().contains(owner);
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public void setRowsPerPage(final int rowsPerPage) {
		this.user.setRowsPerPage(rowsPerPage);
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	protected String translate(final String message) {
		final ResourceBundle bundle = this.getResourceBundle("ged.i18n");
		return bundle.getString(message);
	}
}

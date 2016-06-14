package ged.web.core.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.model.Action;
import ged.ejb.core.model.SavedSearch;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Named
@SessionScoped
public class SessionBean extends AbstractBean {

	private static final long serialVersionUID = -8079836415042166193L;

	@Produces
	private List<Action> actions = new ArrayList<Action>();

	private final List<BreadcrumbState> breadcrumb = new ArrayList<BreadcrumbState>();

	private Locale locale;

	@Produces
	private List<SavedSearch> savedSearches = new ArrayList<SavedSearch>();

	@Produces
	private User user;

	@Inject
	private transient UserService userService;

	public void addAction(final Action action) {
		if (!containsAction(action)) {
			if (this.actions.size() > 9) {
				this.actions.remove(9);
			}
			this.actions.add(0, action);
		}
	}

	public void addBreadcrumb(final BreadcrumbState breadcrumbState) {
		this.breadcrumb.add(breadcrumbState);
	}

	public void clearBreadcrumb() {
		this.breadcrumb.clear();
	}

	public boolean containsAction(final Action action) {
		return this.actions.contains(action);
	}

	public List<Action> getActions() {
		return this.actions;
	}

	public List<BreadcrumbState> getBreadcrumb() {
		return this.breadcrumb;
	}

	public Locale getLocale() {
		return this.locale;
	}

	private ResourceBundle getResourceBundle(final String filename) {
		final Locale locale = this.facesContext.getViewRoot().getLocale();
		final ResourceBundle bundle = ResourceBundle.getBundle(filename, locale);
		return bundle;
	}

	public int getRowsPerPage() {
		return this.user.getRowsPerPage();
	}

	public List<SavedSearch> getSavedSearches() {
		return this.savedSearches;
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		final String username = this.facesContext.getExternalContext().getRemoteUser();
		this.user = this.userService.findUserByUsername(username);
		this.locale = new Locale(this.user.getLanguage());
	}

	public void removeAction(final Action action) {
		this.actions.remove(action);
	}

	public void setActions(final List<Action> actions) {
		this.actions = actions;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public void setRowsPerPage(final int rowsPerPage) {
		this.user.setRowsPerPage(rowsPerPage);
	}

	public void setSavedSearches(final List<SavedSearch> savedSearches) {
		this.savedSearches = savedSearches;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	protected String translate(final String message) {
		final ResourceBundle bundle = getResourceBundle("ged.i18n");
		final String translatedMessage = bundle.getString(message);
		return translatedMessage;
	}
}

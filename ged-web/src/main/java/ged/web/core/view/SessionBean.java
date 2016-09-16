package ged.web.core.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Named
@SessionScoped
public class SessionBean extends AbstractBean {

	private static final long serialVersionUID = -8079836415042166193L;

	private final List<BreadcrumbState> breadcrumb = new ArrayList<BreadcrumbState>();

	private Locale locale;

	private User user;

	@Inject
	private transient UserService userService;

	public void addBreadcrumb(final BreadcrumbState breadcrumbState) {
		this.breadcrumb.add(breadcrumbState);
	}

	public void clearBreadcrumb() {
		this.breadcrumb.clear();
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

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		final String username = this.facesContext.getExternalContext().getRemoteUser();
		this.user = this.userService.findUserByUsername(username);
		this.locale = new Locale(this.user.getLanguage());
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
		final ResourceBundle bundle = getResourceBundle("ged.i18n");
		final String translatedMessage = bundle.getString(message);
		return translatedMessage;
	}
}

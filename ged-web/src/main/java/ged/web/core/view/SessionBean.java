package ged.web.core.view;

import java.util.Calendar;
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

	private Locale locale;

	private User user;

	@Inject
	private transient UserService userService;

	public Locale getLocale() {
		return this.locale;
	}

	private ResourceBundle getResourceBundle(final String filename) {
		final Locale facesLocale = this.facesContext.getViewRoot().getLocale();
		return ResourceBundle.getBundle(filename, facesLocale);
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
		this.user = this.userService.findUserByEmail(username);
		setLastConnectionToNow(user);
		this.locale = new Locale(user.getLanguage());
	}

	private void setLastConnectionToNow(User user) {
		user.setLastConnection(Calendar.getInstance().getTime());
		user = this.userService.save(user);
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

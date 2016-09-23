package ged.web.view.config;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class ConfigIndexBean extends AbstractPageBean {

	private static final Logger logger = Logger.getLogger(ConfigIndexBean.class.getName());

	private static final long serialVersionUID = -2789492893353263506L;

	private User user;

	@Inject
	private transient UserService userService;

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		logger.finest("Init ConfigIndexBean");
		this.user = this.sessionBean.getUser();
	}

	public void save() {
		logger.log(Level.FINE, "Save config action performed");
		this.user = this.userService.update(this.user);
		this.sessionBean.setUser(this.user);
		this.sessionBean.setLocale(new Locale(this.user.getLanguage()));
		this.sessionBean.setRowsPerPage(this.user.getRowsPerPage());
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}
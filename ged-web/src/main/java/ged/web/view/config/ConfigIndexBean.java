package ged.web.view.config;

import java.util.Locale;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public final class ConfigIndexBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(ConfigIndexBean.class.getName());

	private static final long serialVersionUID = -2789492893353263506L;

	private User user;

	private transient UserService userService;

	@Inject
	public ConfigIndexBean(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	private void changeSessionUser() {
		this.sessionBean.setUser(this.user);
		this.sessionBean.setLocale(new Locale(this.user.getLanguage()));
		this.sessionBean.setRowsPerPage(this.user.getRowsPerPage());
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		logger.trace("Init ConfigIndexBean");
		this.user = this.sessionBean.getUser();
	}

	public void save() {
		logger.debug("Save user action performed");
		final long userId = this.user.getId();
		if (userId == this.sessionBean.getUser().getId()) {
			changeSessionUser();
		}
		this.user.setUser(this.sessionBean.getUser());
		this.user = this.userService.save(this.user);
		this.sessionBean.setUser(this.user);
	}

	public void setUser(final User user) {
		this.user = user;
	}
}
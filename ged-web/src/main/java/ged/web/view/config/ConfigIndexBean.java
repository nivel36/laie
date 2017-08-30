package ged.web.view.config;

import java.lang.invoke.MethodHandles;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
public class ConfigIndexBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -2789492893353263506L;

	private User user;

	@Inject
	private transient UserService userService;

	public void setUserService(UserService userService) {
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
		this.user = this.userService.save(this.user);
		this.sessionBean.setUser(this.user);
		addMessage(FacesMessage.SEVERITY_INFO, "action.save_action_performed", "action.save_action_performed");
	}

	public void setUser(final User user) {
		this.user = user;
	}
}
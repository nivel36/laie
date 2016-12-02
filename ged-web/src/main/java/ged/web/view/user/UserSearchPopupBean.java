package ged.web.view.user;

import java.util.logging.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.UserService;

@Named
@ViewScoped
public class UserSearchPopupBean extends AbstractUserSearchBean {

	public static final transient Logger logger = Logger.getLogger(UserSearchPopupBean.class.getName());

	private static final long serialVersionUID = 9150785979243375541L;

	protected boolean rendered = false;

	@Inject
	public UserSearchPopupBean(final UserService userService) {
		super(userService);
	}

	public void cancel() {
		this.rendered = false;
	}

	public void hide() {
		clean();
		this.rendered = false;
	}

	public boolean isRendered() {
		return this.rendered;
	}

	public void open() {
		this.rendered = true;
	}

	public void setRendered(final boolean rendered) {
		this.rendered = rendered;
	}

	public void show() {
		this.rendered = true;
		search();
	}
}
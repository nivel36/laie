package ged.web.view.user;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.UserService;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class UserSearchPopupBean extends AbstractUserSearchBean {

	public static final transient Logger logger = LoggerFactory.getLogger(UserSearchPopupBean.class.getName());

	private static final long serialVersionUID = 9150785979243375541L;

	protected boolean rendered = false;

	@Inject
	public UserSearchPopupBean(final UserService userService) {
		super(userService);
	}

	public void cancel() {
		hide();
	}

	public void hide() {
		cleanSearchFields();
		this.rendered = false;
	}

	@Override
	@PostConstruct
	public void init() {
		logger.trace( "Init UserSearchBean");
		this.paginator = new Paginator<>(this.sessionBean.getRowsPerPage());
		if (isRendered()) {
			search();
		}
	}

	public boolean isRendered() {
		return this.rendered;
	}

	public void open() {
		this.rendered = true;
	}

	public void show() {
		this.rendered = true;
		search();
	}
}
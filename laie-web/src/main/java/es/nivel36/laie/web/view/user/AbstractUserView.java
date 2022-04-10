package es.nivel36.laie.web.view.user;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.Role;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;

public abstract class AbstractUserView extends AbstractView {

	private static final long serialVersionUID = 5892896620148492686L;

	private static final Logger logger = LoggerFactory.getLogger(AbstractUserView.class);

	@Param(name="user", converter="userConverter")
	protected User user;

	@Inject
	protected transient FileService fileUploadService;

	@Inject
	protected transient UserService userService;

	public void changeRoleListener() {
		logger.trace("Change role listener triggered");
		if (this.user.getRole().equals(Role.ADMIN)) {
			// Los Adminstradores no tienen managers
			this.user.setManager(null);
		}
	}

	public List<User> queryManager(final String query) {
		logger.trace("Search manager with the string {}", query);
		return this.userService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData();
	}

	protected String userUrl() {
		return ViewUserView.URL + "?user=" + this.user.getId();
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setFileUploadService(final FileService fileUploadService) {
		Objects.requireNonNull(fileUploadService);
		this.fileUploadService = fileUploadService;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}

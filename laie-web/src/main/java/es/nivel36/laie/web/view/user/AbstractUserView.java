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

	private static final long serialVersionUID = -5806017913115206570L;

	private static final Logger logger = LoggerFactory.getLogger(AbstractUserView.class);
	
	@Param
	protected User user;

	@Inject
	protected transient FileService fileUploadService;

	@Inject
	protected transient UserService userService;
	
	public void changeRoleListener() {
		logger.trace("Change role listener triggered");
		if (getUser().getRole().equals(Role.ADMIN)) {
			// Los Administradores no tienen managers
			getUser().setManager(null);
		}
	}

	public List<User> queryManager(final String query) {
		logger.trace("Search manager with the string {}", query);
		return this.userService.search(query, Page.FIRST_TEN_RESULTS).getResultData();
	}

	protected String viewUserUrl() {
		return ViewUserView.getUrl(this.user.getId());
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

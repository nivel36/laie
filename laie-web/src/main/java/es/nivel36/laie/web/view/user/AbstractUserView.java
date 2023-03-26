package es.nivel36.laie.web.view.user;

import java.util.List;
import java.util.Objects;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.commons.file.PhysicalFileService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.inject.Inject;

public abstract class AbstractUserView extends AbstractView {

	private static final long serialVersionUID = -5806017913115206570L;

	private static final Logger logger = LoggerFactory.getLogger(AbstractUserView.class);

	protected @Param User user;

	protected transient @Inject PhysicalFileService fileUploadService;

	protected transient @Inject UserService userService;

	public void changeRoleListener() {
		logger.trace("Change role listener triggered");
	}

	public List<User> queryManager(final String query) {
		logger.trace("Search manager with the string {}", query);
		return this.userService.search(query, Page.FIRST_TEN_RESULTS).hits();
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

	public void setFileUploadService(final PhysicalFileService fileUploadService) {
		Objects.requireNonNull(fileUploadService);
		this.fileUploadService = fileUploadService;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}

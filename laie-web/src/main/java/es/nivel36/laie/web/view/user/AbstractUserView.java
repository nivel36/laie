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
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;

public abstract class AbstractUserView extends AbstractView {

	private static final long serialVersionUID = 7151813864584776910L;

	private static final Logger logger = LoggerFactory.getLogger(AbstractUserView.class);

	@Inject
	protected transient FileService fileUploadService;

	@Inject
	@Param(name = "uid", required = true, converter = "userConverter")
	protected UserDto user;

	@Inject
	protected transient UserService userService;

	public void changeRoleListener() {
		logger.trace("Change role listener triggered");
		if (this.user.getRoleName().equals(Role.ADMIN.name())) {
			this.user.setManager(null);
		}
	}

	public UserDto getUser() {
		return this.user;
	}

	public List<UserDto> queryManager(final String query) {
		logger.trace("Search manager with the string {}", query);
		final List<UserDto> managers = this.userService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData();
		managers.remove(this.user);
		return managers;
	}

	public void setFileUploadService(final FileService fileUploadService) {
		Objects.requireNonNull(fileUploadService);
		this.fileUploadService = fileUploadService;
	}

	public void setUser(final UserDto user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	protected String userUrl() {
		return this.navigator.getRedirectUrl(PageEnum.USER, this.user.getUid());
	}
}

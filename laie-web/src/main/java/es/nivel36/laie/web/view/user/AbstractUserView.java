package es.nivel36.laie.web.view.user;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.core.model.Page;
import es.nivel36.files.FileService;
import es.nivel36.laie.ejb.user.Role;
import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;

public abstract class AbstractUserView extends AbstractView {

	private static final long serialVersionUID = 5892896620148492686L;

	private static final Logger logger = LoggerFactory.getLogger(AbstractUserView.class);

	protected UserDto user;

	protected SimpleUserDto manager;

	@Inject
	protected transient FileService fileUploadService;

	@Inject
	protected transient UserService userService;

	public void changeRoleListener() {
		logger.trace("Change role listener triggered");
		if (this.user.getRoleName().equals(Role.ADMIN.name())) {
			this.user.setManager(null);
		}
	}

	public List<SimpleUserDto> queryManager(final String query) {
		logger.trace("Search manager with the string {}", query);
		final List<UserDto> resultData = this.userService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData();
		final List<SimpleUserDto> managers = resultData.stream().filter(u -> !u.equals(user)).map(SimpleUserDto::new)
				.collect(Collectors.toList());
		return managers;
	}

	protected String userUrl() {
		return "/user/view.xhtml?user=" + this.user.getUid();
	}

	public SimpleUserDto getmanager() {
		return this.manager;
	}

	public UserDto getUser() {
		return this.user;
	}

	public void setManager(final SimpleUserDto manager) {
		this.manager = manager;
	}

	public void setUser(final UserDto user) {
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

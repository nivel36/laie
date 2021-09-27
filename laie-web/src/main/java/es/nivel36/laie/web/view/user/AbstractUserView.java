package es.nivel36.laie.web.view.user;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;

public abstract class AbstractUserView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	@Inject
	protected transient FileService fileUploadService;

	@Inject
	@Param(name = "id", required = true, converter = "userConverter")
	protected User user;

	@Inject
	protected transient UserService userService;

	public void changeRoleListener() {
		logger.trace("Change role listener triggered");
		if (this.user.isAdmin()) {
			this.user.setManager(null);
		}
	}

	public User getUser() {
		return this.user;
	}

	public List<User> queryManager(final String query) {
		logger.trace("Search manager with the string {}", query);
		final List<User> managers = this.userService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData();
		managers.remove(this.user);
		return managers;
	}

	public void setFileUploadService(final FileService fileUploadService) {
		Objects.requireNonNull(fileUploadService);
		this.fileUploadService = fileUploadService;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	protected String userUrl() {
		return this.navigator.getRedirectUrl(PageEnum.USER, this.user);
	}

	public void validateEmail(final FacesContext context, final UIComponent component, final Object value) {
		if (value == null) {
			return;
		}
		final String userEmail = (String) value;
		logger.trace("Validate user email {}", userEmail);
		if (userEmail.equals(this.user.getEmail())) {
			// If the old and the new email are equals, the user is not updating the email.
			return;
		}
		if (this.userService.isEmailInUse(userEmail)) {
			logger.warn("Email {} exists", userEmail);
			final String msg = this.translator.message("user.error.email_exists");
			throw new ValidatorException(new FacesMessage(SEVERITY_ERROR, msg, msg));
		}
	}
}

package es.nivel36.laie.view.user;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.core.view.AbstractView;
import es.nivel36.laie.user.DuplicateEmailException;
import es.nivel36.laie.user.DuplicateIdNumberException;
import es.nivel36.laie.user.InvalidManagerException;
import es.nivel36.laie.user.UserDto;
import es.nivel36.laie.user.UserService;

@Named
@ViewScoped
public class EditUserView extends AbstractView {

	private static final long serialVersionUID = 3914427458106069743L;

	private static final Logger logger = LoggerFactory.getLogger(EditUserView.class);

	@Inject
	@Param(name = "uid", required = false, converter = "userConverter")
	private UserDto user;

	@Inject
	private transient UserService userService;

	@PostConstruct
	public void init() {
		if (user == null) {
			user = new UserDto();
		}
	}

	public void changeManager(final UserDto newManager) {
		logger.debug("Change to manager {} for user {}", newManager, user);
		try {
			this.userService.updateManager(this.user.getUid(), newManager.getUid());
		} catch (final InvalidManagerException e) {
			final String message = e.getMessage();
			if (message.equals("User is the manager of his/her new manager")) {
				this.addMessage(FacesMessage.SEVERITY_ERROR, "user.error.title.manager_error",
						"user.error.user_is_manager");
			} else {
				this.addMessage(FacesMessage.SEVERITY_ERROR, "user.error.title.manager_error",
						"user.error.invalid_manager");
			}
		}
	}

	public UserDto getUser() {
		return this.user;
	}

	public void save() {
		logger.debug("Save data for user {}", user);
		try {
			if (this.user.getUid() == null) {
				this.userService.insert(this.user);
			} else {
				this.userService.update(this.user);
			}
		} catch (final DuplicateEmailException e) {
			this.addErrorToField("email", "user.error.duplicate_email");
		} catch (final DuplicateIdNumberException e) {
			this.addErrorToField("idNumber", "user.error.duplicate_id");
		}
		redirect("/user.xhtml?uid=" + user.getUid());
	}

	public List<UserDto> queryManager(final String query) {
		logger.trace("Search manager with the string {}", query);
		final List<UserDto> managers = this.userService.searchByName(0, 10, null, query);
		managers.remove(this.user);
		return managers;
	}

	public void setUser(final UserDto user) {
		Objects.requireNonNull(user);
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
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
		final UserDto user = userService.findUserByEmail(userEmail);
		if (user != null && user != this.user) {
			logger.warn("Email {} exists", userEmail);
			final String msg = this.translator.message("user.error.email_exists");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}
}

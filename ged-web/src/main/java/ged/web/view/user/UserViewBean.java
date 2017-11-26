package ged.web.view.user;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleService;
import ged.web.core.util.ConfigurationProperty;
import ged.web.core.util.MessageUtils;
import ged.web.core.util.Navigate;
import ged.web.core.util.TransaltionUtils;
import ged.web.core.view.AbstractPageBean;
import ged.web.reports.UserReport;

@Named
@ViewScoped
public class UserViewBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -2187385732087309689L;

	private boolean editable;

	@Inject
	@ConfigurationProperty(value = "image.directory")
	private String imageDirectory;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	private User manager;

	@Inject
	private transient RoleService roleService;

	private List<User> team;

	private User user;

	private String userId;

	@Inject
	private transient UserService userService;

	public void cancelEditUser() {
		this.editable = false;
	}

	public List<User> completeManager(final String query) {
		return this.userService.search(query);
	}

	public void editUser() {
		this.editable = true;
	}

	private void error() {
		Navigate.toPage("userSearch");
	}

	public void export() throws IOException {
		final UserReport userReport = new UserReport(this.user, this.jobOffers);
		Faces.sendFile(userReport.create(), true);
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public User getManager() {
		return this.manager;
	}

	public List<User> getTeam() {
		return this.team;
	}

	public User getUser() {
		return this.user;
	}

	public String getUserId() {
		return this.userId;
	}

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (this.userId == null) {
			error();
		}
		long id = 0;
		try {
			id = Long.parseLong(this.userId);
		} catch (final NumberFormatException ex) {
			error();
		}
		this.user = this.userService.find(id);
		if (this.user == null) {
			error();
		}
		this.manager = this.user.getManager();
		this.team = this.userService.findSubordinateUsers(this.user);
		this.jobOffers = this.jobOfferService.findAllJobOffersByOwner(this.user);
		if (this.user.isDeleted()) {
			MessageUtils.addWarningMessage("message.erased_entity", "message.erased_entity");
		}
	}

	private boolean isAvalidRole(final Role userRole, final Role managerRole) {
		if (userRole.getName().equals(managerRole.getName())) {
			return true;
		}
		return this.roleService.isASubordinateRole(managerRole, userRole);
	}

	public boolean isEditable() {
		return this.editable;
	}

	public String modifyUser() {
		this.flash.put("user", this.user);
		return "userEdit?faces-redirect=true";
	}

	public void saveUser() {
		logger.debug("Save user action performed");
		try {
			this.user.setManager(this.manager);
			this.user = this.userService.save(this.user);
			this.editable = false;
		} catch (final Exception ue) {
			final Role admin = this.roleService.findAdmin();
			this.user.setRole(admin);
			addMessage(FacesMessage.SEVERITY_ERROR, "user.error.last_admin", "user.error.last_admin");
		}
	}

	public void setImageDirectory(final String imageDirectory) {
		this.imageDirectory = imageDirectory;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setManager(final User manager) {
		this.manager = manager;
	}

	public void setRoleService(final RoleService roleService) {
		this.roleService = roleService;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	private String upload(final String directory, final UploadedFile file) {
		final String uuid = UUID.randomUUID().toString();
		try (InputStream input = file.getInputstream()) {
			Files.copy(input, new java.io.File(directory, uuid).toPath());
		} catch (final IOException ex) {
			logger.error("Can't upload file", ex);
			MessageUtils.addErrorMessage("error.unnexpected_error", "error.unnexpected_error");
		}
		return uuid;
	}

	public void uploadImage(final FileUploadEvent event) {
		final String uuid = upload(this.imageDirectory, event.getFile());
		this.user.setImageFileName(uuid);
		this.user = this.userService.save(this.user);
	}

	public void validateEmail(final FacesContext context, final UIComponent component, final Object value)
			throws ValidatorException {
		logger.debug("Checking email");
		if (value == null) {
			return;
		}
		final String email = (String) value;
		if (value.equals(this.user.getEmail())) {
			// Si el valor del email es el mismo que el que estamos validando
			// es porque estamos actualizando un valor (que no es el email)
			// y no hace falta que validemos si el registro existe (que por otra
			// parte sí lo estará)
			return;
		}
		if (this.userService.emailExists(email)) {
			logger.debug("The email exists");
			final String msg = TransaltionUtils.translate("user.error.email_exists");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	public void validateManager(final FacesContext context, final UIComponent component, final Object value)
			throws ValidatorException {
		if (value == null) {
			return;
		}
		final User manager = (User) value;
		if (manager.equals(this.user)) {
			logger.debug("User can't be his/her manager");
			final String msg = TransaltionUtils.translate("user.error.manager");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	public void validateRole(final FacesContext context, final UIComponent component, final Object value)
			throws ValidatorException {
		if (this.manager == null || this.manager.getEmail() == null) {
			return;
		}
		final Role userRole = (Role) value;
		final Role managerRole = this.manager.getRole();
		if (!isAvalidRole(userRole, managerRole)) {
			final String msg = TransaltionUtils.translate("user.error.role");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}
}
package ged.web.view.user;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserException;
import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleService;
import ged.web.core.util.Message;
import ged.web.core.util.Navigate;
import ged.web.core.util.Translate;
import ged.web.core.view.AbstractBean;
import ged.web.core.view.FileUploadService;
import ged.web.reports.UserReport;

@Named
@ViewScoped
public class UserBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -2187385732087309689L;

	private boolean editable;

	@Inject
	private transient FileUploadService fileUploadService;

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

	public User buildNewUser() {
		return new User();
	}

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
		Navigate.toUserSearch();
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
		if (this.userId != null) {
			try {
				final long id = Long.parseLong(this.userId);
				this.user = this.userService.find(id);
				if (this.user == null) {
					this.error();
				}
				this.manager = this.user.getManager();
				this.team = this.userService.findSubordinateUsers(this.user);
				this.jobOffers = this.jobOfferService.findAllJobOffersByOwner(this.user);
				if (this.user.isDeleted()) {
					Message.addWarning("message.erased_entity", "message.erased_entity");
				}
			} catch (final NumberFormatException ex) {
				this.error();
			}
		} else {
			this.editable = true;
			this.user = this.buildNewUser();
		}
	}

	public String insertUser() {
		logger.debug("Insert user action performed");
		this.user.setManager(this.manager);
		this.userService.insert(this.user);
		return "user.xhtml?id=" + this.user.getId() + "&faces-redirect=true";
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

	public boolean isNewUser() {
		return this.user.getId() == 0;
	}

	public String modifyUser() {
		this.flash.put("user", this.user);
		return "user?faces-redirect=true";
	}

	public void setFileUploadService(final FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
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

	public void updateUser() {
		logger.debug("Update user action performed");
		try {
			this.user.setManager(this.manager);
			this.user = this.userService.update(this.user);
			this.editable = false;
		} catch (final EJBException e) {
			if (e.getCause() instanceof UserException) {
				final Role admin = this.roleService.findAdmin();
				this.user.setRole(admin);
				this.addMessage(FacesMessage.SEVERITY_ERROR, "user.error.last_admin", "user.error.last_admin");
			} else {
				throw e;
			}
		}
	}

	public void uploadImage(final FileUploadEvent event) throws IOException {
		final String uuid = this.fileUploadService.uploadImage(event.getFile());
		this.user.setImageFileName(uuid);
	}

	public void validateEmail(final FacesContext context, final UIComponent component, final Object value) {
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
			final String msg = Translate.message("user.error.email_exists");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	public void validateManager(final FacesContext context, final UIComponent component, final Object value) {
		if (value == null) {
			return;
		}
		final User managerToValidate = (User) value;
		if (managerToValidate.equals(this.user)) {
			logger.debug("User can't be his/her manager");
			final String msg = Translate.message("user.error.manager");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	public void validateRole(final FacesContext context, final UIComponent component, final Object value) {
		if ((this.manager == null) || (this.manager.getEmail() == null)) {
			return;
		}
		final Role userRole = (Role) value;
		final Role managerRole = this.manager.getRole();
		if (!this.isAvalidRole(userRole, managerRole)) {
			final String msg = Translate.message("user.error.role");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}
}
package ged.web.view.user;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.primefaces.event.CaptureEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleService;
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

	private void newManager() {
		this.manager = new User();
		this.manager.setName("");
		this.manager.setSurename("");
	}

	public void removeManager() {
		logger.debug("Remove manager action performed");
		newManager();
		this.user.setManager(null);
	}

	public void saveUser() {
		logger.debug("Save user action performed");
		try {
			this.user = this.userService.save(this.user);
			this.editable = false;
		} catch (final Exception ue) {
			final Role admin = this.roleService.findAdmin();
			this.user.setRole(admin);
			addMessage(FacesMessage.SEVERITY_ERROR, "user.error.last_admin", "user.error.last_admin");
		}
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

	public void undeleteUser() {
		this.userService.undelete(this.user);
	}

	public void uploadImage(final CaptureEvent captureEvent) {
		this.user.setImageFileName(this.userId);
		final byte[] data = captureEvent.getData();

		final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		final String newFileName = externalContext.getRealPath("") + File.separator + "resources" + File.separator
				+ "demo" + File.separator + "images" + File.separator + "photocam" + File.separator
				+ this.user.getImageFileName() + ".jpeg";

		FileImageOutputStream imageOutput;
		try {
			imageOutput = new FileImageOutputStream(new File(newFileName));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
		} catch (final IOException e) {
			throw new FacesException("Error in writing captured image.", e);
		}
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
		if (this.manager.equals(this.user)) {
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
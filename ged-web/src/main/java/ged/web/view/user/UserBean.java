package ged.web.view.user;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.PageNotFoundException;
import ged.web.core.util.Message;
import ged.web.core.view.AbstractBean;
import ged.web.reports.UserReport;

@Named
@ViewScoped
public class UserBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -2187385732087309689L;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	private List<User> team;

	private User user;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Param(required = true)
	@Inject
	private Long userId;

	@Inject
	private transient UserService userService;

	public void export() throws IOException {
		final UserReport userReport = new UserReport(this.user, this.jobOffers);
		Faces.sendFile(userReport.create(), true);
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public User getManager() {
		return this.user.getManager();
	}

	public List<User> getTeam() {
		return this.team;
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		logger.trace("UserBean init");
		this.user = this.userService.find(this.userId);
		if (this.user == null) {
			throw new PageNotFoundException();
		}
		this.team = this.userService.findSubordinateUsers(this.user);
		this.jobOffers = this.jobOfferService.findAllJobOffersByOwner(this.user);
		if (this.user.isDeleted()) {
			Message.addWarning("message.erased_entity", "message.erased_entity");
		}
	}

	public void onCloseUserDialog(final SelectEvent event) {
		Objects.requireNonNull(event);
		final User userFromDialog = (User) event.getObject();
		if (userFromDialog != null) {
			this.user = userFromDialog;
		}
	}

	public void openUserDialog() {
		this.openDialog("/faces/user/userDialog", this.buildDialogParameter("userId", String.valueOf(this.userId)));
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}
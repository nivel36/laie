package ged.web.view.user;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
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

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@Param(name = "userId", required = true)
	private User user;

	@Inject
	private transient UserService userService;

	public void editUser() {
		this.flash.put("user", this.user);
	}

	public void export() throws IOException {
		final UserReport userReport = new UserReport(this.user, this.jobOffers);
		Faces.sendFile(userReport.create(), true);
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public List<User> getTeam() {
		return this.team;
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		logger.debug("UserBean init");
		this.team = this.userService.findSubordinateUsers(this.user);
		this.jobOffers = this.jobOfferService.findAllJobOffersByOwner(this.user);
		if (this.user.isDeleted()) {
			Message.addWarning("message.erased_entity", "message.erased_entity");
		}
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
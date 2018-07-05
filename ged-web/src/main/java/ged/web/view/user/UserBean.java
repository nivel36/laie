package ged.web.view.user;

import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.Type.GET;
import static ged.web.core.util.PageEnum.USER;

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
import ged.web.core.PageNotFoundException;
import ged.web.core.util.Message;
import ged.web.core.util.Page;
import ged.web.core.view.AbstractBean;
import ged.web.reports.UserReport;

@Named
@ViewScoped
public class UserBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static Page page = new Page("/faces/user/user", GET);

	private static final long serialVersionUID = -2187385732087309689L;

	public static void go(final String id) {
		page.go("userId", id);
	}

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

	public void newUser() {
		to(USER).doPost();
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
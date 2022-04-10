package es.nivel36.laie.web.view.user;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.reports.UserReport;

@Named
@ViewScoped
public class ViewUserView extends AbstractView {

	private static final long serialVersionUID = 1878119297191882440L;

	private static final Logger logger = LoggerFactory.getLogger(ViewUserView.class);

	public static final String URL = "/user/view.xhtml";
	
	@Param(name="user", converter="userConverter")
	private User user;

	private boolean editable;

	private List<JobOffer> jobOffers;

	private List<Meeting> meetings;

	private List<User> team;

	@Inject
	private transient JobOfferService jobOfferService;

	@Inject
	private transient MeetingService meetingService;

	@Inject
	private transient UserService userService;

	@PostConstruct
	public void init() {
		if (this.user == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("User {} init", this.user);
		this.team = this.userService.findSubordinateUsers(this.user);
		this.jobOffers = this.jobOfferService.findJobOffersByOwner(this.user, Page.ALL_RESULTS);
		this.meetings = this.meetingService.findPlannedMeetings(this.user, Page.TEN_RESULTS_PER_PAGE);
		this.editable = this.sessionUser.isAdmin() || this.sessionUser.get().equals(this.user);
	}

	public void editUser() {
		logger.debug("Edit user action performed");
		this.navigateTo(EditUserView.URL + "?user=" + this.user.getId());
	}

	public void export() throws IOException {
		logger.debug("Export user action performed");
		final UserReport userReport = new UserReport(this.user, this.jobOffers);
		Faces.sendFile(userReport.create(), true);
	}

	public boolean isLogedUser() {
		return this.sessionUser.get().equals(this.user);
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public List<Meeting> getMeetings() {
		return this.meetings;
	}

	public List<User> getTeam() {
		return this.team;
	}

	public User getUser() {
		return this.user;
	}

	public boolean isEditable() {
		return this.editable;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}

	public void setMeetingService(final MeetingService meetingService) {
		Objects.requireNonNull(meetingService);
		this.meetingService = meetingService;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}
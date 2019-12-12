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

import ged.ejb.core.model.Page;
import ged.ejb.job.meeting.Meeting;
import ged.ejb.job.meeting.MeetingService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.IllegalPageStateException;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;
import ged.web.reports.UserReport;

@Named
@ViewScoped
public class ViewUserView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private boolean editable;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	private List<Meeting> meetings;

	@Inject
	private transient MeetingService meetingService;

	private List<User> team;

	@Inject
	@Param(name = "id", required = true, converter = "userConverter")
	private User user;

	@Inject
	private transient UserService userService;

	public String editUser() {
		logger.debug("Edit user action performed");
		this.putValueToFlash("user", this.user);
		return PageEnum.USER_EDIT.getUrl();
	}

	public void export() throws IOException {
		logger.debug("Export user action performed");
		final UserReport userReport = new UserReport(this.user, this.jobOffers);
		Faces.sendFile(userReport.create(), true);
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

	@PostConstruct
	public void init() {
		if (this.user == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("User {} init", this.user);
		this.team = this.userService.findSubordinateUsers(this.user);
		this.jobOffers = this.jobOfferService.findJobOffers(this.user, Page.ALL_RESULTS);
		this.editable = this.sessionUser.isAdmin();
		this.meetings = this.meetingService.findPlannedMeetings(this.user, Page.TEN_RESULTS_PER_PAGE);
	}

	public boolean isEditable() {
		return this.editable;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setMeetingService(final MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}
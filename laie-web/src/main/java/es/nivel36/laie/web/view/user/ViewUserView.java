package es.nivel36.laie.web.view.user;

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

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.meeting.MeetingDto;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.ejb.job.offer.JobOfferDto;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.reports.UserReport;

@Named
@ViewScoped
public class ViewUserView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private boolean editable;

	private List<JobOfferDto> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	private List<MeetingDto> meetings;

	@Inject
	private transient MeetingService meetingService;

	private List<UserDto> team;

	@Inject
	@Param(name = "uid", required = true, converter = "userConverter")
	private UserDto user;

	@Inject
	private transient UserService userService;

	public String editUser() {
		logger.debug("Edit user action performed");
		return this.navigator.getRedirectUrl(PageEnum.USER_EDIT, this.user.getUid());
	}

	public void export() throws IOException {
		logger.debug("Export user action performed");
		final UserReport userReport = new UserReport(this.user, this.jobOffers);
		Faces.sendFile(userReport.create(), true);
	}

	public List<JobOfferDto> getJobOffers() {
		return this.jobOffers;
	}

	public List<MeetingDto> getMeetings() {
		return this.meetings;
	}

	public List<UserDto> getTeam() {
		return this.team;
	}

	public UserDto getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		if (this.user == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("User {} init", this.user);
		this.team = this.userService.findSubordinateUsers(this.user.getUid());
		this.jobOffers = this.jobOfferService.findJobOffersByOwner(this.user.getUid(), Page.ALL_RESULTS);
		this.editable = this.sessionUser.isAdmin();
		this.meetings = this.meetingService.findPlannedMeetings(this.user.getUid(), Page.TEN_RESULTS_PER_PAGE);
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

	public void setUser(final UserDto user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}
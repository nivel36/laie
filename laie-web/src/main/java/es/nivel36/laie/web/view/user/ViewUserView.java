package es.nivel36.laie.web.view.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.action.ActionsByUserLazyDataModel;
import es.nivel36.laie.web.view.job.JobOffersByOwnerOrRecruiterLazyDataModel;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ViewUserView extends AbstractView {

	private static final long serialVersionUID = -6450384052805715881L;

	private static final Logger logger = LoggerFactory.getLogger(ViewUserView.class);

	private static final String URL = "/user/view.xhtml";

	private @Param(required = true, name = "user") String userId;

	private User user;

	private boolean editable;

	private List<Meeting> meetings;

	private List<User> team;

	private boolean loggedUser;

	private @Inject ActionsByUserLazyDataModel actions;
	
	private @Inject JobOffersByOwnerOrRecruiterLazyDataModel jobOffers;

	private transient @Inject MeetingService meetingService;

	private transient @Inject UserService userService;

	private transient @Inject JobOfferService jobOfferService;

	@PostConstruct
	public void init() {
		logger.trace("User {} init", this.userId);
		findUser();
		this.team = new ArrayList<>(this.user.getTeam());
		this.meetings = this.meetingService.findPlannedMeetings(this.user, Page.ALL_RESULTS);
		this.loggedUser = this.sessionUser.get().equals(this.user);
		this.editable = this.sessionUser.isAdmin() || loggedUser;
		actions.setUser(user);
		jobOffers.setUser(user);
	}

	private void findUser() {
		try {
			final Long id = Long.parseLong(userId);
			this.user = this.userService.findAllUserData(id);
			if (this.user == null) {
				throw new IllegalPageStateException();
			}
		} catch (final NumberFormatException ex) {
			throw new IllegalPageStateException();
		}
	}

	public static String getUrl(long userId) {
		return URL + "?user=" + userId;
	}

	public boolean isLogedUser() {
		return loggedUser;
	}

	public ActionsByUserLazyDataModel getActions() {
		return actions;
	}

	public JobOffersByOwnerOrRecruiterLazyDataModel getJobOffers() {
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

	public void setMeetingService(final MeetingService meetingService) {
		Objects.requireNonNull(meetingService);
		this.meetingService = meetingService;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}
}
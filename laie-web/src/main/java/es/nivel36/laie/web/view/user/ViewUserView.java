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

	public static final String URL = "/user/view.xhtml";

	public static String getUrl(long userId) {
		return URL + "?user=" + userId;
	}

	private @Param(required = true, name = "user") String userId;
	private User user;
	private boolean editable;
	private List<User> team;

	private boolean loggedUser;
	private @Inject ActionsByUserLazyDataModel actions;
	private @Inject JobOffersByOwnerOrRecruiterLazyDataModel jobOffers;
	private transient @Inject MeetingService meetingService;
	private List<Meeting> meetings;

	private transient @Inject UserService userService;

	private User findUser() {
		try {
			final Long id = Long.parseLong(userId);
			final User user = this.userService.findUserDetailsById(id);
			if (user == null) {
				throw new IllegalPageStateException();
			}
			return user;
		} catch (final NumberFormatException ex) {
			throw new IllegalPageStateException();
		}
	}

	public ActionsByUserLazyDataModel getActions() {
		return actions;
	}

	public JobOffersByOwnerOrRecruiterLazyDataModel getJobOffers() {
		Objects.requireNonNull(jobOffers);
		return this.jobOffers;
	}

	public List<Meeting> getMeetings() {
		return meetings;
	}

	public List<User> getTeam() {
		return this.team;
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		logger.trace("User {} init", this.userId);
		this.user = findUser();
		this.team = new ArrayList<>(this.user.getTeam());
		this.loggedUser = this.sessionUser.get().equals(this.user);
		this.editable = this.sessionUser.isAdmin() || loggedUser;
		this.actions.setUser(user);
		this.jobOffers.setUser(user);
		this.meetings = this.meetingService.findFutureMeetingsByOwner(user, Page.of(0, 5));
	}

	public boolean isEditable() {
		return this.editable;
	}

	public boolean isLoggedUser() {
		return loggedUser;
	}

	public void setActions(ActionsByUserLazyDataModel actions) {
		Objects.requireNonNull(actions);
		this.actions = actions;
	}

	public void setJobOffers(JobOffersByOwnerOrRecruiterLazyDataModel jobOffers) {
		Objects.requireNonNull(jobOffers);
		this.jobOffers = jobOffers;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}
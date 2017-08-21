package ged.web.view.user;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.util.MessageUtils;
import ged.web.core.view.AbstractPageBean;
import ged.web.reports.UserReport;

@Named
@ViewScoped
public class UserViewBean extends AbstractPageBean {

	private static final long serialVersionUID = -2187385732087309689L;

	private List<JobOffer> jobOffers;

	private final transient JobOfferService jobOfferService;

	private List<User> team;

	private User user;

	private String userId;

	private final transient UserService userService;

	@Inject
	public UserViewBean(final UserService userService, final JobOfferService jobOfferService) {
		Objects.requireNonNull(userService);
		Objects.requireNonNull(jobOfferService);
		this.userService = userService;
		this.jobOfferService = jobOfferService;
	}

	public String editUser() {
		this.flash.put("user", this.user);
		final String returnAddress = "userView.xhtml?id" + this.user.getId();
		this.flash.put("returnAddress", returnAddress);
		return "userEdit?faces-redirect=true";
	}

	private void error() {
		final NavigationHandler navigationHandler = this.facesContext.getApplication().getNavigationHandler();
		navigationHandler.handleNavigation(this.facesContext, null, "userSearch?faces-redirect=true");
		this.facesContext.renderResponse();
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
		this.team =  this.userService.findSubordinateUsers(id);
		this.jobOffers =  this.jobOfferService.findAllByOwner(this.user);
		if (this.user.isDeleted()) {
			MessageUtils.addWarningMessage("message.erased_entity", "message.erased_entity");
		}
	}

	public String modifyUser() {
		this.flash.put("user", this.user);
		return "userEdit?faces-redirect=true";
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public void undeleteUser() {
		this.userService.undelete(this.user);
	}
}
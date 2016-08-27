package ged.web.view.user;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.user.User;
import ged.ejb.user.service.UserService;
import ged.web.core.view.AbstractPageBean;
import ged.web.core.view.Paginator;
import ged.web.reports.UserReport;

@Named
@ViewScoped
public class UserViewBean extends AbstractPageBean {

	private static final long serialVersionUID = -2187385732087309689L;

	private Paginator<JobOffer> jobOffers;

	private Paginator<User> team;

	private User user;

	private String userId;

	@Inject
	private transient UserService userService;

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
		final UserReport userReport = new UserReport(this.user, this.jobOffers.getEntities());
		Faces.sendFile(userReport.create(), true);
	}

	public Paginator<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public Paginator<User> getTeam() {
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
		Long id = null;
		try {
			id = Long.parseLong(this.userId);
		} catch (final NumberFormatException ex) {
			error();
		}
		this.user = this.userService.find(id);
		if (this.user == null) {
			error();
		}
		this.team = new Paginator<>(this.sessionBean.getRowsPerPage());
		this.team.setEntities(this.userService.findSubordinateUsers(id));
		this.jobOffers = new Paginator<>(this.sessionBean.getRowsPerPage());
		// this.jobOffers.setEntities(this.jobService.findJobOffersByOwner(this.user));
		if (this.user.isDeleted()) {
			addMessage(FacesMessage.SEVERITY_WARN, "message.erased_entity", "message.erased_entity");
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
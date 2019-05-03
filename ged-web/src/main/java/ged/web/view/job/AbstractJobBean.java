package ged.web.view.job;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ged.ejb.core.model.Page;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractBean;

public abstract class AbstractJobBean extends AbstractBean {

	private static final long serialVersionUID = 747123354355904789L;

	protected JobOffer jobOffer;

	@Inject
	protected transient JobOfferService jobOfferService;

	protected List<String> recruiters = new ArrayList<>();

	@Inject
	protected transient UserService userService;

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public List<String> getRecruiters() {
		return this.recruiters;
	}

	public boolean isUserHasPermissionToEditJobOffer() {
		return this.sessionUser.hasPermissionToEdit(this.jobOffer);
	}

	protected String jobUrl() {
		return PageEnum.JOB.getRedirectUrl(this.jobOffer);
	}

	public List<User> searchOwner(final String query) {
		if ((query == null) || (query.trim().length() < 3)) {
			return new ArrayList<>();
		}
		return this.userService.search(query, Page.ALL);
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setRecruiters(final List<String> recruiters) {
		this.recruiters = recruiters;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

}

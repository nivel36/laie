package es.nivel36.laie.web.view.job;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.IllegalPageStateException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class EditJobView extends AbstractJobView {

	private static final long serialVersionUID = 7356542779288827753L;

	private static final Logger logger = LoggerFactory.getLogger(EditJobView.class);

	@PostConstruct
	public void init() {
		if (this.jobOffer == null) {
			logger.error("Trying to edit a job offer but job is null");
			throw new IllegalPageStateException();
		}
		checkEditPermissions();
		logger.trace("Edit job offer {} init", this.jobOffer);
		this.fillRecruiters();
	}

	private void checkEditPermissions() {
		final User user = sessionUser.get();
		if (sessionUser.isAdmin()) {
			return;
		}
		final User owner = this.jobOffer.getOwner();
		if( userService.isSubordinateUser(owner, user) ) {
			return;
		}
		if (!this.jobOffer.getOwner().equals(user)) {
			throw new SecurityException();
		}
	}

	private void fillRecruiters() {
		this.recruiters =this.jobOfferService.findRecruitersByJobOffer(jobOffer, Page.ALL_RESULTS);
	}

	public void save() {
		logger.debug("Save job offer action performed");
		if (canAddJobOfferToClient()) {
			jobOffer.setRecruiters(recruiters);
			this.jobOfferService.updateJobOfferData(jobOffer);
			Faces.redirect(ViewJobView.getUrl(this.jobOffer.getId()));
		} else {
			this.addErrorToField("jobOfferForm:client", "job.error.no_permissions");
		}
	}

	private boolean canAddJobOfferToClient() {
		final User user = sessionUser.get();
		final User owner = jobOffer.getClient().getOwner();
		final boolean isOwnersTeam = userService.isSubordinateUser(user, owner);
		return sessionUser.isAdmin() || user.equals(owner) || isOwnersTeam;
	}

	public void next() {
		jobOffer.setRecruiters(recruiters);
		this.jobOffer = this.jobOfferService.updateJobOfferData(jobOffer);
		Faces.redirect("/job/editDetails.xhtml?jobOffer=" + this.jobOffer.getId());
	}
}
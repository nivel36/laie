package ged.web.view.job;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.Page;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.job.candidature.JobCandidatureService;
import ged.web.core.IllegalPageStateException;
import ged.web.core.util.PageEnum;

@Named
@ViewScoped
public class ViewJobView extends AbstractJobView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private boolean editable;

	private List<JobCandidature> jobCandidatures;

	@Inject
	private transient JobCandidatureService jobCandidatureService;

	public String editJobOffer() {
		logger.debug("Edit job offer action performed");
		return this.navigator.getRedirectUrl(PageEnum.JOB_EDIT, this.jobOffer);
	}

	public String editJobOfferState() {
		logger.debug("Edit job offer state action performed");
		return this.navigator.getRedirectUrl(PageEnum.JOB_EDIT_STATE, this.jobOffer);
	}

	public void export() throws IOException {
		logger.debug("Export job action performed");
	}

	public List<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	@PostConstruct
	public void init() {
		if (this.jobOffer == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("JobOffer {} init", this.jobOffer);
		this.jobCandidatures = this.jobCandidatureService.findJobCanditures(this.jobOffer, Page.ALL_RESULTS);
		this.editable = this.sessionUser.hasPermissionToEdit(this.jobOffer);
	}

	public boolean isEditable() {
		return this.editable;
	}
}
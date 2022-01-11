package es.nivel36.laie.web.view.job;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureDto;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class ViewJobView extends AbstractJobView {

	private static final long serialVersionUID = 8333996053828223314L;

	private static final Logger logger = LoggerFactory.getLogger(ViewJobView.class);
	
	public static String URL = "/job/view.xhtml";

	private List<JobCandidatureDto> jobCandidatures;

	@Inject
	private transient JobCandidatureService jobCandidatureService;

	@PostConstruct
	public void init() {
		final String uid = this.getValueFromGetParameters("jobOfferUid", true);
		this.jobOffer = this.jobOfferService.findJobOfferByUid(uid);
		if (this.jobOffer == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("JobOffer {} init", this.jobOffer);
		this.jobCandidatures = this.jobCandidatureService.findJobOffersJobCanditures(this.jobOffer.getUid(),
				Page.ALL_RESULTS);
	}

	public void export() {
		logger.debug("Export job action performed");
	}

	public List<JobCandidatureDto> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public boolean isEditable() {
		return true;
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		Objects.requireNonNull(jobCandidatureService);
		this.jobCandidatureService = jobCandidatureService;
	}
}
package es.nivel36.laie.web.view.job;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class ViewJobView extends AbstractJobView {

	private static final long serialVersionUID = 5265865623840819856L;

	private static final Logger logger = LoggerFactory.getLogger(ViewJobView.class);
	
	public static String URL = "/job/view.xhtml";

	private List<JobCandidature> jobCandidatures;

	@PostConstruct
	public void init() {
		final String Id = this.getValueFromGetParameters("job", true);
		this.jobOffer = this.jobOfferService.findJobOfferById(Id);
		if (this.jobOffer == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("JobOffer {} init", this.jobOffer);
		this.jobCandidatures = new ArrayList<>(jobOffer.getJobCandidatures());
	}

	public void export() {
		logger.debug("Export job action performed");
	}

	public List<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public boolean isEditable() {
		return true;
	}
}
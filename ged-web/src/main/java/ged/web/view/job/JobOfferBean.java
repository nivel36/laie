package ged.web.view.job;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.CloseDialogListener;
import ged.web.core.PageNotFoundException;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobOfferBean extends AbstractBean implements CloseDialogListener {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -1200840678252895578L;

	private List<Candidate> candidates;

	@Inject
	private CandidateService candidateService;

	private JobOffer jobOffer;

	@Inject
	private transient JobOfferService jobService;

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	private Long getJoBofferIdFromGetParameter() {
		try {
			final String jobOfferIdValue = this.getValueFromGetParameters("jobOfferId");
			if (jobOfferIdValue == null) {
				logger.error("JobOfferId is null");
				throw new PageNotFoundException();
			}
			return Long.parseLong(jobOfferIdValue);
		}
		catch (final NumberFormatException e) {
			logger.error("JobOfferId is not a number");
			throw new PageNotFoundException();
		}
	}

	@PostConstruct
	public void init() {
		logger.trace("JobOfferBean Init");
		final Long jobOfferId = this.getJoBofferIdFromGetParameter();
		this.jobOffer = this.jobService.find(jobOfferId);
		if (this.jobOffer == null) {
			throw new PageNotFoundException("Bad jobOfferId");
		}
		this.candidates = this.candidateService.findCandidatesByJobOffer(this.jobOffer);
	}

	// boolean -> is[name]
	public boolean isUserHasPermissionToEditJobOffer() {
		return this.userHasPermissionToEdit(this.jobOffer);
	}

	@Override
	public void onCloseDialog(final Object value) {
		this.jobOffer = (JobOffer) value;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
}
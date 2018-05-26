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
public class JobOfferBean extends AbstractBean {

	private class CandidateSelectDialogCloseEvent implements CloseDialogListener {

		private final JobOfferBean parent;

		public CandidateSelectDialogCloseEvent(final JobOfferBean parent) {
			this.parent = parent;
		}

		@Override
		public void onCloseDialog(final Object value) {
			@SuppressWarnings("unchecked")
			final List<Candidate> selectedCandidates = (List<Candidate>) value;
			for (final Candidate candidate : selectedCandidates) {
				this.parent.jobService.addJobCandidature(this.parent.jobOffer, candidate);
				this.parent.candidates.add(candidate);
			}
		}
	}

	private class JobOfferDialogCloseEvent implements CloseDialogListener {

		private final JobOfferBean parent;

		public JobOfferDialogCloseEvent(final JobOfferBean parent) {
			this.parent = parent;
		}

		@Override
		public void onCloseDialog(final Object value) {
			this.parent.jobOffer = ((JobOffer) value);
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -1200840678252895578L;

	private List<Candidate> candidates;

	private final CandidateSelectDialogCloseEvent candidateSelectDialogCloseEvent = new CandidateSelectDialogCloseEvent(this);

	@Inject
	private CandidateService candidateService;

	private JobOffer jobOffer;

	private final JobOfferDialogCloseEvent jobOfferDialogCloseEvent = new JobOfferDialogCloseEvent(this);

	@Inject
	private transient JobOfferService jobService;

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public CandidateSelectDialogCloseEvent getCandidateSelectDialogCloseEvent() {
		return this.candidateSelectDialogCloseEvent;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public JobOfferDialogCloseEvent getJobOfferDialogCloseEvent() {
		return this.jobOfferDialogCloseEvent;
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

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
}
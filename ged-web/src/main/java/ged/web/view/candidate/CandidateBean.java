package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.Address;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.CloseDialogListener;
import ged.web.core.PageNotFoundException;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CandidateBean extends AbstractBean implements CloseDialogListener {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1577879781927493283L;

	private Candidate candidate;

	@Inject
	private transient CandidateService candidateService;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	private final List<String> tags = new ArrayList<>();

	public Candidate getCandidate() {
		return this.candidate;
	}

	private Long getCandidateIdFromGetParameter() {
		try {
			final String candidateIdValue = this.getValueFromGetParameters("candidateId");
			if (candidateIdValue == null) {
				logger.error("CandidateId is null");
				throw new PageNotFoundException();
			}
			return Long.parseLong(candidateIdValue);
		}
		catch (final NumberFormatException e) {
			logger.error("CandidateId is not a number");
			throw new PageNotFoundException();
		}
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public List<String> getTags() {
		return this.tags;
	}

	@PostConstruct
	public void init() {
		logger.trace("CandidateBean init");
		final Long candidateId = this.getCandidateIdFromGetParameter();
		this.candidate = this.candidateService.find(candidateId);
		if (this.candidate == null) {
			throw new PageNotFoundException();
		}
		if (this.candidate.getAddress() == null) {
			this.candidate.setAddress(new Address());
		}
		for (final Tag tag : this.candidate.getTags()) {
			this.tags.add(tag.getLabel());
		}
		this.jobOffers = this.jobOfferService.findJobOffersByCandidate(this.candidate);
	}

	@Override
	public void onCloseDialog(final Object value) {
		@SuppressWarnings("unchecked")
		final List<JobOffer> selectedJobOffers = (List<JobOffer>) value;
		for (final JobOffer jobOffer : selectedJobOffers) {
			this.jobOfferService.addJobCandidature(jobOffer, this.candidate);
			this.jobOffers.add(jobOffer);
		}
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}
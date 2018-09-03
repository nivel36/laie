package ged.web.view.job;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.PageNotFoundException;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobOfferBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -1200840678252895578L;

	private List<Candidate> candidates;

	@Inject
	private transient CandidateService candidateService;

	private JobOffer jobOffer;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Param(required = true)
	@Inject
	private Long jobOfferId;

	@Inject
	private transient JobOfferService jobService;

	public void editJobOffer() {
		this.putValueToFlash("jobOffer", this.jobOffer);

	}

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	@PostConstruct
	public void init() {
		logger.trace("JobOfferBean Init");
		this.jobOffer = this.jobService.find(this.jobOfferId);
		if (this.jobOffer == null) {
			throw new PageNotFoundException("Bad jobOfferId");
		}
		this.candidates = this.candidateService.findCandidatesByJobOffer(this.jobOffer);
	}

	// boolean -> is[name]
	public boolean isUserHasPermissionToEditJobOffer() {
		return sessionUser.hasPermissionToEdit(this.jobOffer);
	}

	public void onCloseSelectCandidateDialog(final SelectEvent event) {
		@SuppressWarnings("unchecked")
		final List<Candidate> selectedCandidates = (List<Candidate>) event.getObject();
		for (final Candidate candidate : selectedCandidates) {
			this.jobService.addJobCandidature(this.jobOffer, candidate);
			this.candidates.add(candidate);
		}
	}

	public void openSelectCandidatesDialog() {
		this.openBigDialog("/faces/candidate/candidateSelectDialog");
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
}
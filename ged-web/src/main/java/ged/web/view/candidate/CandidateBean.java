package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
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
import ged.ejb.core.Address;
import ged.ejb.core.tag.Tag;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.PageNotFoundException;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CandidateBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1577879781927493283L;

	private Candidate candidate;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@Param(required = true)
	private Long candidateId;

	@Inject
	private transient CandidateService candidateService;

	private List<Curriculum> curricula;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	private final List<String> tags = new ArrayList<>();

	public void editCandidate() {
		this.putValueToFlash("candidate", this.candidate);
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public List<Curriculum> getCurricula() {
		return this.curricula;
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
		this.candidate = this.candidateService.find(this.candidateId);
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

	public void onCloseCurriculumDialog(final SelectEvent event) {
		final Curriculum curriculumFromDialog = (Curriculum) event.getObject();
		if (curriculumFromDialog != null) {
			this.curricula.add(curriculumFromDialog);
		}
	}

	public void onCloseSelectJobOfferDialog(final SelectEvent event) {
		@SuppressWarnings("unchecked")
		final List<JobOffer> selectedJobOffers = (List<JobOffer>) event.getObject();
		for (final JobOffer jobOffer : selectedJobOffers) {
			this.jobOfferService.addJobCandidature(jobOffer, this.candidate);
			this.jobOffers.add(jobOffer);
		}
	}

	public void openCurriculumDialog() {
		this.openBigDialog("/faces/candidate/curriculumDialog", this.buildDialogParameter("candidateId", String.valueOf(this.candidateId)));
	}

	public void openSelectJobOfferDialog() {
		this.openBigDialog("/faces/jobOffer/jobOfferSelectDialog");
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setCandidateId(final Long candidateId) {
		this.candidateId = candidateId;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}
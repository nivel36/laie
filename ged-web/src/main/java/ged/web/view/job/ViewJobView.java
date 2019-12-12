package ged.web.view.job;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.Page;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.job.candidature.JobCandidatureService;
import ged.ejb.job.offer.JobOffer;
import ged.web.core.IllegalPageStateException;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class ViewJobView extends AbstractView {

	private static final String JOB_OFFER_KEY = "jobOffer";

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private boolean editable;

	private List<JobCandidature> jobCandidatures;

	@Inject
	private transient JobCandidatureService jobCandidatureService;

	@Inject
	@Param(name = "id", required = true)
	private JobOffer jobOffer;

	public String editJobOffer() {
		logger.debug("Edit job offer action performed");
		this.putValueToFlash(JOB_OFFER_KEY, this.jobOffer);
		return PageEnum.JOB_EDIT.getUrl();
	}

	public String editJobOfferState() {
		logger.debug("Edit job offer state action performed");
		this.putValueToFlash(JOB_OFFER_KEY, this.jobOffer);
		return PageEnum.JOB_EDIT_STATE.getUrl();
	}

	public void export() throws IOException {
		logger.debug("Export job action performed");
	}

	private String getCandidateIdAsString(final JobCandidature jobCandidature) {
		return String.valueOf(jobCandidature.getCandidate().getId());
	}

	public List<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
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

	public void onCloseSelectCandidateDialog(final SelectEvent event) {
		final Object eventObject = event.getObject();
		if (eventObject == null) {
			return;
		}
		@SuppressWarnings("unchecked")
		final List<Candidate> selectedCandidates = (List<Candidate>) eventObject;
		final List<JobCandidature> newJobCandidatures = this.jobCandidatureService.addJobCandidatures(this.jobOffer,
				selectedCandidates);
		this.jobCandidatures.addAll(newJobCandidatures);
	}

	public void selectCandidates() {
		logger.debug("Select candidates action performed");

		if (this.jobCandidatures.isEmpty()) {
			this.openBigDialog(PageEnum.CANDIDATE_SELECT.getUrl());
		} else {
			final String candidateIds = this.jobCandidatures.stream().map(this::getCandidateIdAsString)
					.collect(Collectors.joining("|"));
			final Map<String, List<String>> parameters = new HashMap<>();
			parameters.put("jobCandiatesId", Arrays.asList(candidateIds));
			this.openBigDialog(PageEnum.CANDIDATE_SELECT.getUrl(), parameters);
		}
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
}
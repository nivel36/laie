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
import ged.ejb.job.offer.JobCandidature;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.PageNotFoundException;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ViewJobBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -1200840678252895578L;

	private List<JobCandidature> jobCandidatures;

	@Inject
	@Param(name = "id", required = true)
	private JobOffer jobOffer;

	@Inject
	private transient JobOfferService jobService;

	public void editJobOffer() {
		this.putValueToFlash("jobOffer", this.jobOffer);
	}

	public void export() throws IOException {
		logger.debug("Export jobs action performed");
	}

	public List<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	@PostConstruct
	public void init() {
		logger.trace("JobOffer {} init", this.jobOffer);
		if (this.jobOffer == null) {
			throw new PageNotFoundException("Bad jobOfferId");
		}
		this.jobCandidatures = this.jobService.findJobCandituresByJobOffer(this.jobOffer);
	}

	public boolean isEditable() {
		return this.sessionUser.hasPermissionToEdit(this.jobOffer);
	}

	public void onCloseSelectCandidateDialog(final SelectEvent event) {
		@SuppressWarnings("unchecked")
		final List<Candidate> selectedCandidates = (List<Candidate>) event.getObject();
		for (final Candidate candidate : selectedCandidates) {
			final JobCandidature jobCandidature = this.jobService.addJobCandidature(this.jobOffer, candidate);
			this.jobCandidatures.add(jobCandidature);
		}
	}

	public void openSelectCandidatesDialog() {
		if (this.jobCandidatures.size() != 0) {
			final String candidateIds = this.jobCandidatures.stream()
					.map(jc -> String.valueOf(jc.getCandidate().getId())).collect(Collectors.joining("|"));
			final Map<String, List<String>> parameters = new HashMap<>();
			parameters.put("jobCandiatesId", Arrays.asList(candidateIds));
			this.openBigDialog(PageEnum.CANDIDATE_SELECT.getUrl(), parameters);
		} else {
			this.openBigDialog(PageEnum.CANDIDATE_SELECT.getUrl());
		}
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobService(final JobOfferService jobService) {
		this.jobService = jobService;
	}
}
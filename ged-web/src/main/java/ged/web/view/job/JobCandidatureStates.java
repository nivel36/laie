package ged.web.view.job;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.model.Page;
import ged.ejb.job.candidature.JobCandidatureState;
import ged.ejb.job.candidature.JobCandidatureStateService;

@Named
@ApplicationScoped
public class JobCandidatureStates implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<JobCandidatureState> jobCandidatureStates;

	@Inject
	private transient JobCandidatureStateService jobCandidatureStateService;

	public List<JobCandidatureState> getList() {
		return this.jobCandidatureStates;
	}

	@PostConstruct
	public void init() {
		this.jobCandidatureStates = this.jobCandidatureStateService.findAll(Page.ALL_RESULTS);
	}

	public void setJobCandidatureStateService(final JobCandidatureStateService jobCandidatureStateService) {
		this.jobCandidatureStateService = jobCandidatureStateService;
	}

}
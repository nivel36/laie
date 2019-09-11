package ged.web.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.job.candidature.JobCandidatureService;
import ged.ejb.job.candidature.JobCandidatureState;

@Named
@ApplicationScoped
public class JobCandidatureStates implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient JobCandidatureService jobCandidatureService;

	private List<JobCandidatureState> jobCandidatureStates;

	public List<JobCandidatureState> getList() {
		return this.jobCandidatureStates;
	}

	@PostConstruct
	public void init() {
		this.jobCandidatureStates = this.jobCandidatureService.findJobCandidatureStates();
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		this.jobCandidatureService = jobCandidatureService;
	}

}
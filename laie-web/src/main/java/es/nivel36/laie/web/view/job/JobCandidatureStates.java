package es.nivel36.laie.web.view.job;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.nivel36.laie.ejb.job.candidature.JobCandidatureState;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureStateService;

@Named
@ApplicationScoped
public class JobCandidatureStates implements Serializable {

	private static final long serialVersionUID = -1367282911973997930L;

	private List<JobCandidatureState> states;

	@Inject
	private transient JobCandidatureStateService jobCandidatureStateService;

	public List<JobCandidatureState> getList() {
		return this.states;
	}

	@PostConstruct
	public void init() {
		this.states = this.jobCandidatureStateService.findAll();
	}

	public void setJobCandidatureStateService(final JobCandidatureStateService jobCandidatureStateService) {
		Objects.requireNonNull(jobCandidatureStateService);
		this.jobCandidatureStateService = jobCandidatureStateService;
	}
}
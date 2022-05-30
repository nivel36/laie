package es.nivel36.laie.ejb.job.candidature;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.laie.ejb.core.model.Page;

@Stateless
public class JobCandidatureStateService {

	private @Inject JobCandidatureStateDao jobCandidatureStateDao;

	public JobCandidatureState findInitialState() {
		return this.jobCandidatureStateDao.findInitialState();
	}

	public void setJobCandidatureStateDao(final JobCandidatureStateDao jobCandidatureStateDao) {
		Objects.requireNonNull(jobCandidatureStateDao);
		this.jobCandidatureStateDao = jobCandidatureStateDao;
	}

	public List<JobCandidatureState> findAll() {
		return jobCandidatureStateDao.findAll(JobCandidatureState.class, Page.ALL_RESULTS);
	}
}

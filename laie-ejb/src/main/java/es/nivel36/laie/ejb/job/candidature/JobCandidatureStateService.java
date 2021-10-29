package es.nivel36.laie.ejb.job.candidature;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.laie.ejb.core.model.Repository;

@Stateless
public class JobCandidatureStateService {

	@Repository
	@Inject
	private JobCandidatureStateDao jobCandidatureStateDao;

	public JobCandidatureState findInitialState() {
		return this.jobCandidatureStateDao.findInitialState();
	}

	public void setJobCandidatureStateDao(final JobCandidatureStateDao jobCandidatureStateDao) {
		Objects.requireNonNull(jobCandidatureStateDao);
		this.jobCandidatureStateDao = jobCandidatureStateDao;
	}
}

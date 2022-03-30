package es.nivel36.laie.ejb.job.candidature;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.core.model.Page;
import es.nivel36.core.model.Repository;

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

	public JobCandidatureStateDto addJobCandidatureState(final String name, final boolean isApproved,
			final boolean isDeclined, final boolean isFirst) {
		final JobCandidatureState jcs = new JobCandidatureState();
		jcs.setApproved(isApproved);
		jcs.setFirst(isFirst);
		jcs.setDeclined(isDeclined);
		this.jobCandidatureStateDao.insert(jcs);
		return new JobCandidatureStateMapper().map(jcs);
	}
	
	public List<JobCandidatureState> findAll() {
		return jobCandidatureStateDao.findAll(JobCandidatureState.class, Page.ALL_RESULTS);
	}
}

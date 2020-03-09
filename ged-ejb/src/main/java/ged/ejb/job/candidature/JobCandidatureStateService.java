package ged.ejb.job.candidature;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class JobCandidatureStateService extends AbstractService<JobCandidatureState> {

	@Repository
	@Inject
	private JobCandidatureStateDao jobCandidatureStateDao;

	public JobCandidatureState findInitialState() {
		return this.jobCandidatureStateDao.findInitialState();
	}

	@Override
	protected AbstractDao<JobCandidatureState> getDao() {
		return this.jobCandidatureStateDao;
	}

	public void setJobCandidatureStateDao(final JobCandidatureStateDao jobCandidatureStateDao) {
		this.jobCandidatureStateDao = jobCandidatureStateDao;
	}
}

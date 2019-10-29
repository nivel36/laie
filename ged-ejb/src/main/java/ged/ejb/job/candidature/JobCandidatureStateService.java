package ged.ejb.job.candidature;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class JobCandidatureStateService  extends AbstractService<JobCandidatureState> {
	
	@Repository
	@Inject
	private JobCandidatureStateDao jobCandidatureStateDao; 

	@Override
	protected AbstractDao<JobCandidatureState> getDao() {
		return jobCandidatureStateDao;
	}

	public void setJobCandidatureStateDao(JobCandidatureStateDao jobCandidatureStateDao) {
		this.jobCandidatureStateDao = jobCandidatureStateDao;
	}
	
	public JobCandidatureState findInitialState() {
		return this.jobCandidatureStateDao.findInitialState();
	}
}

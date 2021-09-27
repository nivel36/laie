package es.nivel36.laie.ejb.job.candidature;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Repository;

@Repository
public class JobCandidatureStateDao extends AbstractDao<JobCandidatureState> {

	public JobCandidatureState findInitialState() {
		return this.findByQuery(JobCandidatureState.class, "JobCandidatureState.findFirstJobCandidatureState");
	}

	@Override
	protected Class<JobCandidatureState> getType() {
		return JobCandidatureState.class;
	}
}

package ged.ejb.job.candidature;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class JobCandidatureStateDao extends AbstractDao<JobCandidatureState> {

	@Override
	protected Class<JobCandidatureState> getType() {
		return JobCandidatureState.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] {};
	}

	public JobCandidatureState findInitialState() {
		return this.findByQuery(JobCandidatureState.class, "JobCandidatureState.findFirstJobCandidatureState");
	}
}

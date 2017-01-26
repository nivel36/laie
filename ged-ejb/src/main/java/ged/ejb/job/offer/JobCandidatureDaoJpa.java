package ged.ejb.job.offer;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.job.JobCandidature;

@Repository
public class JobCandidatureDaoJpa extends AbstractDao<Long, JobCandidature> implements JobCandidatureDao {

	@Inject
	public JobCandidatureDaoJpa(final EntityManager em) {
		super(em);
	}

	@Override
	protected Class<JobCandidature> getType() {
		return JobCandidature.class;
	}

}

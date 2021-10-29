package es.nivel36.laie.ejb.job.candidature;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Repository;

@Repository
public class JobCandidatureStateDao extends AbstractDao {

	public JobCandidatureState findInitialState() {
		final String namedQuery = "JobCandidatureState.findFirstJobCandidatureState";
		return this.findByQuery(JobCandidatureState.class, namedQuery, null);
	}
}

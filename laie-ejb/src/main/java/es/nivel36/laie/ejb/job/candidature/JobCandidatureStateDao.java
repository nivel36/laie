package es.nivel36.laie.ejb.job.candidature;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.util.Parameters;

@Repository
public class JobCandidatureStateDao extends AbstractDao {

	public JobCandidatureState findInitialState() {
		final String namedQuery = "JobCandidatureState.findFirstJobCandidatureState";
		return this.findByQuery(JobCandidatureState.class, namedQuery, null);
	}
	
	public JobCandidatureState findByName(final String name) {
		final String namedQuery = "JobCandidatureState.findByName";
		final Parameters parameters = map("name", name);
		return this.findByQuery(JobCandidatureState.class, namedQuery, parameters);
	}
	
	public void insert(final JobCandidatureState jobCandidatureState) {
		Objects.requireNonNull(jobCandidatureState);
		em.persist(jobCandidatureState);
	}
}

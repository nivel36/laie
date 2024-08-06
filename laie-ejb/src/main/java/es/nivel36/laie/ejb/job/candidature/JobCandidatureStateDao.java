package es.nivel36.laie.ejb.job.candidature;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.util.Parameters;


public class JobCandidatureStateDao extends AbstractDao {

	public JobCandidatureState findInitialState() {
		final String namedQuery = "JobCandidatureState.findFirstJobCandidatureState";
		return this.findByQuery(JobCandidatureState.class, namedQuery, null);
	}
	
	public JobCandidatureState findByName(final String name) {
		Objects.requireNonNull(name);
		final String namedQuery = "JobCandidatureState.findByName";
		final Parameters parameters = map("name", name);
		return this.findByQuery(JobCandidatureState.class, namedQuery, parameters);
	}
	
	public List<JobCandidatureState> findNextStates(final JobCandidatureState currentState) {
		Objects.requireNonNull(currentState);
		final String namedQuery = "JobCandidatureState.findNextStates";
		final Parameters parameters = map("currentState", currentState);
		return this.findByQuery(JobCandidatureState.class, namedQuery, parameters, Page.ALL_RESULTS);
	}
}

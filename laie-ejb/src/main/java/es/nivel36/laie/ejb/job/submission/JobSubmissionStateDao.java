package es.nivel36.laie.ejb.job.submission;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.util.Parameters;


public class JobSubmissionStateDao extends AbstractDao {

	public JobSubmissionState findInitialState() {
		final String namedQuery = "JobSubmissionState.findFirstJobSubmissionState";
		return this.findByQuery(JobSubmissionState.class, namedQuery, null);
	}
	
	public JobSubmissionState findByName(final String name) {
		Objects.requireNonNull(name);
		final String namedQuery = "JobSubmissionState.findByName";
		final Parameters parameters = map("name", name);
		return this.findByQuery(JobSubmissionState.class, namedQuery, parameters);
	}
	
	public List<JobSubmissionState> findNextStates(final JobSubmissionState currentState) {
		Objects.requireNonNull(currentState);
		final String namedQuery = "JobSubmissionState.findNextStates";
		final Parameters parameters = map("currentState", currentState);
		return this.findByQuery(JobSubmissionState.class, namedQuery, parameters, Page.ALL_RESULTS);
	}
}

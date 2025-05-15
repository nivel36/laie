package es.nivel36.laie.ejb.job.submission;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class JobSubmissionStateDao extends AbstractDao {
	
	public JobSubmissionState findInitialState() {
		final String jpql = """
				SELECT j
				FROM JobSubmissionState j
				WHERE j.first = true
				""";
		final TypedQuery<JobSubmissionState> query = this.em.createQuery(jpql, JobSubmissionState.class);
		return query.getSingleResult();
	}

	public JobSubmissionState findByName(final String name) {
		Objects.requireNonNull(name);
		final String jpql = """
				SELECT j
				FROM JobSubmissionState j
				WHERE j.name = :name
				""";
		final TypedQuery<JobSubmissionState> query = this.em.createQuery(jpql, JobSubmissionState.class);
		query.setParameter("name", name);
		return query.getSingleResult();
	}

	public List<JobSubmissionState> findNextStates(final JobSubmissionState currentState) {
		Objects.requireNonNull(currentState);
		final String jpql = """
				SELECT t.destinationState
				FROM Transition t
				WHERE t.originState = :currentState
				""";
		final TypedQuery<JobSubmissionState> query = this.em.createQuery(jpql, JobSubmissionState.class);
		query.setParameter("currentState", currentState);
		return query.getResultList();
	}
}

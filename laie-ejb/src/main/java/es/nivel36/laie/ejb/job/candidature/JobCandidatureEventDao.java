package es.nivel36.laie.ejb.job.candidature;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.util.Parameters;

public class JobCandidatureEventDao extends AbstractDao {

	public void addJobCandidatureEvent(final JobCandidatureEvent jobCandidatureEvent) {
		Objects.requireNonNull(jobCandidatureEvent);
		this.em.persist(jobCandidatureEvent);
	}

	public JobCandidatureEvent findJobCandidatureEventById(final long id) {
		if (id <= 0) {
			throw new IllegalArgumentException();
		}
		return this.em.find(JobCandidatureEvent.class, id);
	}
	
	public List<JobCandidatureEvent> findAll(final JobCandidature jobCandidature, final Page page) {
		Objects.requireNonNull(jobCandidature);final String namedQuery = "JobCandidatureEvent.findAll";
		final Parameters parameters = map("jobCandidature", jobCandidature);
		return this.findByQuery(JobCandidatureEvent.class, namedQuery, parameters, page);
	}
}

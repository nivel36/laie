package es.nivel36.laie.ejb.job.submission;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.util.Parameters;
import es.nivel36.laie.ejb.job.offer.JobOffer;

public class JobSubmissionEventDao extends AbstractDao {

	public void addJobSubmissionEvent(final JobSubmissionEvent jobSubmissionEvent) {
		Objects.requireNonNull(jobSubmissionEvent);
		this.em.persist(jobSubmissionEvent);
	}

	public JobSubmissionEvent findJobSubmissionEventById(final long id) {
		if (id <= 0) {
			throw new IllegalArgumentException();
		}
		return this.em.find(JobSubmissionEvent.class, id);
	}

	public List<JobSubmissionEvent> findAll(JobOffer jobOffer, Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String namedQuery = "JobSubmissionEvent.findAllByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(JobSubmissionEvent.class, namedQuery, parameters, page);
	}
	
	public long countAll(JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final String namedQuery = "JobSubmissionEvent.countAllByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(Long.class, namedQuery, parameters);
	}
}

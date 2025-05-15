package es.nivel36.laie.ejb.job.submission;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class JobSubmissionEventDao extends AbstractDao {

	public List<JobSubmissionEvent> findAll(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT j
				FROM JobSubmissionEvent j
				WHERE j.jobSubmission.jobOffer = :jobOffer
				""";
		final TypedQuery<JobSubmissionEvent> query = this.em.createQuery(jpql, JobSubmissionEvent.class);
		query.setParameter("jobOffer", jobOffer);
		this.paginate(page, query);
		return query.getResultList();
	}

	public long countAll(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final String jpql = """
				SELECT COUNT(j)
				FROM JobSubmissionEvent j
				WHERE j.jobSubmission.jobOffer = :jobOffer
				""";
		final TypedQuery<Long> query = this.em.createQuery(jpql, Long.class);
		query.setParameter("jobOffer", jobOffer);
		return query.getSingleResult();
	}
}

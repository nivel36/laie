package es.nivel36.laie.ejb.job.submission;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

public class JobSubmissionDao extends AbstractDao {

	public void delete(final JobOffer jobOffer, final Candidate candidate) {
		final JobSubmission jobSubmission = this.findJobSubmissionByJobOfferAndCandidate(jobOffer, candidate);
		if (jobSubmission == null) {
			throw new EntityNotFoundException(
					String.format("No job submission found for job offer %s and candidate %s", jobOffer, candidate));
		}
		this.em.remove(jobSubmission);
	}

	public JobSubmission findJobSubmission(final long jobSubmissionId) {
		if (jobSubmissionId <= 0) {
			throw new IllegalStateException(
					"Job submission ID must be greater than zero. Received: " + jobSubmissionId);
		}
		return em.find(JobSubmission.class, jobSubmissionId);
	}

	public long countApprovedJobSubmissions(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final String sqlQuery = "SELECT COUNT(j) FROM JobSubmission j LEFT JOIN j.jobOffer WHERE j.jobOffer = :jobOffer AND j.state.approved = true";
		final TypedQuery<Long> query = this.em.createQuery(sqlQuery, Long.class);
		query.setParameter("jobOffer", jobOffer);
		return query.getSingleResult();
	}

	public JobSubmission findJobSubmissionByJobOfferAndCandidate(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		final String sqlQuery = "SELECT j FROM JobSubmission j LEFT JOIN FETCH j.candidate LEFT JOIN FETCH j.jobOffer WHERE j.jobOffer = :jobOffer AND j.candidate = :candidate";
		final TypedQuery<JobSubmission> query = this.em.createQuery(sqlQuery, JobSubmission.class);
		query.setParameter("jobOffer", jobOffer);
		query.setParameter("candidate", candidate);
		return query.getSingleResult();
	}

	public List<JobSubmission> findJobSubmissionsByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		final String sqlQuery = "SELECT j FROM JobSubmission	LEFT JOIN FETCH j.candidate LEFT JOIN FETCH j.jobOffer LEFT JOIN FETCH j.jobOffer.client LEFT JOIN FETCH j.state WHERE j.candidate=:candidate";
		final TypedQuery<JobSubmission> query = this.em.createQuery(sqlQuery, JobSubmission.class);
		query.setParameter("candidate", candidate);
		paginate(page, query);
		return query.getResultList();
	}

	public long countJobSubmissionsByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		final String sqlQuery = "SELECT COUNT(j) FROM JobSubmission j WHERE j.candidate = :candidate ";
		final TypedQuery<Long> query = this.em.createQuery(sqlQuery, Long.class);
		query.setParameter("candidate", candidate);
		return query.getSingleResult();
	}

	public List<JobSubmission> findJobSubmissionsByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String sqlQuery = "SELECT j FROM JobSubmission j LEFT JOIN FETCH j.candidate LEFT JOIN FETCH j.jobOffer WHERE j.jobOffer = :jobOffer";
		final TypedQuery<JobSubmission> query = this.em.createQuery(sqlQuery, JobSubmission.class);
		query.setParameter("jobOffer", jobOffer);
		paginate(page, query);
		return query.getResultList();
	}

	public long countJobSubmissionsByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final String sqlQuery = "SELECT COUNT(j) FROM JobSubmission j WHERE j.jobOffer = :jobOffer";
		final TypedQuery<Long> query = this.em.createQuery(sqlQuery, Long.class);
		query.setParameter("jobOffer", jobOffer);
		return query.getSingleResult();
	}
}

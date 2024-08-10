package es.nivel36.laie.ejb.job.submission;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.util.Parameters;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;

public class JobSubmissionDao extends AbstractDao {

	public void delete(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		final JobSubmission jobSubmission = this.findByJobOfferAndCandidate(jobOffer, candidate);
		this.em.remove(jobSubmission);
	}

	public JobSubmission findJobSubmission(final long jobSubmissionId) {
		if (jobSubmissionId <= 0) {
			throw new IllegalStateException();
		}
		return em.find(JobSubmission.class, jobSubmissionId);
	}

	public List<JobSubmission> findApprovedJobCanditures(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String namedQuery = "JobSubmission.findApprovedByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(JobSubmission.class, namedQuery, parameters, page);
	}

	public JobSubmission findByJobOfferAndCandidate(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		final String namedQuery = "JobSubmission.findByJobOfferAndCandidate";
		final Parameters parameters = map("jobOffer", jobOffer).and("candidate", candidate);
		return this.findByQuery(JobSubmission.class, namedQuery, parameters);
	}

	public List<JobSubmission> findCandidatesJobSubmissions(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		final String namedQuery = "JobSubmission.findByCandidate";
		final Parameters parameters = map("candidate", candidate);
		return this.findByQuery(JobSubmission.class, namedQuery, parameters, page);
	}
	
	public long countCandidatesJobSubmissions(Candidate candidate) {
		Objects.requireNonNull(candidate);
		final String namedQuery = "JobSubmission.countByCandidate";
		final Parameters parameters = map("candidate", candidate);
		return this.findByQuery(Long.class, namedQuery, parameters);
	}

	public List<JobSubmission> findUsersJobSubmissions(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		final String namedQuery = "JobSubmission.findByUser";
		final Parameters parameters = map("user", user);
		return this.findByQuery(JobSubmission.class, namedQuery, parameters, page);
	}

	public List<JobSubmission> findByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String namedQuery = "JobSubmission.findByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(JobSubmission.class, namedQuery, parameters, page);
	}
	
	public long countJobOffersJobCanditures(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final String namedQuery = "JobSubmission.countByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(Long.class, namedQuery, parameters);
	}
}

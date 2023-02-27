package es.nivel36.laie.ejb.job.candidature;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.util.Parameters;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;

public class JobCandidatureDao extends AbstractDao {

	public void delete(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		final JobCandidature jobCandidature = this.findByJobOfferAndCandidate(jobOffer, candidate);
		this.em.remove(jobCandidature);
	}

	public JobCandidature findJobCandidature(final long jobCandidatureId) {
		if (jobCandidatureId <= 0) {
			throw new IllegalStateException();
		}
		return em.find(JobCandidature.class, jobCandidatureId);
	}

	public List<JobCandidature> findApprovedJobCanditures(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String namedQuery = "JobCandidature.findApprovedByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(JobCandidature.class, namedQuery, parameters, page);
	}

	public JobCandidature findByJobOfferAndCandidate(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		final String namedQuery = "JobCandidature.findByJobOfferAndCandidate";
		final Parameters parameters = map("jobOffer", jobOffer).and("candidate", candidate);
		return this.findByQuery(JobCandidature.class, namedQuery, parameters);
	}

	public List<JobCandidature> findCandidatesJobCandidatures(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		final String namedQuery = "JobCandidature.findByCandidate";
		final Parameters parameters = map("candidate", candidate);
		return this.findByQuery(JobCandidature.class, namedQuery, parameters, page);
	}
	
	public long countCandidatesJobCandidatures(Candidate candidate) {
		Objects.requireNonNull(candidate);
		final String namedQuery = "JobCandidature.countByCandidate";
		final Parameters parameters = map("candidate", candidate);
		return this.findByQuery(Long.class, namedQuery, parameters);
	}

	public List<JobCandidature> findUsersJobCandidatures(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		final String namedQuery = "JobCandidature.findByUser";
		final Parameters parameters = map("user", user);
		return this.findByQuery(JobCandidature.class, namedQuery, parameters, page);
	}

	public List<JobCandidature> findJobOffersJobCanditures(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String namedQuery = "JobCandidature.findByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(JobCandidature.class, namedQuery, parameters, page);
	}
	
	public long countJobOffersJobCanditures(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final String namedQuery = "JobCandidature.countByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(Long.class, namedQuery, parameters);
	}
}

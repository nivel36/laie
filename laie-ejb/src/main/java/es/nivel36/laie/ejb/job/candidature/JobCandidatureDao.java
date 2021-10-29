package es.nivel36.laie.ejb.job.candidature;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.util.Parameters;

@Repository
public class JobCandidatureDao extends AbstractDao {

	public void insert(final JobCandidature jobCandidature) {
		Objects.requireNonNull(jobCandidature);
		this.em.persist(jobCandidature);
	}

	public void delete(final String jobOffereUid, final String candidateUid) {
		Objects.requireNonNull(jobOffereUid);
		Objects.requireNonNull(candidateUid);
		final JobCandidature jobCandidature = this.findByJobOfferAndCandidate(jobOffereUid, candidateUid);
		this.em.remove(jobCandidature);
	}

	public List<JobCandidature> findApprovedJobCanditures(final String jobOfferUid, final Page page) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(page);
		final String namedQuery = "JobCandidature.findApprovedByJobOfferUid";
		final Parameters parameters = map("jobOfferUid", jobOfferUid);
		return this.findByQuery(JobCandidature.class, namedQuery, parameters, page);
	}

	public JobCandidature findByJobOfferAndCandidate(final String jobOfferUid, final String candidateUid) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(candidateUid);
		final String namedQuery = "JobCandidature.findByJobOfferAndCandidate";
		final Parameters parameters = map("jobOfferUid", jobOfferUid).and("candidateUid", candidateUid);
		return this.findByQuery(JobCandidature.class, namedQuery, parameters);
	}

	public List<JobCandidature> findCandidatesJobCandidatures(final String candidateUid, final Page page) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(page);
		final String namedQuery = "JobCandidature.findByCandidateUid";
		final Parameters parameters = map("candidateUid", candidateUid);
		return this.findByQuery(JobCandidature.class, namedQuery, parameters, page);
	}

	public List<JobCandidature> findUsersJobCandidatures(final String userUid, final Page page) {
		Objects.requireNonNull(userUid);
		Objects.requireNonNull(page);
		final String namedQuery = "JobCandidature.findByUserUid";
		final Parameters parameters = map("userUid", userUid);
		return this.findByQuery(JobCandidature.class, namedQuery, parameters, page);
	}

	public List<JobCandidature> findJobOffersJobCanditures(final String jobOfferUid, final Page page) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(page);
		final String namedQuery = "JobCandidature.findByJobOfferUid";
		final Parameters parameters = map("jobOfferUid", jobOfferUid);
		return this.findByQuery(JobCandidature.class, namedQuery, parameters, page);
	}
}

package ged.ejb.job.candidature;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.user.User;

@Repository
public class JobCandidatureDao extends AbstractDao<JobCandidature> {

	protected boolean existUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(Boolean.class, "JobCandidature.existUid", map("uid", uid));
	}
	
	public List<JobCandidature> findApprovedJobCanditures(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer, "JobOffer can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		return this.findByQuery(JobCandidature.class, "JobCandidature.findApprovedByJobOffer", map("jobOffer", jobOffer), page);
	}
	
	public JobCandidature findByJobOfferAndCandidate(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer, "JobOffer can't be null");
		Objects.requireNonNull(candidate, "Candidate can't be null");
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByJobOfferAndCandidate",
				map("jobOffer", jobOffer).and("candidate", candidate));
	}

	public JobCandidature findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByUid", map("uid", uid));
	}

	public List<JobCandidature> findJobCandidatures(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate, "Candidate can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByCandidate", map("candidate", candidate),
				page);
	}

	public List<JobCandidature> findJobCandidatures(final User user, final Page page) {
		Objects.requireNonNull(user, "User can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByUser", map("user", user), page);
	}

	public List<JobCandidature> findJobCanditures(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer, "JobOffer can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByJobOffer", map("jobOffer", jobOffer), page);
	}
	
	@Override
	protected Class<JobCandidature> getType() {
		return JobCandidature.class;
	}
	
	@Override
	protected void preInsert(final JobCandidature jobCandidature) {
		final String base64Id = generateUid();
		jobCandidature.setUid(base64Id);
	}

	@Override
	public String[] searchFields() {
		return new String[] {};
	}
}

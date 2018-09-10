package ged.ejb.job.offer;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class JobCandidatureDao extends AbstractDao<JobCandidature> {

	public List<JobCandidature> findByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByCandidate", map("candidate", candidate), 0, 0);
	}

	public List<JobCandidature> findByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByJobOffer", map("jobOffer", jobOffer), 0, 0);
	}

	public JobCandidature findByJobOfferAndCandidate(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByJobOfferAndCandidate", map("jobOffer", jobOffer).and("candidate", candidate));
	}

	@Override
	protected Class<JobCandidature> getType() {
		return JobCandidature.class;
	}

	@Override
	public List<JobCandidature> search(final String searchText) {
		throw new UnsupportedOperationException();
	}
}

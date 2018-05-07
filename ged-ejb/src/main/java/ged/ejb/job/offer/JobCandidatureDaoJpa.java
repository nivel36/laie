package ged.ejb.job.offer;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class JobCandidatureDaoJpa extends AbstractDaoJpa<JobCandidature> implements JobCandidatureDao {

	@Override
	public List<JobCandidature> findByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByCandidate", map("candidate", candidate), 0, 0);
	}

	@Override
	public List<JobCandidature> findByJobOfferId(final long jobOfferId) {
		if (jobOfferId < 1) {
			throw new IllegalArgumentException();
		}
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByJobOfferId", map("jobOfferId", jobOfferId), 0, 0);
	}

	@Override
	public JobCandidature findByJobOfferIdAndCandidateId(final long jobOfferId, final long candidateId) {
		if (candidateId < 1) {
			throw new IllegalArgumentException();
		}
		if (jobOfferId < 1) {
			throw new IllegalArgumentException();
		}
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByJobOfferIdAndCandidateId",
				map("jobOfferId", jobOfferId).and("candidateId", candidateId));
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

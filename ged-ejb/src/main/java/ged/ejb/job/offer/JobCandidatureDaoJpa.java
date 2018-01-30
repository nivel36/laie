package ged.ejb.job.offer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class JobCandidatureDaoJpa extends AbstractDaoJpa<JobCandidature> implements JobCandidatureDao {

	@Override
	public JobCandidature findByJobOfferAndCandidate(final JobOffer jobOffer, final Candidate candidate) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("jobOffer", jobOffer);
		parameters.put("candidate", candidate);
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByJobOfferAndCandidate", parameters);
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

package ged.ejb.job.offer;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public class JobCandidatureDaoJpa extends AbstractDaoJpa<JobCandidature> implements JobCandidatureDao {

	@Inject
	public JobCandidatureDaoJpa(final EntityManager em) {
		super(em);
	}

	@Override
	public JobCandidature findByJobOfferAndCandidate(final JobOffer jobOffer, final Candidate candidate) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("jobOffer", jobOffer);
		parameters.put("candidate", candidate);
		return findByTypedQuery(JobCandidature.class, "JobCandidature.findByJobOfferAndCandidate", parameters);
	}

	@Override
	protected Class<JobCandidature> getType() {
		return JobCandidature.class;
	}
}

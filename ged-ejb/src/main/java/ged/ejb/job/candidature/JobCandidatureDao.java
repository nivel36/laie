package ged.ejb.job.candidature;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobOffer;

@Repository
public class JobCandidatureDao extends AbstractDao<JobCandidature> {

	public List<JobCandidatureState> findJobCandidatureStates() {
		return this.getPersistenceFacade().findAll(JobCandidatureState.class, Page.ALL);
	}

	public List<JobCandidature> findJobCandidatures(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate, "Candidate can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByCandidate", map("candidate", candidate),
				page);
	}

	public List<JobCandidature> findJobCanditures(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer, "JobOffer can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByJobOffer", map("jobOffer", jobOffer), page);
	}

	public JobCandidature findByJobOfferAndCandidate(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer, "JobOffer can't be null");
		Objects.requireNonNull(candidate, "Candidate can't be null");
		return this.findByQuery(JobCandidature.class, "JobCandidature.findByJobOfferAndCandidate",
				map("jobOffer", jobOffer).and("candidate", candidate));
	}

	public JobCandidatureState findFirstJobCandidatureState() {
		return this.findByQuery(JobCandidatureState.class, "JobCandidatureState.findFirstJobCandidatureState");
	}

	@Override
	protected Class<JobCandidature> getType() {
		return JobCandidature.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] {};
	}
}

package ged.ejb.job.candidature;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.event.JobCandidatureEventService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.user.User;

@Stateless
public class JobCandidatureService extends AbstractService<JobCandidature> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	private JobCandidatureEventService jobCandidatureEventService;
	
	@Inject
	@Repository
	private JobCandidatureDao jobCandidatureDao;
	
	@Inject
	private JobCandidatureStateService jobCandidatureStateService;

	public JobCandidature addJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer, "JobOffer can't be null");
		Objects.requireNonNull(candidate, "Candidate can't be null");
		logger.debug("Add Job Candidature of candidate {} to jobOffer {}", candidate.getFullName(), jobOffer);
		final JobCandidatureState firstJobCandidatureState = this.jobCandidatureStateService.findFirstJobCandidatureState();
		final JobCandidature jobCandidature = new JobCandidature(candidate, jobOffer);
		jobCandidature.setJobCandidatureState(firstJobCandidatureState);
		jobCandidatureEventService.createEvent(jobCandidature);
		return this.save(jobCandidature);
	}

	public List<JobCandidature> addJobCandidatures(final JobOffer jobOffer, final List<Candidate> candidates) {
		Objects.requireNonNull(jobOffer, "JobOffer can't be null");
		Objects.requireNonNull(candidates, "Candidates can't be null");
		logger.debug("Add job candidatures to jobOffer {}", jobOffer);
		final List<JobCandidature> jobCandidatures = new ArrayList<>();
		for (final Candidate candidate : candidates) {
			final JobCandidature jobCandidature = this.addJobCandidature(jobOffer, candidate);
			jobCandidatures.add(jobCandidature);
		}
		return jobCandidatures;
	}

	public List<JobCandidature> findJobCandidatures(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate, "Candidate can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		logger.debug("Find all job candidatures of the candidate {}", candidate);
		return this.jobCandidatureDao.findJobCandidatures(candidate, page);
	}

	public List<JobCandidature> findJobCandidatures(final User user, final Page page) {
		Objects.requireNonNull(user, "User can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		logger.debug("Find job candidatures regarding user {} ({})", user, page);
		return jobCandidatureDao.findJobCandidatures(user, page);
	}

	public List<JobCandidature> findJobCanditures(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer, "JobOffer can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		logger.debug("Find all job candidatures of the job offer {}", jobOffer);
		return this.jobCandidatureDao.findJobCanditures(jobOffer, page);
	}

	@Override
	protected AbstractDao<JobCandidature> getDao() {
		return jobCandidatureDao;
	}

	public void removeJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer, "JobOffer can't be null");
		Objects.requireNonNull(candidate, "Candidate can't be null");
		logger.debug("Remove job candidature of candidate {} to job offer {}", candidate.getFullName(), jobOffer);
		final JobCandidature jobCandidature = this.jobCandidatureDao.findByJobOfferAndCandidate(jobOffer, candidate);
		this.jobCandidatureDao.delete(jobCandidature);
	}
	
	public void setEventService(JobCandidatureEventService jobCandidatureEventService) {
		this.jobCandidatureEventService = jobCandidatureEventService;
	}

	public void setJobCandidatureDao(final JobCandidatureDao jobCandidatureDao) {
		Objects.requireNonNull(jobCandidatureDao, "JobCandidatureDao can't be null");
		this.jobCandidatureDao = jobCandidatureDao;
	}

	public void setJobCandidatureStateService(JobCandidatureStateService jobCandidatureStateService) {
		this.jobCandidatureStateService = jobCandidatureStateService;
	}
}

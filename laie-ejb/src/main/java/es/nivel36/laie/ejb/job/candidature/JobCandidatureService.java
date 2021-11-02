package es.nivel36.laie.ejb.job.candidature;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.job.candidature.event.JobCandidatureCompletedEvent;
import es.nivel36.laie.ejb.job.candidature.event.JobCandidatureCreatedEvent;
import es.nivel36.laie.ejb.job.candidature.event.JobCandidatureStateChangedEvent;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferDao;

@Stateless
public class JobCandidatureService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@JobCandidatureCompletedEvent
	private Event<JobCandidature> completedEvent;

	@Inject
	@JobCandidatureCreatedEvent
	private Event<JobCandidature> createdEvent;

	@Inject
	@JobCandidatureStateChangedEvent
	private Event<JobCandidature> stateChangedEvent;

	@Inject
	@Repository
	private JobCandidatureDao jobCandidatureDao;

	@Inject
	@Repository
	private JobOfferDao jobOfferDao;

	@Inject
	@Repository
	private CandidateDao candidateDao;

	@Inject
	private JobCandidatureStateService jobCandidatureStateService;

	public void addJobCandidature(final String jobOfferUid, final String candidateUid) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(candidateUid);
		final JobOffer jobOffer = jobOfferDao.findByUid(jobOfferUid);
		if (!jobOffer.isOpen()) {
			throw new IllegalStateException("Job offer isn't open");
		}
		final Candidate candidate = candidateDao.findByUid(candidateUid);
		logger.debug("Add Job Candidature of candidate {} to jobOffer {}", candidate.getFullName(), jobOffer);

		final JobCandidatureState firstState = this.jobCandidatureStateService.findInitialState();
		final JobCandidature jobCandidature = new JobCandidature(candidate, jobOffer);
		jobCandidature.setState(firstState);
		this.jobCandidatureDao.insert(jobCandidature);
	}

	public void addJobCandidatures(final String jobOfferUid, final List<String> candidateUids) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(candidateUids);
		for (final String candidateUid : candidateUids) {
			this.addJobCandidature(jobOfferUid, candidateUid);
		}
	}

	public List<JobCandidatureDto> findCandidatesJobCandidatures(final String candidateUid, final Page page) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(page);
		logger.debug("Find all job candidatures of the candidate {}", candidateUid);
		final List<JobCandidature> jobCandidatures = this.jobCandidatureDao.findCandidatesJobCandidatures(candidateUid,
				page);
		return new JobCandidatureMapper().mapList(jobCandidatures);
	}

	public List<JobCandidatureDto> findUsersJobCandidatures(final String userUid, final Page page) {
		Objects.requireNonNull(userUid);
		Objects.requireNonNull(page);
		logger.debug("Find job candidatures regarding user {} ({})", userUid, page);
		final List<JobCandidature> jobCandidatures = this.jobCandidatureDao.findUsersJobCandidatures(userUid, page);
		return new JobCandidatureMapper().mapList(jobCandidatures);
	}

	public List<JobCandidatureDto> findJobOffersJobCanditures(final String jobOfferUid, final Page page) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(page, "Page can't be null");
		logger.debug("Find all job candidatures of the job offer {}", jobOfferUid);
		final List<JobCandidature> jobCandidatures = this.jobCandidatureDao.findJobOffersJobCanditures(jobOfferUid,
				page);
		return new JobCandidatureMapper().mapList(jobCandidatures);
	}

	public void removeJobCandidature(final String jobOfferUid, final String candidateUid) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(candidateUid);
		logger.debug("Remove job candidature of candidate {} to job offer {}", candidateUid, jobOfferUid);
		this.jobCandidatureDao.delete(jobOfferUid, candidateUid);
	}

	public void changeState(final String jobOfferUid, final String candidateUid, final JobCandidatureState newState) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(candidateUid);
		final JobCandidature jobCandidature = jobCandidatureDao.findByJobOfferAndCandidate(jobOfferUid, candidateUid);
		if (jobCandidature.getState().equals(newState)) {
			return;
		}
		logger.trace("State has changed to {}", jobCandidature.getState());
		this.stateChangedEvent.fire(jobCandidature);
		if (newState.isApproved()) {
			logger.trace("Job candidature is approved");
			this.completedEvent.fire(jobCandidature);
		}
	}

	public void setJobCandidatureCompletedEvent(final Event<JobCandidature> jobCandidatureCompletedEvent) {
		Objects.requireNonNull(jobCandidatureCompletedEvent);
		this.completedEvent = jobCandidatureCompletedEvent;
	}

	public void setJobCandidatureCreatedEvent(final Event<JobCandidature> jobCandidatureCreatedEvent) {
		Objects.requireNonNull(jobCandidatureCreatedEvent);
		this.createdEvent = jobCandidatureCreatedEvent;
	}

	public void setJobCandidatureDao(final JobCandidatureDao jobCandidatureDao) {
		Objects.requireNonNull(jobCandidatureDao);
		this.jobCandidatureDao = jobCandidatureDao;
	}

	public void setJobCandidatureStateChangedEvent(final Event<JobCandidature> jobCandidatureStateChangedEvent) {
		Objects.requireNonNull(jobCandidatureStateChangedEvent);
		this.stateChangedEvent = jobCandidatureStateChangedEvent;
	}

	public void setJobCandidatureStateService(final JobCandidatureStateService jobCandidatureStateService) {
		Objects.requireNonNull(jobCandidatureStateService);
		this.jobCandidatureStateService = jobCandidatureStateService;
	}
}
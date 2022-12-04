package es.nivel36.laie.ejb.job.candidature;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.candidature.event.JobCandidatureCompletedEvent;
import es.nivel36.laie.ejb.job.candidature.event.JobCandidatureCreatedEvent;
import es.nivel36.laie.ejb.job.candidature.event.JobCandidatureStateChangedEvent;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class JobCandidatureService {

	private static final Logger logger = LoggerFactory.getLogger(JobCandidatureService.class);

	private @Inject @JobCandidatureCompletedEvent Event<JobCandidature> completedEvent;

	private @Inject @JobCandidatureCreatedEvent Event<JobCandidature> createdEvent;

	private @Inject @JobCandidatureStateChangedEvent Event<JobCandidature> stateChangedEvent;

	private @Inject JobCandidatureDao jobCandidatureDao;

	private @Inject JobCandidatureStateService jobCandidatureStateService;

	private @Inject JobCandidatureEventDao jobCandidatureEventDao;

	public void addJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		if (!jobOffer.isOpen()) {
			throw new IllegalStateException("Job offer isn't open");
		}
		logger.debug("Add Job Candidature of candidate {} to jobOffer {}", candidate.getFullName(), jobOffer);
		final JobCandidatureState firstState = this.jobCandidatureStateService.findInitialState();
		final JobCandidature jobCandidature = new JobCandidature(candidate, jobOffer);
		jobCandidature.setState(firstState);
		createdEvent.fireAsync(jobCandidature);
		this.jobCandidatureDao.insert(jobCandidature);
	}

	public void addJobCandidatureEvent(final JobCandidatureEvent event) {
		Objects.requireNonNull(event);
		final JobCandidature jobCandidature = event.getJobCandidature();
		if (!event.getState().equals(jobCandidature.getState())) {
			logger.trace("State of {} has changed from {} to {}", jobCandidature, jobCandidature.getState(),
					event.getState());
			jobCandidature.setState(event.getState());
			stateChangedEvent.fireAsync(jobCandidature);
			if (event.getState().isApproved()) {
				logger.trace("Job candidature is approved");
				completedEvent.fireAsync(jobCandidature);
			}
		}
		this.jobCandidatureDao.update(jobCandidature);
		this.jobCandidatureEventDao.addJobCandidatureEvent(event);
	}

	public void addJobCandidatures(final JobOffer jobOffer, final List<Candidate> candidates) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidates);
		for (final Candidate candidate : candidates) {
			this.addJobCandidature(jobOffer, candidate);
		}
	}

	public JobCandidature findJobCandidature(final long jobCandidatureId) {
		logger.debug("Find job candidature with id {}", jobCandidatureId);
		return this.jobCandidatureDao.findJobCandidature(jobCandidatureId);
	}

	public JobCandidature findJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(jobOffer);
		logger.debug("Find job candidature of the candidate {} in the job offer {}", candidate, jobOffer);
		return this.jobCandidatureDao.findByJobOfferAndCandidate(jobOffer, candidate);
	}

	public List<JobCandidature> findCandidatesJobCandidatures(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		logger.debug("Find all job candidatures of the candidate {}", candidate);
		return this.jobCandidatureDao.findCandidatesJobCandidatures(candidate, page);
	}

	public List<JobCandidature> findUsersJobCandidatures(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		logger.debug("Find job candidatures regarding user {} ({})", user, page);
		return this.jobCandidatureDao.findUsersJobCandidatures(user, page);
	}

	public List<JobCandidature> findJobOffersJobCanditures(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page, "Page can't be null");
		logger.debug("Find all job candidatures of the job offer {}", jobOffer);
		return this.jobCandidatureDao.findJobOffersJobCanditures(jobOffer, page);
	}
	
	public List<JobCandidatureEvent> findJobCandidatureEvents(JobOffer jobOffer, Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		logger.debug("Find all job candidature events of the job candidature {}", jobOffer);
		return this.jobCandidatureEventDao.findAll(jobOffer, page);
	}
	
	public long countJobCandidatureEvents(JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Count all job candidature events of the job candidature {}", jobOffer);
		return this.jobCandidatureEventDao.countAll(jobOffer);
	}

	public JobCandidatureEvent findJobCandidatureEvent(long id) {
		logger.debug("Find job candidature event {}", id);
		return this.jobCandidatureEventDao.findJobCandidatureEventById(id);
	}

	public void removeJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		logger.debug("Remove job candidature of candidate {} to job offer {}", candidate, jobOffer);
		this.jobCandidatureDao.delete(jobOffer, candidate);
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

	public void setJobCandidatureEventDao(final JobCandidatureEventDao jobCandidatureEventDao) {
		Objects.requireNonNull(jobCandidatureEventDao);
		this.jobCandidatureEventDao = jobCandidatureEventDao;
	}
}
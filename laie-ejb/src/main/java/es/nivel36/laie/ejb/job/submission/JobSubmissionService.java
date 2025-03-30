package es.nivel36.laie.ejb.job.submission;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.submission.event.JobSubmissionCompletedEvent;
import es.nivel36.laie.ejb.job.submission.event.JobSubmissionCreatedEvent;
import es.nivel36.laie.ejb.job.submission.event.JobSubmissionStateChangedEvent;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Stateless
public class JobSubmissionService {

	private static final Logger logger = LoggerFactory.getLogger(JobSubmissionService.class);

	private @Inject @JobSubmissionCompletedEvent Event<JobSubmission> completedEvent;
	private @Inject @JobSubmissionCreatedEvent Event<JobSubmission> createdEvent;
	private @Inject @JobSubmissionStateChangedEvent Event<JobSubmission> stateChangedEvent;
	private @Inject JobSubmissionDao jobSubmissionDao;
	private @Inject JobSubmissionStateService jobSubmissionStateService;
	private @Inject JobSubmissionEventDao jobSubmissionEventDao;

	public void addJobSubmission(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		if (!jobOffer.isOpen()) {
			throw new IllegalStateException("Job offer is not open");
		}
		logger.debug("Adding job submission for candidate {} to job offer {}", candidate.getFullName(), jobOffer);
		final JobSubmissionState firstState = this.jobSubmissionStateService.findInitialState();
		final JobSubmission jobSubmission = new JobSubmission(candidate, jobOffer, firstState);
		this.jobSubmissionDao.insert(jobSubmission);
		this.createdEvent.fire(jobSubmission);
	}
	
	public void addJobSubmissions(final JobOffer jobOffer, final List<Candidate> candidates) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidates);
		for (final Candidate candidate : candidates) {
			this.addJobSubmission(jobOffer, candidate);
		}
	}
	
	public void removeJobSubmission(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		logger.debug("Removing job submission for candidate {} from job offer {}", candidate, jobOffer);
		this.jobSubmissionDao.delete(jobOffer, candidate);
	}

	public JobSubmission findJobSubmissionById(final long jobSubmissionId) {
		logger.debug("Finding job submission with id {}", jobSubmissionId);
		return this.jobSubmissionDao.findJobSubmission(jobSubmissionId);
	}
	
	public List<JobSubmission> findJobSubmissionsByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		logger.debug("Finding all job submissions for job offer {}", jobOffer);
		return this.jobSubmissionDao.findJobSubmissionsByJobOffer(jobOffer, page);
	}
	
	public long countJobSubmissionsByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Counting all job submissions for job offer {}", jobOffer);
		return this.jobSubmissionDao.countJobSubmissionsByJobOffer(jobOffer);
	}

	public List<JobSubmission> findJobSubmissionsByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		logger.debug("Finding all job submissions for candidate {}", candidate);
		return this.jobSubmissionDao.findJobSubmissionsByCandidate(candidate, page);
	}

	public long countJobSubmissionsByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Counting all job submissions for candidate {}", candidate);
		return this.jobSubmissionDao.countJobSubmissionsByCandidate(candidate);
	}

	public long countApprovedJobSubmissions(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Counting approved job submissions for job offer {}", jobOffer);
		return this.jobSubmissionDao.countApprovedJobSubmissions(jobOffer);
	}
	
	public void addJobSubmissionEvent(final JobSubmissionEvent event) {
	    Objects.requireNonNull(event);
	    logger.debug("Adding job submission event {}", event);
	    final JobSubmission jobSubmission = event.getJobSubmission();
	    boolean stateChanged = false;
	    
		final JobSubmissionState newState = event.getState();
		final JobSubmissionState jobState = jobSubmission.getState();
		if (!newState.equals(jobState)) {
	        logger.trace("Job submission {} state changed from {} to {}",
	                     jobSubmission, jobState, newState);
	        jobSubmission.setState(newState);
	        stateChanged = true;
	    }
	    
	    this.jobSubmissionDao.update(jobSubmission);
	    this.jobSubmissionEventDao.addJobSubmissionEvent(event);
	    
	    if (stateChanged) {
	        this.stateChangedEvent.fire(jobSubmission);
	        if (newState.isApproved()) {
	            logger.trace("Job submission is approved");
	            this.completedEvent.fire(jobSubmission);
	        }
	    }
	}

	public JobSubmissionEvent findJobSubmissionEvent(final long id) {
		logger.debug("Finding job submission event with id {}", id);
		return this.jobSubmissionEventDao.findJobSubmissionEventById(id);
	}
	
	public List<JobSubmissionEvent> findJobSubmissionEvents(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		logger.debug("Finding all job submission events for job offer {}", jobOffer);
		return this.jobSubmissionEventDao.findAll(jobOffer, page);
	}

	public long countJobSubmissionEvents(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Counting all job submission events for job offer {}", jobOffer);
		return this.jobSubmissionEventDao.countAll(jobOffer);
	}

	public void setJobSubmissionCompletedEvent(final Event<JobSubmission> jobSubmissionCompletedEvent) {
		this.completedEvent = Objects.requireNonNull(jobSubmissionCompletedEvent);
	}

	public void setJobSubmissionCreatedEvent(final Event<JobSubmission> jobSubmissionCreatedEvent) {
		this.createdEvent = Objects.requireNonNull(jobSubmissionCreatedEvent);
	}
	
	public void setJobSubmissionStateChangedEvent(final Event<JobSubmission> jobSubmissionStateChangedEvent) {
		this.stateChangedEvent = Objects.requireNonNull(jobSubmissionStateChangedEvent);
	}

	public void setJobSubmissionDao(final JobSubmissionDao jobSubmissionDao) {
		this.jobSubmissionDao = Objects.requireNonNull(jobSubmissionDao);
	}
	
	public void setJobSubmissionEventDao(final JobSubmissionEventDao jobSubmissionEventDao) {
		this.jobSubmissionEventDao = Objects.requireNonNull(jobSubmissionEventDao);
	}

	public void setJobSubmissionStateService(final JobSubmissionStateService jobSubmissionStateService) {
		this.jobSubmissionStateService = Objects.requireNonNull(jobSubmissionStateService);
	}
}

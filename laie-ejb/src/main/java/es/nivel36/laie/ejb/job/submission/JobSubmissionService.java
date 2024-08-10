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
import es.nivel36.laie.ejb.user.User;
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
			throw new IllegalStateException("Job offer isn't open");
		}
		logger.debug("Add Job Submission of candidate {} to jobOffer {}", candidate.getFullName(), jobOffer);
		final JobSubmissionState firstState = this.jobSubmissionStateService.findInitialState();
		final JobSubmission jobSubmission = new JobSubmission(candidate, jobOffer);
		jobSubmission.setState(firstState);
		createdEvent.fireAsync(jobSubmission);
		this.jobSubmissionDao.insert(jobSubmission);
	}

	public void addJobSubmissionEvent(final JobSubmissionEvent event) {
		Objects.requireNonNull(event);
		final JobSubmission jobSubmission = event.getJobSubmission();
		if (!event.getState().equals(jobSubmission.getState())) {
			logger.trace("State of {} has changed from {} to {}", jobSubmission, jobSubmission.getState(),
					event.getState());
			jobSubmission.setState(event.getState());
			stateChangedEvent.fireAsync(jobSubmission);
			if (event.getState().isApproved()) {
				logger.trace("Job jobSubmission is approved");
				completedEvent.fireAsync(jobSubmission);
			}
		}
		this.jobSubmissionDao.update(jobSubmission);
		this.jobSubmissionEventDao.addJobSubmissionEvent(event);
	}

	public void addJobSubmissions(final JobOffer jobOffer, final List<Candidate> candidates) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidates);
		for (final Candidate candidate : candidates) {
			this.addJobSubmission(jobOffer, candidate);
		}
	}

	public JobSubmission findJobSubmission(final long jobSubmissionId) {
		logger.debug("Find job jobSubmission with id {}", jobSubmissionId);
		return this.jobSubmissionDao.findJobSubmission(jobSubmissionId);
	}

	public JobSubmission findJobSubmission(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(jobOffer);
		logger.debug("Find job jobSubmission of the candidate {} in the job offer {}", candidate, jobOffer);
		return this.jobSubmissionDao.findByJobOfferAndCandidate(jobOffer, candidate);
	}

	public List<JobSubmission> findCandidatesJobSubmissions(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		logger.debug("Find all job jobSubmissions of the candidate {}", candidate);
		return this.jobSubmissionDao.findCandidatesJobSubmissions(candidate, page);
	}
	
	public long countCandidatesJobSubmissions(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Count all job jobSubmissions of the candidate {}", candidate);
		return this.jobSubmissionDao.countCandidatesJobSubmissions(candidate);
	}

	public List<JobSubmission> findUsersJobSubmissions(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		logger.debug("Find job jobSubmissions regarding user {} ({})", user, page);
		return this.jobSubmissionDao.findUsersJobSubmissions(user, page);
	}

	public List<JobSubmission> findJobSubmissionsByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page, "Page can't be null");
		logger.debug("Find all job jobSubmissions of the job offer {}", jobOffer);
		return this.jobSubmissionDao.findByJobOffer(jobOffer, page);
	}
	
	public long countJobOffersJobCanditures(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Count all job jobSubmissions of the job offer {}", jobOffer);
		return this.jobSubmissionDao.countJobOffersJobCanditures(jobOffer);
	}
	
	public List<JobSubmissionEvent> findJobSubmissionEvents(JobOffer jobOffer, Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		logger.debug("Find all job jobSubmission events of the job jobSubmission {}", jobOffer);
		return this.jobSubmissionEventDao.findAll(jobOffer, page);
	}
	
	public long countJobSubmissionEvents(JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Count all job jobSubmission events of the job jobSubmission {}", jobOffer);
		return this.jobSubmissionEventDao.countAll(jobOffer);
	}

	public JobSubmissionEvent findJobSubmissionEvent(long id) {
		logger.debug("Find job jobSubmission event {}", id);
		return this.jobSubmissionEventDao.findJobSubmissionEventById(id);
	}

	public void removeJobSubmission(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		logger.debug("Remove job jobSubmission of candidate {} to job offer {}", candidate, jobOffer);
		this.jobSubmissionDao.delete(jobOffer, candidate);
	}

	public void setJobSubmissionCompletedEvent(final Event<JobSubmission> jobSubmissionCompletedEvent) {
		Objects.requireNonNull(jobSubmissionCompletedEvent);
		this.completedEvent = jobSubmissionCompletedEvent;
	}

	public void setJobSubmissionCreatedEvent(final Event<JobSubmission> jobSubmissionCreatedEvent) {
		Objects.requireNonNull(jobSubmissionCreatedEvent);
		this.createdEvent = jobSubmissionCreatedEvent;
	}

	public void setJobSubmissionDao(final JobSubmissionDao jobSubmissionDao) {
		Objects.requireNonNull(jobSubmissionDao);
		this.jobSubmissionDao = jobSubmissionDao;
	}

	public void setJobSubmissionStateChangedEvent(final Event<JobSubmission> jobSubmissionStateChangedEvent) {
		Objects.requireNonNull(jobSubmissionStateChangedEvent);
		this.stateChangedEvent = jobSubmissionStateChangedEvent;
	}

	public void setJobSubmissionStateService(final JobSubmissionStateService jobSubmissionStateService) {
		Objects.requireNonNull(jobSubmissionStateService);
		this.jobSubmissionStateService = jobSubmissionStateService;
	}

	public void setJobSubmissionEventDao(final JobSubmissionEventDao jobSubmissionEventDao) {
		Objects.requireNonNull(jobSubmissionEventDao);
		this.jobSubmissionEventDao = jobSubmissionEventDao;
	}
}
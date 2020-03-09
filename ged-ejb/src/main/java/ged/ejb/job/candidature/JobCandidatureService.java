package ged.ejb.job.candidature;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.job.candidature.event.JobCandidatureCompletedEvent;
import ged.ejb.job.candidature.event.JobCandidatureCreatedEvent;
import ged.ejb.job.candidature.event.JobCandidatureStateChangedEvent;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.user.User;

@Stateless
public class JobCandidatureService extends AbstractService<JobCandidature> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@JobCandidatureCompletedEvent
	private Event<JobCandidature> completedEvent;

	@Inject
	@JobCandidatureCreatedEvent
	private Event<JobCandidature> createdEvent;

	@Inject
	@Repository
	private JobCandidatureDao jobCandidatureDao;

	@Inject
	private JobCandidatureStateService jobCandidatureStateService;

	@Inject
	@JobCandidatureStateChangedEvent
	private Event<JobCandidature> stateChangedEvent;

	public JobCandidature addJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		if (!jobOffer.isOpen()) {
			throw new IllegalStateException("Job offer isn't open");
		}
		logger.debug("Add Job Candidature of candidate {} to jobOffer {}", candidate.getFullName(), jobOffer);

		final JobCandidatureState firstState = this.jobCandidatureStateService.findInitialState();
		final JobCandidature jobCandidature = new JobCandidature(candidate, jobOffer);
		jobCandidature.setState(firstState);
		return this.save(jobCandidature);
	}

	public List<JobCandidature> addJobCandidatures(final JobOffer jobOffer, final List<Candidate> candidates) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidates);
		if (!jobOffer.isOpen()) {
			throw new IllegalStateException("Job offer isn't open");
		}
		logger.debug("Add job candidatures to jobOffer {}", jobOffer);

		final List<JobCandidature> jobCandidatures = new ArrayList<>();
		for (final Candidate candidate : candidates) {
			final JobCandidature jobCandidature = this.addJobCandidature(jobOffer, candidate);
			jobCandidatures.add(jobCandidature);
		}
		return jobCandidatures;
	}

	public List<JobCandidature> findApprovedJobCanditures(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		logger.debug("Find all approved  job candidatures of the job offer {}", jobOffer);
		return this.jobCandidatureDao.findJobCanditures(jobOffer, page);
	}

	public JobCandidature findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.jobCandidatureDao.findByUid(uid);
	}

	public List<JobCandidature> findJobCandidatures(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		logger.debug("Find all job candidatures of the candidate {}", candidate);

		return this.jobCandidatureDao.findJobCandidatures(candidate, page);
	}

	public List<JobCandidature> findJobCandidatures(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		logger.debug("Find job candidatures regarding user {} ({})", user, page);

		return this.jobCandidatureDao.findJobCandidatures(user, page);
	}

	public List<JobCandidature> findJobCanditures(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page, "Page can't be null");
		logger.debug("Find all job candidatures of the job offer {}", jobOffer);

		return this.jobCandidatureDao.findJobCanditures(jobOffer, page);
	}

	@Override
	protected AbstractDao<JobCandidature> getDao() {
		return this.jobCandidatureDao;
	}

	public boolean hasStateChanged(final JobCandidature jobCandidature) {
		final JobCandidature previousVersion = this.find(jobCandidature.getId());
		final JobCandidatureState state = jobCandidature.getState();
		return !previousVersion.hasState(state);
	}

	public void removeJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		logger.debug("Remove job candidature of candidate {} to job offer {}", candidate.getFullName(), jobOffer);

		final JobCandidature jobCandidature = this.jobCandidatureDao.findByJobOfferAndCandidate(jobOffer, candidate);
		this.jobCandidatureDao.delete(jobCandidature);
	}

	@Override
	public JobCandidature save(final JobCandidature jobCandidature) {
		Objects.requireNonNull(jobCandidature);
		logger.debug("Save job candidature {}", jobCandidature);

		final boolean newCandidature = jobCandidature.isNew();
		final boolean stateChanged = !newCandidature && this.hasStateChanged(jobCandidature);
		final boolean approved = !stateChanged && jobCandidature.isApproved();

		final JobCandidature savedJobCandidature = super.save(jobCandidature);

		if (newCandidature) {
			logger.trace("Is a new job candidature");
			this.createdEvent.fire(jobCandidature);
		} else {
			if (stateChanged) {
				logger.trace("State has changed to {}", jobCandidature.getState());
				this.stateChangedEvent.fire(jobCandidature);
			}
			if (approved) {
				logger.trace("Job candidature is approved");
				this.completedEvent.fire(jobCandidature);
			}
		}
		return savedJobCandidature;
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
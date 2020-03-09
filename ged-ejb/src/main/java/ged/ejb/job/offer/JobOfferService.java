package ged.ejb.job.offer;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.AbstractIndexedService;
import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.job.candidature.JobCandidatureService;
import ged.ejb.job.candidature.event.JobCandidatureCompletedEvent;
import ged.ejb.job.offer.event.JobOfferCompletedEvent;
import ged.ejb.job.offer.event.JobOfferCreatedEvent;
import ged.ejb.job.offer.event.JobOfferStateChangedEvent;
import ged.ejb.user.User;

@Stateless
public class JobOfferService extends AbstractIndexedService<JobOffer> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@JobOfferCompletedEvent
	private Event<JobOffer> completedEvent;

	@Inject
	@JobOfferCreatedEvent
	private Event<JobOffer> createdEvent;

	@Inject
	private JobCandidatureService jobCandidatureService;

	@Inject
	@Repository
	private JobOfferDao jobOfferDao;

	@Inject
	@Repository
	private JobOfferStateChangeEventDao jobOfferStateChangeEventDao;

	@Inject
	@JobOfferStateChangedEvent
	private Event<JobOffer> stateChangedEvent;

	private void closeJobOffer(final JobOffer jobOffer) {
		jobOffer.setDateClosed(LocalDate.now());
		jobOffer.setState(JobOfferState.CLOSED);
		this.completedEvent.fire(jobOffer);
	}

	public JobOffer findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.jobOfferDao.findByUid(uid);
	}

	public List<JobOffer> findJobOffers(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		logger.debug("Find all job offers of the candidate  {}", candidate);
		return this.jobOfferDao.findJobOffers(candidate, page);
	}

	public List<JobOffer> findJobOffers(final Client client, final Page page) {
		Objects.requireNonNull(client);
		Objects.requireNonNull(page);
		logger.debug("Find all job offers of the client  {}", client);
		return this.jobOfferDao.findJobOffers(client, page);
	}

	public List<JobOffer> findJobOffers(final User owner, final Page page) {
		Objects.requireNonNull(owner);
		Objects.requireNonNull(page);
		logger.debug("Find all job offers of the owner {}", owner.getFullName());
		return this.jobOfferDao.findJobOffers(owner, page);
	}

	public List<JobOfferState> findJobOfferStates() {
		logger.debug("Find all job offer states");
		return Arrays.asList(JobOfferState.values());
	}

	@Override
	public AbstractIndexedDao<JobOffer> getDao() {
		return this.jobOfferDao;
	}

	private JobOfferState getPreviousState(final JobOffer jobOffer) {
		if (!jobOffer.isNew()) {
			final JobOffer savedJobOffer = this.find(jobOffer.getId());
			return savedJobOffer.getState();
		}
		return null;
	}

	private boolean isCompleted(final JobOffer jobOffer) {
		final List<JobCandidature> jobCandidatures = this.jobCandidatureService.findApprovedJobCanditures(jobOffer,
				Page.ALL_RESULTS);
		final int numberofAprrovedCandidatures = jobCandidatures.size();
		return jobOffer.getPlaces() == numberofAprrovedCandidatures;
	}

	public void onJobCandidatureCompleted(@Observes @JobCandidatureCompletedEvent final JobCandidature jobCandidature) {
		Objects.requireNonNull(jobCandidature, "Job candidature can't be null");
		final JobOffer jobOffer = jobCandidature.getJobOffer();
		if (this.isCompleted(jobCandidature.getJobOffer())) {
			this.closeJobOffer(jobOffer);
		}
	}

	private boolean openDateHasCome(final JobOffer jobOffer) {
		return !LocalDate.now().isBefore(jobOffer.getDateOpened());
	}

	private void openJobOffer(final JobOffer jobOffer) {
		jobOffer.setDateOpened(LocalDate.now());
		jobOffer.setState(JobOfferState.OPENED);
	}

	@Override
	public JobOffer save(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Save job offer {}", jobOffer);
		if (jobOffer.isNew() && this.openDateHasCome(jobOffer)) {
			logger.debug("The open date has come. Opening the job offer");
			this.openJobOffer(jobOffer);
			this.createdEvent.fire(jobOffer);
		} else {
			final JobOfferState previousJobOfferState = this.getPreviousState(jobOffer);
			if (!jobOffer.hasState(previousJobOfferState)) {
				logger.debug("The job offer state has changed");
				this.stateChangedEvent.fire(jobOffer);
			}
		}
		return super.save(jobOffer);
	}

	public void setCompletedEvent(final Event<JobOffer> completedEvent) {
		this.completedEvent = completedEvent;
	}

	public void setCreatedEvent(final Event<JobOffer> createdEvent) {
		this.createdEvent = createdEvent;
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		this.jobCandidatureService = jobCandidatureService;
	}

	public void setJobOfferDao(final JobOfferDao jobOfferDao) {
		Objects.requireNonNull(jobOfferDao);

		this.jobOfferDao = jobOfferDao;
	}

	public void setJobOfferStateChangeEventDao(final JobOfferStateChangeEventDao jobOfferStateChangeEventDao) {
		this.jobOfferStateChangeEventDao = jobOfferStateChangeEventDao;
	}

	public void setStateChangedEvent(final Event<JobOffer> stateChangedEvent) {
		this.stateChangedEvent = stateChangedEvent;
	}
}
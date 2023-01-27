package es.nivel36.laie.ejb.job.offer;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.core.action.Create;
import es.nivel36.laie.ejb.core.action.Update;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureDao;
import es.nivel36.laie.ejb.job.candidature.event.JobCandidatureCompletedEvent;
import es.nivel36.laie.ejb.job.offer.event.JobOfferCompletedEvent;
import es.nivel36.laie.ejb.job.offer.event.JobOfferCreatedEvent;
import es.nivel36.laie.ejb.job.offer.event.JobOfferStateChangedEvent;
import es.nivel36.laie.ejb.user.User;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@Stateless
public class JobOfferService {

	private static final Logger logger = LoggerFactory.getLogger(JobOffer.class);

	private @Inject JobCandidatureDao jobCandidatureDao;

	private @Inject JobOfferDao jobOfferDao;

	private @Inject @Update @JobOfferCompletedEvent Event<JobOffer> completedEvent;

	private @Inject @Update Event<JobOffer> updateEvent;

	private @Inject @Create @JobOfferCreatedEvent Event<JobOffer> createdEvent;

	private @Inject @Update @JobOfferStateChangedEvent Event<JobOffer> stateChangedEvent;

	public void addJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		jobOffer.setState(JobOfferState.CREATED);
		this.jobOfferDao.insert(jobOffer);
		final User owner = jobOffer.getOwner();
		final JobOfferEvent newEvent = builJobOfferEvent(jobOffer, JobOfferState.CREATED, null, owner);
		jobOfferDao.addJobOfferEvent(newEvent);
		this.createdEvent.fireAsync(jobOffer);
		if (this.openDateHasCome(jobOffer)) {
			this.changeState(jobOffer, JobOfferState.OPENED, null, owner);
		}
	}

	public JobOffer updateJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final JobOffer updatedJobOffer = jobOfferDao.update(jobOffer);
		this.updateEvent.fireAsync(jobOffer);
		return updatedJobOffer;
	}

	private boolean openDateHasCome(final JobOffer jobOffer) {
		final LocalDateTime dateOpened = jobOffer.getOpenDate();
		final LocalDateTime now = LocalDateTime.now();
		return !now.isBefore(dateOpened);
	}

	public JobOffer findJobOfferById(final Long id) {
		Objects.requireNonNull(id);
		logger.debug("Find job offer by id {}", id);
		return this.jobOfferDao.find(JobOffer.class, id);
	}

	public List<JobOffer> findJobOffersByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		logger.debug("Find all job offers of the candidate  {}", candidate);
		return this.jobOfferDao.findJobOffersByCandidate(candidate, page);
	}

	public List<JobOffer> findJobOffersByClient(final Client client, final Page page) {
		Objects.requireNonNull(client);
		Objects.requireNonNull(page);
		logger.debug("Find all job offers of the client  {}", client);
		return this.jobOfferDao.findJobOffersByClient(client, page);
	}

	public List<JobOffer> findJobOffersByOwnerOrRecruiter(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		logger.debug("Find all job offers of the owner or recruiter {}", user);
		return this.jobOfferDao.findJobOffersByOwnerOrRecruiter(user, page);
	}

	public List<JobOfferState> findJobOfferStates() {
		logger.debug("Find all job offer states");
		return Arrays.asList(JobOfferState.values());
	}

	private boolean isCompleted(final JobOffer jobOffer) {
		final List<JobCandidature> jobCandidatures = this.jobCandidatureDao.findApprovedJobCanditures(jobOffer,
				Page.ALL_RESULTS);
		final int numberofAprrovedCandidatures = jobCandidatures.size();
		return jobOffer.getPlaces() == numberofAprrovedCandidatures;
	}

	public void onJobCandidatureCompleted(@Observes @JobCandidatureCompletedEvent final JobCandidature jobCandidature) {
		Objects.requireNonNull(jobCandidature, "Job candidature can't be null");
		final JobOffer jobOffer = jobCandidature.getJobOffer();
		if (this.isCompleted(jobOffer)) {
			this.changeState(jobOffer, JobOfferState.CLOSED, null, null);
		}
	}

	public JobOffer changeState(final JobOffer jobOffer, final JobOfferState newState, final String notes,
			final User user) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(newState);
		final JobOfferEvent newStateEvent = builJobOfferEvent(jobOffer, newState, notes, user);
		jobOfferDao.addJobOfferEvent(newStateEvent);
		jobOffer.setState(newState);
		if (newState.isCloseState()) {
			jobOffer.setCloseDate(LocalDateTime.now());
			jobOffer.setState(JobOfferState.CLOSED);
		}
		if (newState.isOpenState()) {
			jobOffer.setCloseDate(null);
		}
		this.stateChangedEvent.fireAsync(jobOffer);
		final JobOffer updatedJobOffer = jobOfferDao.update(jobOffer);
		if (newState.isCloseState()) {
			this.completedEvent.fireAsync(jobOffer);
		}
		return updatedJobOffer;
	}

	private JobOfferEvent builJobOfferEvent(final JobOffer jobOffer, final JobOfferState newState, final String notes,
			final User user) {
		final JobOfferEvent newStateEvent = new JobOfferEvent();
		newStateEvent.setDate(LocalDateTime.now());
		newStateEvent.setJobOffer(jobOffer);
		newStateEvent.setState(newState);
		newStateEvent.setNotes(notes);
		newStateEvent.setUser(user);
		newStateEvent.setType(user == null ? JobOfferEventType.AUTOMATIC_EVENT : JobOfferEventType.MANUAL_EVENT);
		return newStateEvent;
	}

	public long countJobOfferEventsByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		return jobOfferDao.countJobOfferEventsByJobOffer(jobOffer);
	}

	public List<JobOfferEvent> findJobOfferEventsByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		return jobOfferDao.findJobOfferEventsByJobOffer(jobOffer, page);
	}

	public SearchResult<JobOffer> search(final String searchText, final Page page) {
		return this.search(searchText, page, null, null);
	}

	public SearchResult<JobOffer> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		Objects.requireNonNull(page);
		return this.jobOfferDao.search(searchText, page, sortField, searchFacets);
	}

	public void setCompletedEvent(final Event<JobOffer> completedEvent) {
		Objects.requireNonNull(completedEvent);
		this.completedEvent = completedEvent;
	}

	public void setCreatedEvent(final Event<JobOffer> createdEvent) {
		Objects.requireNonNull(createdEvent);
		this.createdEvent = createdEvent;
	}

	public void setStateChangedEvent(final Event<JobOffer> stateChangedEvent) {
		Objects.requireNonNull(stateChangedEvent);
		this.stateChangedEvent = stateChangedEvent;
	}

	public void setJobCandidatureDao(final JobCandidatureDao jobCandidatureDao) {
		Objects.requireNonNull(jobCandidatureDao);
		this.jobCandidatureDao = jobCandidatureDao;
	}

	public void setJobOfferDao(final JobOfferDao jobOfferDao) {
		Objects.requireNonNull(jobOfferDao);
		this.jobOfferDao = jobOfferDao;
	}
}
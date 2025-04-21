package es.nivel36.laie.ejb.job.offer;

import java.time.LocalDate;
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
import es.nivel36.laie.ejb.job.offer.event.JobOfferCompletedEvent;
import es.nivel36.laie.ejb.job.offer.event.JobOfferCreatedEvent;
import es.nivel36.laie.ejb.job.offer.event.JobOfferStateChangedEvent;
import es.nivel36.laie.ejb.user.User;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Stateless
public class JobOfferService {

	private static final Logger logger = LoggerFactory.getLogger(JobOfferService.class);

	private @Inject JobOfferDao jobOfferDao;
	private @Inject JobOfferEventDao jobOfferEventDao;
	private @Inject JobOfferProcessDao jobOfferProcessDao;
	private @Inject @Update @JobOfferCompletedEvent Event<JobOffer> completedEvent;
	private @Inject @Create @JobOfferCreatedEvent Event<JobOffer> createdEvent;
	private @Inject @Update @JobOfferStateChangedEvent Event<JobOffer> stateChangedEvent;
	private @Inject @Update Event<JobOffer> updateEvent;

	public void addJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Adding job offer {}", jobOffer);

		jobOffer.setState(JobOfferState.CREATED);
		jobOffer.setCreationDate(LocalDate.now());
		this.jobOfferDao.insert(jobOffer);

		final User owner = jobOffer.getOwner();
		final JobOfferEvent newEvent = this.buildJobOfferEvent(jobOffer, JobOfferState.CREATED, null, owner);
		jobOfferEventDao.insert(newEvent);

		this.createdEvent.fire(jobOffer);
		logger.trace("Job offer {} added successfully", jobOffer);
	}

	private JobOfferEvent buildJobOfferEvent(final JobOffer jobOffer, final JobOfferState newState, final String notes,
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

	public JobOffer updateJobOfferData(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Updating job offer {}", jobOffer);

		final JobOffer oldJobOffer = this.findJobOfferById(jobOffer.getId());
		if (!jobOffer.getState().equals(oldJobOffer.getState())) {
			throw new IllegalStateException("Cannot change the state of a job offer through an update");
		}

		final JobOffer updatedJobOffer = this.jobOfferDao.update(jobOffer);
		this.updateEvent.fire(jobOffer);
		logger.trace("Job offer {} updated successfully", jobOffer);
		return updatedJobOffer;
	}

	public JobOffer changeState(final JobOffer jobOffer, final JobOfferState newState, final String notes,
			final User user) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(newState);
		logger.debug("Changing state of job offer {} to {}", jobOffer, newState);

		final JobOfferEvent newStateEvent = this.buildJobOfferEvent(jobOffer, newState, notes, user);
		this.jobOfferEventDao.insert(newStateEvent);
		jobOffer.setState(newState);
		if (newState.isCloseState()) {
			jobOffer.setCompletionDate(LocalDate.now());
		}
		this.stateChangedEvent.fire(jobOffer);
		final JobOffer updatedJobOffer = this.jobOfferDao.update(jobOffer);
		if (newState.isCloseState()) {
			this.completedEvent.fire(jobOffer);
		}
		logger.trace("State of job offer {} changed to {}", jobOffer, newState);
		return updatedJobOffer;
	}

	public JobOfferProcess findJobOfferProcessByName(final String name) {
		Objects.requireNonNull(name);
		logger.debug("Finding job offer process by name {}", name);

		final JobOfferProcess jobOfferProcess = this.jobOfferProcessDao.findJobOfferProcessByName(name);
		logger.trace("Job offer process for name {} found: {}", name, jobOfferProcess);
		return jobOfferProcess;
	}

	public JobOffer findJobOfferById(final Long id) {
		Objects.requireNonNull(id);
		logger.debug("Finding job offer by id {}", id);

		final JobOffer jobOffer = this.jobOfferDao.find(JobOffer.class, id);
		logger.trace("Job offer found for id {}: {}", id, jobOffer);
		return jobOffer;
	}

	public JobOffer findJobOfferData(final Long id) {
		Objects.requireNonNull(id);
		logger.debug("Finding job offer data by id {}", id);

		final JobOffer jobOfferData = this.jobOfferDao.findJobOfferData(id);
		logger.trace("Job offer data found for id {}: {}", id, jobOfferData);
		return jobOfferData;
	}

	public List<JobOffer> findJobOffersByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		logger.debug("Finding all job offers for candidate {}", candidate);

		final List<JobOffer> jobOffers = this.jobOfferDao.findJobOffersByCandidate(candidate, page);
		logger.trace("Job offers found for candidate {}: {}", candidate, jobOffers);
		return jobOffers;
	}

	public List<JobOffer> findJobOffersByClient(final Client client, final Page page) {
		Objects.requireNonNull(client);
		Objects.requireNonNull(page);
		logger.debug("Finding all job offers for client {}", client);

		final List<JobOffer> jobOffers = this.jobOfferDao.findJobOffersByClient(client, page);
		logger.trace("Job offers found for client {}: {}", client, jobOffers);
		return jobOffers;
	}

	public List<JobOffer> findJobOffersByOwnerOrRecruiter(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		logger.debug("Finding all job offers for owner or recruiter {}", user);

		final List<JobOffer> jobOffers = this.jobOfferDao.findJobOffersByOwnerOrRecruiter(user, page);
		logger.trace("Job offers found for owner or recruiter {}: {}", user, jobOffers);
		return jobOffers;
	}

	public long countJobOffersByOwnerOrRecruiter(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Counting all job offers for owner or recruiter {}", user);

		final long count = this.jobOfferDao.countJobOffersByOwnerOrRecruiter(user);
		logger.trace("Total job offers for owner or recruiter {}: {}", user, count);
		return count;
	}

	public List<JobOfferProcess> findJobOfferProcess() {
		logger.debug("Finding all job offer processes");

		final List<JobOfferProcess> jobOfferProcesses = this.jobOfferProcessDao.findJobOfferProcess();
		logger.trace("Job offer processes found: {}", jobOfferProcesses);
		return jobOfferProcesses;
	}

	public List<JobOfferState> findJobOfferStates() {
		logger.debug("Finding all job offer states");

		final List<JobOfferState> jobOfferStates = Arrays.asList(JobOfferState.values());
		logger.trace("Job offer states found: {}", jobOfferStates);
		return jobOfferStates;
	}

	public List<JobOffer> findJobOffersToClose() {
		logger.debug("Finding all job offers to close");

		final List<JobOffer> jobOffers = jobOfferDao.findJobOffersToClose();
		logger.trace("Job offers to close found: {}", jobOffers);

		return jobOffers;
	}

	public List<JobOffer> findJobOffersToOpen() {
		logger.debug("Finding all job offers to open");

		final List<JobOffer> jobOffers = jobOfferDao.findJobOffersToOpen();
		logger.trace("Job offers to open found: {}", jobOffers);
		return jobOffers;
	}

	public long countJobOfferEventsByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Counting job offer events for job offer {}", jobOffer);

		final long count = jobOfferDao.countJobOfferEventsByJobOffer(jobOffer);
		logger.trace("Total job offer events for job offer {}: {}", jobOffer, count);
		return count;
	}

	public long countJobOffersByClient(final Client client) {
		Objects.requireNonNull(client);
		logger.debug("Counting job offers for client {}", client);

		final long count = jobOfferDao.countJobOffersByClient(client);
		logger.trace("Total job offers for client {}: {}", client, count);
		return count;
	}

	public List<JobOfferEvent> findJobOfferEventsByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		logger.debug("Finding job offer events for job offer {} with pagination", jobOffer);

		final List<JobOfferEvent> jobOfferEvents = jobOfferDao.findJobOfferEventsByJobOffer(jobOffer, page);
		logger.trace("Job offer events found for job offer {}: {}", jobOffer, jobOfferEvents);
		return jobOfferEvents;
	}

	public List<User> findRecruitersByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		logger.debug("Finding recruiters for job offer {} with pagination", jobOffer);

		final List<User> recruiters = jobOfferDao.findRecruitersByJobOffer(jobOffer, page);
		logger.trace("Recruiters found for job offer {}: {}", jobOffer, recruiters);
		return recruiters;
	}

	public SearchResult<JobOffer> search(final String searchText, final Page page) {
		logger.debug("Searching job offers with text '{}' and pagination", searchText);

		final SearchResult<JobOffer> searchResult = this.search(searchText, page, null, null);
		logger.trace("Search results found for text '{}': {}", searchText, searchResult);
		return searchResult;
	}

	public SearchResult<JobOffer> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		Objects.requireNonNull(page);
		logger.debug("Searching job offers with text '{}', pagination, sorting, and facets", searchText);

		final SearchResult<JobOffer> searchResult = this.jobOfferDao.search(searchText, page, sortField, searchFacets);
		logger.trace("Search results found for text '{}', sort field '{}', and facets '{}': {}", searchText, sortField,
				Arrays.toString(searchFacets), searchResult);
		return searchResult;
	}

	public void setCompletedEvent(final Event<JobOffer> completedEvent) {
		this.completedEvent = Objects.requireNonNull(completedEvent);
	}

	public void setCreatedEvent(final Event<JobOffer> createdEvent) {
		this.createdEvent = Objects.requireNonNull(createdEvent);
	}

	public void setJobOfferProcessDao(final JobOfferProcessDao jobOfferProcessDao) {
		this.jobOfferProcessDao = Objects.requireNonNull(jobOfferProcessDao);
	}

	public void setUpdateEvent(final Event<JobOffer> updateEvent) {
		this.updateEvent = Objects.requireNonNull(updateEvent);
	}

	public void setStateChangedEvent(final Event<JobOffer> stateChangedEvent) {
		this.stateChangedEvent = Objects.requireNonNull(stateChangedEvent);
	}

	public void setJobOfferDao(final JobOfferDao jobOfferDao) {
		this.jobOfferDao = Objects.requireNonNull(jobOfferDao);
	}
	
	public void setJobOfferEventDao(final JobOfferEventDao jobOfferEventDao) {
		this.jobOfferEventDao = Objects.requireNonNull(jobOfferEventDao);
	}
}
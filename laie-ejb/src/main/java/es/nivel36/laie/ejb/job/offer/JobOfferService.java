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

	private static final Logger logger = LoggerFactory.getLogger(JobOffer.class);

	private @Inject JobOfferDao jobOfferDao;
	private @Inject JobOfferProcessDao jobOfferProcessDao;
	private @Inject @Update @JobOfferCompletedEvent Event<JobOffer> completedEvent;
	private @Inject @Create @JobOfferCreatedEvent Event<JobOffer> createdEvent;
	private @Inject @Update @JobOfferStateChangedEvent Event<JobOffer> stateChangedEvent;
	private @Inject @Update Event<JobOffer> updateEvent;

	public void addJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Adding job offer {}", jobOffer);
		jobOffer.setState(JobOfferState.CREATED);
		this.jobOfferDao.insert(jobOffer);
		
		final User owner = jobOffer.getOwner();
		final JobOfferEvent newEvent = builJobOfferEvent(jobOffer, JobOfferState.CREATED, null, owner);
		jobOfferDao.addJobOfferEvent(newEvent);
		
		this.createdEvent.fireAsync(jobOffer);
	}

	public JobOfferProcess findJobOfferProcessByName(String name) {
		Objects.requireNonNull(name);
		return this.jobOfferProcessDao.findJobOfferProcessByName(name);
	}

	public JobOffer updateJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final JobOffer updatedJobOffer = jobOfferDao.update(jobOffer);
		this.updateEvent.fireAsync(jobOffer);
		return updatedJobOffer;
	}

	public JobOffer findJobOfferById(final Long id) {
		Objects.requireNonNull(id);
		logger.debug("Find job offer by id {}", id);
		return this.jobOfferDao.find(JobOffer.class, id);
	}

	public JobOffer findJobOfferData(final Long id) {
		Objects.requireNonNull(id);
		logger.debug("Find job offer by id {}", id);
		return this.jobOfferDao.findJobOfferData(id);
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

	public long countJobOffersByOwnerOrRecruiter(User user) {
		Objects.requireNonNull(user);
		logger.debug("Find all job offers of the owner or recruiter {}", user);
		return this.jobOfferDao.countJobOffersByOwnerOrRecruiter(user);
	}

	public List<JobOfferProcess> findJobOfferProcess() {
		return this.jobOfferDao.findJobOfferProcess();
	}

	public List<JobOfferState> findJobOfferStates() {
		logger.debug("Find all job offer states");
		return Arrays.asList(JobOfferState.values());
	}

	public JobOffer changeState(final JobOffer jobOffer, final JobOfferState newState, final String notes,
			final User user) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(newState);
		final JobOfferEvent newStateEvent = builJobOfferEvent(jobOffer, newState, notes, user);
		jobOfferDao.addJobOfferEvent(newStateEvent);
		jobOffer.setState(newState);
		if (newState.isCloseState()) {
			jobOffer.setCloseDate(LocalDate.now());
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
	
	public List<JobOffer> findJobOffersToClose(){
		return jobOfferDao.findJobOffersToClose();
	}
	
	public List<JobOffer> findJobOffersToOpen(){
		return jobOfferDao.findJobOffersToOpen();
	}

	public long countJobOfferEventsByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		return jobOfferDao.countJobOfferEventsByJobOffer(jobOffer);
	}

	public long countJobOffersByClient(final Client client) {
		Objects.requireNonNull(client);
		return jobOfferDao.countJobOffersByClient(client);
	}

	public List<JobOfferEvent> findJobOfferEventsByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		return jobOfferDao.findJobOfferEventsByJobOffer(jobOffer, page);
	}

	public List<User> findRecruitersByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		return jobOfferDao.findRecruitersByJobOffer(jobOffer, page);
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

	public void setJobOfferDao(final JobOfferDao jobOfferDao) {
		Objects.requireNonNull(jobOfferDao);
		this.jobOfferDao = jobOfferDao;
	}
}
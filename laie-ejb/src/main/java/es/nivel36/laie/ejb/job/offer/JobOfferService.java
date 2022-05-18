package es.nivel36.laie.ejb.job.offer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.core.action.Create;
import es.nivel36.laie.ejb.core.action.Update;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureDao;
import es.nivel36.laie.ejb.job.candidature.event.JobCandidatureCompletedEvent;
import es.nivel36.laie.ejb.job.offer.event.JobOfferCompletedEvent;
import es.nivel36.laie.ejb.job.offer.event.JobOfferCreatedEvent;
import es.nivel36.laie.ejb.job.offer.event.JobOfferStateChangedEvent;
import es.nivel36.laie.ejb.user.User;

@Stateless
public class JobOfferService {

	private static final Logger logger = LoggerFactory.getLogger(JobOffer.class);

	private @Inject JobCandidatureDao jobCandidatureDao;

	private @Inject JobOfferDao jobOfferDao;

	private @Inject JobOfferStateChangeEventDao jobOfferStateChangeEventDao;

	private @Inject @Update @JobOfferCompletedEvent Event<JobOffer> completedEvent;

	private @Inject @Update Event<JobOffer> updateEvent;

	private @Inject @Create @JobOfferCreatedEvent Event<JobOffer> createdEvent;

	private @Inject @Update @JobOfferStateChangedEvent Event<JobOffer> stateChangedEvent;

	public void addJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		jobOffer.setState(JobOfferState.CREATED);
		this.jobOfferDao.insert(jobOffer);
		this.createdEvent.fireAsync(jobOffer);
		if (this.openDateHasCome(jobOffer)) {
			this.changeState(jobOffer, JobOfferState.OPENED, null);
		}
	}

	public JobOffer updateJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final JobOffer updatedJobOffer = jobOfferDao.update(jobOffer);
		this.updateEvent.fireAsync(jobOffer);
		return updatedJobOffer;
	}

	private boolean openDateHasCome(final JobOffer jobOffer) {
		final LocalDate dateOpened = jobOffer.getOpenDate();
		final LocalDate now = LocalDate.now();
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
			this.changeState(jobOffer, JobOfferState.CLOSED, null);
		}
	}

	public JobOffer changeState(final JobOffer jobOffer, final JobOfferState newState, final String notes) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(newState);
		final JobOfferStateEvent newStateEvent = builJobOfferStateEvent(jobOffer, newState, notes);
		jobOfferDao.addJobOfferStateEvent(newStateEvent);
		jobOffer.setState(newState);
		if (newState.isCloseState()) {
			jobOffer.setCloseDate(LocalDate.now());
			jobOffer.setState(JobOfferState.CLOSED);
		}
		this.stateChangedEvent.fireAsync(jobOffer);
		final JobOffer updatedJobOffer = jobOfferDao.update(jobOffer);
		if (newState.isCloseState()) {
			this.completedEvent.fireAsync(jobOffer);
		}
		return updatedJobOffer;
	}

	private JobOfferStateEvent builJobOfferStateEvent(final JobOffer jobOffer, final JobOfferState newState,
			final String notes) {
		final JobOfferStateEvent newStateEvent = new JobOfferStateEvent();
		newStateEvent.setDate(LocalDateTime.now());
		newStateEvent.setJobOffer(jobOffer);
		newStateEvent.setState(newState);
		newStateEvent.setNotes(notes);
		return newStateEvent;
	}

	public long countJobOfferStateEventsByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		return jobOfferDao.countJobOfferStateEventsByJobOffer(jobOffer);
	}

	public List<JobOfferStateEvent> findJobOfferStateEventsByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		return jobOfferDao.findJobOfferStateEventsByJobOffer(jobOffer, page);
	}

	public SearchResult<JobOffer> search(final String searchText, final Page page) {
		return this.search(searchText, page, null, null);
	}

	public SearchResult<JobOffer> search(final String searchText, final Page page, final SortField sortField,
			final SearchFacets searchFacets) {
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
		Objects.requireNonNull(jobOfferStateChangeEventDao);
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

	public void setJobOfferStateChangeEventDao(final JobOfferStateChangeEventDao jobOfferStateChangeEventDao) {
		Objects.requireNonNull(jobOfferStateChangeEventDao);
		this.jobOfferStateChangeEventDao = jobOfferStateChangeEventDao;
	}
}
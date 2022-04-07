package es.nivel36.laie.ejb.job.offer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.hibernate.search.query.facet.Facet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
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
import es.nivel36.laie.ejb.user.UserDao;

@Stateless
public class JobOfferService {

	private static final Logger logger = LoggerFactory.getLogger(JobOffer.class);

	@Inject
	@Repository
	private ClientDao clientDao;

	@Inject
	@Repository
	private JobCandidatureDao jobCandidatureDao;

	@Inject
	@Repository
	private JobOfferDao jobOfferDao;

	@Inject
	@Repository
	private UserDao userDao;

	@Inject
	@Repository
	private JobOfferStateChangeEventDao jobOfferStateChangeEventDao;

	@Inject
	@JobOfferCompletedEvent
	private Event<JobOffer> completedEvent;

	@Inject
	@JobOfferCreatedEvent
	private Event<JobOffer> createdEvent;

	@Inject
	@JobOfferStateChangedEvent
	private Event<JobOffer> stateChangedEvent;

	private JobOfferMapper jobOfferMapper = new JobOfferMapper();

	private JobOfferMerger jobOfferMerger = new JobOfferMerger();

	public JobOfferDto addJobOffer(final String clientUid, final String ownerUid, String[] recruiterUids,
			final JobOfferDto jobOffer) {
		Objects.requireNonNull(clientUid);
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(ownerUid);
		final Client client = this.clientDao.findClientByUid(clientUid);
		final JobOffer entity = new JobOffer();
		jobOfferMerger.merge(entity, jobOffer);
		entity.setClient(client);
		entity.setState(JobOfferState.CREATED);
		if (this.openDateHasCome(entity)) {
			this.openJobOffer(entity);
		}
		final User owner = this.userDao.findUserByUid(ownerUid);
		chageJobOffersOwner(entity, owner);
		addRecruiters(entity, recruiterUids);
		this.jobOfferDao.insert(entity);
		this.createdEvent.fire(entity);
		return jobOfferMapper.map(entity);
	}

	public void addRecruiter(final String jobOfferUid, final String[] recruiterUids) {
		final JobOffer jobOffer = jobOfferDao.findByUid(jobOfferUid);
		addRecruiters(jobOffer, recruiterUids);
	}

	private void addRecruiters(final JobOffer jobOffer, final String[] recruiterUids) {
		for (String recruiterUid : recruiterUids) {
			final User recruiter = userDao.findUserByUid(recruiterUid);
			jobOffer.getRecruiters().add(recruiter);
		}
	}

	public void removeRecruiter(final String jobOfferUid, final String[] recruiterUids) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(recruiterUids);
		final JobOffer jobOffer = jobOfferDao.findByUid(jobOfferUid);
		for (String recruiterUid : recruiterUids) {
			final User recruiter = userDao.findUserByUid(recruiterUid);
			jobOffer.getRecruiters().remove(recruiter);
		}
	}

	private boolean openDateHasCome(final JobOffer jobOffer) {
		final LocalDate dateOpened = jobOffer.getOpenDate();
		final LocalDate now = LocalDate.now();
		return !now.isBefore(dateOpened);
	}

	private void openJobOffer(final JobOffer jobOffer) {
		logger.debug("The open date has come. Opening the job offer");
		jobOffer.setState(JobOfferState.OPENED);
		this.stateChangedEvent.fire(jobOffer);
	}

	public void closeJobOffer(final String jobOfferUid) {
		final JobOffer jobOffer = this.jobOfferDao.findByUid(jobOfferUid);
		jobOffer.setCloseDate(LocalDate.now());
		jobOffer.setState(JobOfferState.CLOSED);
		jobOffer.setPublished(false);
		this.completedEvent.fire(jobOffer);
	}

	public void updateJobOffer(final JobOfferDto jobOffer) {
		Objects.requireNonNull(jobOffer);
		final JobOffer entity = jobOfferDao.findByUid(jobOffer.getUid());
		jobOfferMerger.merge(entity, jobOffer);
	}

	public void changeJobOffersOwner(final String jobOfferUid, final String newOwnerUid) {
		final JobOffer jobOffer = this.jobOfferDao.findByUid(jobOfferUid);
		final User owner = this.userDao.findUserByUid(newOwnerUid);
		chageJobOffersOwner(jobOffer, owner);
	}

	private void chageJobOffersOwner(final JobOffer jobOffer, final User owner) {
		jobOffer.setOwner(owner);
	}

	public JobOfferDto findJobOfferByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find job offer by uid {}", uid);
		final JobOffer jobOffer = this.jobOfferDao.findByUid(uid);
		return jobOfferMapper.map(jobOffer);
	}

	public List<JobOfferDto> findJobOffersByCandidate(final String candidateUid, final Page page) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(page);
		logger.debug("Find all job offers of the candidate  {}", candidateUid);
		final List<JobOffer> jobOffers = this.jobOfferDao.findJobOffersByCandidate(candidateUid, page);
		return jobOfferMapper.mapList(jobOffers);
	}

	public List<JobOfferDto> findJobOffersByClient(final String clientUid, final Page page) {
		Objects.requireNonNull(clientUid);
		Objects.requireNonNull(page);
		logger.debug("Find all job offers of the client  {}", clientUid);
		final List<JobOffer> jobOffers = this.jobOfferDao.findJobOffersByClient(clientUid, page);
		return jobOfferMapper.mapList(jobOffers);
	}

	public List<JobOfferDto> findJobOffersByOwner(final String ownerUid, final Page page) {
		Objects.requireNonNull(ownerUid);
		Objects.requireNonNull(page);
		logger.debug("Find all job offers of the owner {}", ownerUid);
		final List<JobOffer> jobOffers = this.jobOfferDao.findJobOffersByOwner(ownerUid, page);
		return jobOfferMapper.mapList(jobOffers);
	}

	public List<JobOfferState> findJobOfferStates() {
		logger.debug("Find all job offer states");
		return Arrays.asList(JobOfferState.values());
	}

	private boolean isCompleted(final JobOffer jobOffer) {
		final List<JobCandidature> jobCandidatures = this.jobCandidatureDao.findApprovedJobCanditures(jobOffer.getUid(),
				Page.ALL_RESULTS);
		final int numberofAprrovedCandidatures = jobCandidatures.size();
		return jobOffer.getPlaces() == numberofAprrovedCandidatures;
	}

	public void onJobCandidatureCompleted(@Observes @JobCandidatureCompletedEvent final JobCandidature jobCandidature) {
		Objects.requireNonNull(jobCandidature, "Job candidature can't be null");
		final JobOffer jobOffer = jobCandidature.getJobOffer();
		if (this.isCompleted(jobOffer)) {
			this.closeJobOffer(jobOffer.getUid());
		}
	}

	public JobOfferDto publish(final String jobOfferUid) {
		Objects.requireNonNull(jobOfferUid);
		logger.debug("Publish job offer {}", jobOfferUid);
		final JobOfferDto jobOffer = this.findJobOfferByUid(jobOfferUid);
		if (jobOffer.isOpen()) {
			jobOffer.setPublished(true);
		} else {
			throw new IllegalStateException(String.format("Job offer %s is not oppen", jobOfferUid));
		}
		return jobOffer;
	}

	public void changeState(final String jobOfferUid, final JobOfferState newState) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(newState);
		final JobOffer entity = this.jobOfferDao.findByUid(jobOfferUid);
		entity.setState(newState);
		this.stateChangedEvent.fire(entity);
	}

	public JobOfferDto unpublish(final String jobOfferUid) {
		Objects.requireNonNull(jobOfferUid);
		final JobOfferDto jobOffer = this.findJobOfferByUid(jobOfferUid);
		if (jobOffer.isPublished()) {
			jobOffer.setPublished(false);
		}
		return jobOffer;
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

	public SearchResult<JobOfferDto> search(final String searchText, final Page page) {
		return this.search(searchText, page, null, null);
	}

	public SearchResult<JobOfferDto> search(final String searchText, final Page page, final SortField sortField,
			final SearchFacets searchFacets) {
		Objects.requireNonNull(page);
		final SearchResult<JobOffer> restul = this.jobOfferDao.search(searchText, page, sortField, searchFacets);
		final List<JobOffer> resultData = restul.getResultData();
		final List<JobOfferDto> mapList = new JobOfferMapper().mapList(resultData);
		final Map<String, List<Facet>> allFacets = restul.getAllFacets();
		final int count = restul.getCount();
		return new SearchResult<JobOfferDto>(mapList, count, allFacets);
	}
}
package es.nivel36.laie.ejb.job.offer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
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
	@JobOfferCompletedEvent
	private Event<JobOffer> completedEvent;

	@Inject
	@JobOfferCreatedEvent
	private Event<JobOffer> createdEvent;

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
	@JobOfferStateChangedEvent
	private Event<JobOffer> stateChangedEvent;

	private JobOfferMapper jobOfferMapper;

	@PostConstruct
	public void init() {
		jobOfferMapper = new JobOfferMapper();
	}

	public JobOfferDto closeJobOffer(final String jobOfferUid) {
		final JobOffer jobOffer = this.jobOfferDao.findByUid(jobOfferUid);
		jobOffer.setDateClosed(LocalDate.now());
		jobOffer.setState(JobOfferState.CLOSED);
		jobOffer.setPublished(false);
		this.completedEvent.fire(jobOffer);
		return jobOfferMapper.map(jobOffer);
	}

	public void addRecruiters(final String jobOfferUid, final String[] recruiterUids) {
		final JobOffer jobOffer = jobOfferDao.findByUid(jobOfferUid);
		for (String recruiterUid : recruiterUids) {
			final User recruiter = userDao.findUserByUid(recruiterUid);
			jobOffer.getRecruiters().add(recruiter);
		}
	}

	public void addRecruiter(final String jobOfferUid, final String recruiterUid) {
		final JobOffer jobOffer = jobOfferDao.findByUid(jobOfferUid);
		final User recruiter = userDao.findUserByUid(recruiterUid);
		jobOffer.getRecruiters().add(recruiter);
	}

	public void removeRecruiter(final String jobOfferUid, final String recruiterUid) {
		final JobOffer jobOffer = jobOfferDao.findByUid(jobOfferUid);
		final User recruiter = userDao.findUserByUid(recruiterUid);
		jobOffer.getRecruiters().remove(recruiter);
	}

	public void createJobOffer(final String clientUid, final JobOfferDto jobOffer) {
		Objects.requireNonNull(jobOffer);
		final User user = this.userDao.findUserByUid(jobOffer.getOwner().getUid());
		final Client client = this.clientDao.findClientByUid(clientUid);
		final JobOffer entity = new JobOffer();
		entity.setClient(client);
		entity.setOwner(user);
		entity.setTitle(jobOffer.getTitle());
		entity.setState(JobOfferState.CREATED);
		entity.setDateOpened(LocalDate.now());
		entity.setDescription(jobOffer.getDescription());
		entity.setPlaces(jobOffer.getPlaces());
		entity.setPublished(jobOffer.isPublished());
		entity.setSalary(jobOffer.getSalary());
		if (this.openDateHasCome(entity)) {
			this.openJobOffer(entity);
		}
		this.jobOfferDao.insert(entity);
	}

	private boolean openDateHasCome(final JobOffer jobOffer) {
		return !LocalDate.now().isBefore(jobOffer.getDateOpened());
	}

	private void openJobOffer(final JobOffer jobOffer) {
		logger.debug("The open date has come. Opening the job offer");
		jobOffer.setDateOpened(LocalDate.now());
		jobOffer.setState(JobOfferState.OPENED);
		this.createdEvent.fire(jobOffer);
	}

	public JobOfferDto findByUid(final String uid) {
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
		logger.debug(String.format("Publish job oofer %s", jobOfferUid));
		final JobOfferDto jobOffer = this.findByUid(jobOfferUid);
		if (jobOffer.isOpen()) {
			jobOffer.setPublished(true);
		} else {
			throw new IllegalStateException(String.format("Job offer %s is not oppen", jobOfferUid));
		}
		return jobOffer;
	}

	public void changeState(String jobOfferUid, JobOfferState newState) {
		final JobOffer entity = this.jobOfferDao.findByUid(jobOfferUid);
		entity.setState(newState);
		this.stateChangedEvent.fire(entity);
	}

	public void update(final JobOfferDto jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Update job offer {}", jobOffer);
		final JobOffer entity = this.jobOfferDao.findByUid(jobOffer.getUid());
		entity.setTitle(jobOffer.getTitle());
		entity.setDescription(jobOffer.getDescription());
		entity.setPlaces(jobOffer.getPlaces());
		entity.setSalary(jobOffer.getSalary());
	}

	public void setCompletedEvent(final Event<JobOffer> completedEvent) {
		this.completedEvent = completedEvent;
	}

	public void setCreatedEvent(final Event<JobOffer> createdEvent) {
		this.createdEvent = createdEvent;
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

	public void setStateChangedEvent(final Event<JobOffer> stateChangedEvent) {
		this.stateChangedEvent = stateChangedEvent;
	}

	public JobOfferDto unpublish(final String jobOfferUid) {
		final JobOfferDto jobOffer = this.findByUid(jobOfferUid);
		if (jobOffer.isPublished()) {
			jobOffer.setPublished(false);
		}
		return jobOffer;
	}
}
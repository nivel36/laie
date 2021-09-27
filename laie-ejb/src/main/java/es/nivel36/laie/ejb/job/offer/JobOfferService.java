package es.nivel36.laie.ejb.job.offer;

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

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.ejb.core.AbstractIndexedService;
import es.nivel36.laie.ejb.core.model.AbstractIndexedDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.ejb.job.candidature.event.JobCandidatureCompletedEvent;
import es.nivel36.laie.ejb.job.offer.event.JobOfferCompletedEvent;
import es.nivel36.laie.ejb.job.offer.event.JobOfferCreatedEvent;
import es.nivel36.laie.ejb.job.offer.event.JobOfferStateChangedEvent;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;

@Stateless
public class JobOfferService extends AbstractIndexedService<JobOffer> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	private ClientService clientService;

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

	@Inject
	private UserService userService;

	public JobOffer closeJobOffer(final String jobOfferUid) {
		final JobOffer jobOffer = this.findByUid(jobOfferUid);
		jobOffer.setDateClosed(LocalDate.now());
		jobOffer.setState(JobOfferState.CLOSED);
		jobOffer.setPublished(false);
		this.completedEvent.fire(jobOffer);
		return jobOffer;
	}

	public JobOffer createJobOffer(final String clientUid, final String ownerEmail, final String title) {
		Objects.requireNonNull(clientUid);
		Objects.requireNonNull(ownerEmail);
		Objects.requireNonNull(title);
		final User user = this.userService.findByEmail(ownerEmail);
		final Client client = this.clientService.findByUid(clientUid);
		final JobOffer jobOffer = new JobOffer();
		jobOffer.setClient(client);
		jobOffer.setOwner(user);
		jobOffer.setTitle(title);
		jobOffer.setState(JobOfferState.CREATED);
		jobOffer.setDateOpened(LocalDate.now());
		return jobOffer;
	}

	public JobOffer findByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find job offer by uid {}", uid);
		return this.jobOfferDao.findByUid(uid);
	}

	public List<JobOffer> findJobOffersByCandidate(final String candidateUid, final Page page) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(page);
		logger.debug("Find all job offers of the candidate  {}", candidateUid);
		return this.jobOfferDao.findJobOffersByCandidate(candidateUid, page);
	}

	public List<JobOffer> findJobOffersByClientUid(final String clientUid, final Page page) {
		Objects.requireNonNull(clientUid);
		Objects.requireNonNull(page);
		logger.debug("Find all job offers of the client  {}", clientUid);
		return this.jobOfferDao.findJobOffersByClientUid(clientUid, page);
	}

	public List<JobOffer> findJobOffersByUser(final String email, final Page page) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(page);
		logger.debug("Find all job offers of the owner {}", email);
		return this.jobOfferDao.findJobOffersByUser(email, page);
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
		if (jobOffer.isNew()) {
			return null;
		}
		final JobOffer savedJobOffer = this.find(jobOffer.getId());
		return savedJobOffer.getState();
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
		if (this.isCompleted(jobOffer)) {
			this.closeJobOffer(jobOffer.getUid());
		}
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

	public JobOffer publish(final String jobOfferUid) {
		Objects.requireNonNull(jobOfferUid);
		logger.debug(String.format("Publish job oofer %s", jobOfferUid));
		final JobOffer jobOffer = this.findByUid(jobOfferUid);
		if (jobOffer.isOpen()) {
			jobOffer.setPublished(true);
		} else {
			throw new IllegalStateException(String.format("Job offer %s is not oppen", jobOfferUid));
		}
		return jobOffer;
	}

	@Override
	public JobOffer save(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Save job offer {}", jobOffer);
		if (jobOffer.isNew() && this.openDateHasCome(jobOffer)) {
			this.openJobOffer(jobOffer);
		} else {
			final JobOfferState previousJobOfferState = this.getPreviousState(jobOffer);
			if (!jobOffer.hasState(previousJobOfferState)) {
				logger.debug("The job offer state has changed");
				this.stateChangedEvent.fire(jobOffer);
			}
		}
		return super.save(jobOffer);
	}

	public void setClientService(ClientService clientService) {
		Objects.requireNonNull(clientService);
		this.clientService = clientService;
	}

	public void setCompletedEvent(final Event<JobOffer> completedEvent) {
		this.completedEvent = completedEvent;
	}

	public void setCreatedEvent(final Event<JobOffer> createdEvent) {
		this.createdEvent = createdEvent;
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		Objects.requireNonNull(jobCandidatureService);
		this.jobCandidatureService = jobCandidatureService;
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

	public void setUserService(UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	public JobOffer unpublish(final String jobOfferUid) {
		final JobOffer jobOffer = this.findByUid(jobOfferUid);
		if (jobOffer.isPublished()) {
			jobOffer.setPublished(false);
		}
		return jobOffer;
	}
}
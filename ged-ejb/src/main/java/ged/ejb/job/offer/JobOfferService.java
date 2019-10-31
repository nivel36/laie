package ged.ejb.job.offer;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.job.candidature.JobCandidatureService;
import ged.ejb.job.candidature.JobCandidatureState;
import ged.ejb.user.User;

@Stateless
public class JobOfferService extends AbstractService<JobOffer> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private JobOfferDao jobOfferDao;
	
	@Inject
	private JobCandidatureService jobCandidatureService;

	@Inject
	@Repository
	private JobOfferStateChangeEventDao jobOfferStateChangeEventDao;

	public List<JobOffer> findJobOffers(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate, "Candidate can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		logger.debug("Find all job offers of the candidate  {}", candidate);

		return this.jobOfferDao.findJobOffers(candidate, page);
	}

	public List<JobOffer> findJobOffers(final Client client, final Page page) {
		Objects.requireNonNull(client, "Client can't be null");
		Objects.requireNonNull(page, "Page can't be null");
		logger.debug("Find all job offers of the client  {}", client);

		return this.jobOfferDao.findJobOffers(client, page);
	}

	public List<JobOffer> findJobOffers(final User owner, final Page page) {
		Objects.requireNonNull(owner, "Owner can't be null ");
		Objects.requireNonNull(page, "Page can't be null");
		logger.debug("Find all job offers of the owner {}", owner.getFullName());

		return this.jobOfferDao.findJobOffers(owner, page);
	}

	public List<JobOfferState> findJobOfferStates() {
		logger.debug("Find all job offer states");

		return Arrays.asList(JobOfferState.values());
	}

	@Override
	public AbstractDao<JobOffer> getDao() {
		return this.jobOfferDao;
	}

	private boolean openDateHasCome(final JobOffer jobOffer) {
		final LocalDate today = LocalDate.now();
		return jobOffer.getDateOpened().isAfter(today) || jobOffer.getDateOpened().isEqual(today);
	}

	@Override
	public JobOffer save(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer, "Job offer can't be null");
		logger.debug("Save job offer {}", jobOffer);

		final JobOffer savedJobOffer = super.save(jobOffer);
		if (savedJobOffer.hasState(JobOfferState.CREATED) && this.openDateHasCome(savedJobOffer)) {
			this.updateJobOfferState(savedJobOffer, JobOfferState.OPENED, null, null);
		} else if (!jobOffer.getJobOfferState().equals(savedJobOffer.getJobOfferState())) {
			this.updateJobOfferState(savedJobOffer, savedJobOffer.getJobOfferState(), null, null);
		}
		return savedJobOffer;
	}

	public void setJobOfferDao(final JobOfferDao jobOfferDao) {
		Objects.requireNonNull(jobOfferDao, "JobOfferDao can't be null");

		this.jobOfferDao = jobOfferDao;
	}

	public void updateJobOfferState(final JobOffer jobOffer, final JobOfferState state, final User user,
			final String notes) {
		Objects.requireNonNull(jobOffer, "Job offer cant't be null");
		Objects.requireNonNull(state, "Job offer state cant't be null");
		logger.debug("Update job offer {} to state {}", jobOffer, state);

		if (jobOffer.hasState(state)) {
			throw new IllegalStateException("Can't change state");
		}
		
		if(state.equals(JobOfferState.CLOSED)) {
			closeJobOffer(jobOffer);
		}

		jobOffer.setJobOfferState(state);
		this.save(jobOffer);

		final JobOfferStateChangeEvent event = new JobOfferStateChangeEvent(jobOffer, state);
		event.setUser(user);
		event.setNotes(notes);
		this.jobOfferStateChangeEventDao.save(event);
	}

	private void closeJobOffer(final JobOffer jobOffer) {
		jobOffer.setDateClosed(LocalDate.now());
	}
}

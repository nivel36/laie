package ged.ejb.job.offer;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.Audited;
import ged.ejb.core.action.Action.ActionType;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.job.meeting.JobMeeting;
import ged.ejb.job.meeting.JobMeetingDao;
import ged.ejb.user.User;

@Stateless
public class JobOfferServiceImpl extends AbstratctAuditedService<JobOffer> implements JobOfferService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final ClientService clientService;

	private final JobCandidatureDao jobCandidatureDao;

	private final JobMeetingDao jobMeetingDao;

	private final JobOfferDao jobOfferDao;

	@Inject
	public JobOfferServiceImpl(@Repository final JobOfferDao jobOfferDao, @Repository final JobMeetingDao jobMeetingDao, final ClientService clientService,
			@Repository final JobCandidatureDao jobCandidatureDao) {
		Objects.requireNonNull(jobOfferDao);
		Objects.requireNonNull(clientService);
		Objects.requireNonNull(jobCandidatureDao);
		Objects.requireNonNull(jobMeetingDao);
		this.clientService = clientService;
		this.jobCandidatureDao = jobCandidatureDao;
		this.jobOfferDao = jobOfferDao;
		this.jobMeetingDao = jobMeetingDao;
	}

	@Override
	public void addJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		logger.debug("Add Job Candidature of candidate {} to jobOffer {}", candidate.getFullName(), jobOffer.getName());
		final JobCandidature jobCandidature = new JobCandidature();
		jobCandidature.setCandidate(candidate);
		jobCandidature.setJobOffer(jobOffer);
		this.jobCandidatureDao.insert(jobCandidature);
	}

	@Override
	public void addJobCandidatures(final JobOffer jobOffer, final List<Candidate> candidates) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidates);
		for (final Candidate candidate : candidates) {
			this.addJobCandidature(jobOffer, candidate);
		}

	}

	@Override
	public void addJobMeeting(final JobMeeting jobMeeting) {
		Objects.requireNonNull(jobMeeting);
		logger.debug("Add Job meeting {}", jobMeeting.getDescription());
		this.jobMeetingDao.insert(jobMeeting);
	}

	@Override
	public List<JobOffer> findAllJobOffersByOwner(final User owner) {
		logger.debug("Find all job Offers of the owner {}", owner.getFullName());
		return this.jobOfferDao.findAllByOwner(owner);
	}

	@Override
	public List<JobOffer> findJobOffersByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Find all job Offers of the candidate  {}", candidate);
		return this.jobOfferDao.findJobOffersByCandidate(candidate);
	}

	@Override
	public List<JobOffer> findJobOffersByClient(final Client client) {
		logger.debug("Find all job Offers of the client  {}", client);
		return this.jobOfferDao.findJobOffersByClient(client);
	}

	@Override
	public List<JobOffer> findLastJobOffers(final User owner) {
		logger.debug("Find last job Offers of the owner {}", owner.getFullName());
		return this.jobOfferDao.findLastJobOffers(owner);
	}

	@Override
	public Dao<JobOffer> getDao() {
		return this.jobOfferDao;
	}

	@Override
	@Audited(action = ActionType.INSERT)
	public void insert(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Insert job offer {}", jobOffer.getDescription());
		this.putClientOnJobOffer(jobOffer);
		this.getDao().insert(jobOffer);
	}

	private void putClientOnJobOffer(final JobOffer jobOffer) {
		Client client = this.clientService.findClientByName(jobOffer.getClient().getName());
		if (client == null) {
			client = jobOffer.getClient();
			this.clientService.update(client);
		}
		else {
			jobOffer.setClient(client);
		}
	}

	@Override
	public void removeJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		logger.debug("Remove job candidature of candidate {} to job offer {}", candidate.getFullName(), jobOffer.getName());
		final JobCandidature jobCandidature = this.jobCandidatureDao.findByJobOfferIdAndCandidateId(jobOffer.getId(), candidate.getId());
		this.jobCandidatureDao.delete(jobCandidature);
	}

	@Override
	@Audited(action = ActionType.UPDATE)
	public JobOffer update(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Update job offer {}", jobOffer.getDescription());
		return this.getDao().update(jobOffer);
	}
}

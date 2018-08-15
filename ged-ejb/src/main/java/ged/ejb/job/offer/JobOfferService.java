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
import ged.ejb.core.AbstractAuditedService;
import ged.ejb.core.Audited;
import ged.ejb.core.action.Action.ActionType;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.job.meeting.JobMeeting;
import ged.ejb.job.meeting.JobMeetingDao;
import ged.ejb.user.User;

@Stateless
public class JobOfferService extends AbstractAuditedService<JobOffer> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	private ClientService clientService;

	@Inject
	@Repository
	private JobCandidatureDao jobCandidatureDao;

	@Inject
	@Repository
	private JobMeetingDao jobMeetingDao;

	@Inject
	@Repository
	private JobOfferDao jobOfferDao;

	public void addJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		logger.debug("Add Job Candidature of candidate {} to jobOffer {}", candidate.getFullName(), jobOffer.getName());
		final JobCandidature jobCandidature = new JobCandidature();
		jobCandidature.setCandidate(candidate);
		jobCandidature.setJobOffer(jobOffer);
		this.jobCandidatureDao.insert(jobCandidature);
	}

	public void addJobCandidatures(final JobOffer jobOffer, final List<Candidate> candidates) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidates);
		logger.debug("Add job candidatures to jobOffer {}", jobOffer);
		for (final Candidate candidate : candidates) {
			this.addJobCandidature(jobOffer, candidate);
		}
	}

	public void addJobMeeting(final JobMeeting jobMeeting) {
		Objects.requireNonNull(jobMeeting);
		logger.debug("Add Job meeting {}", jobMeeting.getDescription());
		this.jobMeetingDao.insert(jobMeeting);
	}

	public List<JobOffer> findAllJobOffersByOwner(final User owner) {
		Objects.requireNonNull(owner);
		logger.debug("Find all job Offers of the owner {}", owner.getFullName());
		return this.jobOfferDao.findAllByOwner(owner);
	}

	public List<JobOffer> findJobOffersByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Find all job Offers of the candidate  {}", candidate);
		return this.jobOfferDao.findJobOffersByCandidate(candidate);
	}

	public List<JobOffer> findJobOffersByClient(final Client client) {
		Objects.requireNonNull(client);
		logger.debug("Find all job Offers of the client  {}", client);
		return this.jobOfferDao.findJobOffersByClient(client);
	}

	public List<JobOffer> findLastJobOffers(final User owner) {
		Objects.requireNonNull(owner);
		logger.debug("Find last job Offers of the owner {}", owner.getFullName());
		return this.jobOfferDao.findLastJobOffers(owner);
	}

	@Override
	public AbstractDao<JobOffer> getDao() {
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

	public void removeJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		logger.debug("Remove job candidature of candidate {} to job offer {}", candidate.getFullName(), jobOffer.getName());
		final JobCandidature jobCandidature = this.jobCandidatureDao.findByJobOfferAndCandidate(jobOffer, candidate);
		this.jobCandidatureDao.delete(jobCandidature);
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

	public void setJobCandidatureDao(final JobCandidatureDao jobCandidatureDao) {
		this.jobCandidatureDao = jobCandidatureDao;
	}

	public void setJobMeetingDao(final JobMeetingDao jobMeetingDao) {
		this.jobMeetingDao = jobMeetingDao;
	}

	public void setJobOfferDao(final JobOfferDao jobOfferDao) {
		this.jobOfferDao = jobOfferDao;
	}

	@Override
	@Audited(action = ActionType.UPDATE)
	public JobOffer update(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Update job offer {}", jobOffer.getDescription());
		return this.getDao().update(jobOffer);
	}
}

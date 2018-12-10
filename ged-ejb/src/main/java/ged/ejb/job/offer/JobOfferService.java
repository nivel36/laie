package ged.ejb.job.offer;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.client.Client;
import ged.ejb.core.AbstractAuditedService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.job.meeting.JobMeeting;
import ged.ejb.job.meeting.JobMeetingDao;
import ged.ejb.user.User;

@Stateless
public class JobOfferService extends AbstractAuditedService<JobOffer> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private JobCandidatureDao jobCandidatureDao;

	@Inject
	@Repository
	private JobMeetingDao jobMeetingDao;

	@Inject
	@Repository
	private JobOfferDao jobOfferDao;

	public JobCandidature addJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		logger.debug("Add Job Candidature of candidate {} to jobOffer {}", candidate.getFullName(), jobOffer);
		final JobCandidature jobCandidature = new JobCandidature(candidate, jobOffer);
		return this.jobCandidatureDao.save(jobCandidature);
	}

	public List<JobCandidature> addJobCandidatures(final JobOffer jobOffer, final List<Candidate> candidates) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidates);
		logger.debug("Add job candidatures to jobOffer {}", jobOffer);
		final List<JobCandidature> jobCandidatures = new ArrayList<>();
		for (final Candidate candidate : candidates) {
			final JobCandidature jobCandidature = this.addJobCandidature(jobOffer, candidate);
			jobCandidatures.add(jobCandidature);
		}
		return jobCandidatures;
	}

	public void addJobMeeting(final JobMeeting jobMeeting) {
		Objects.requireNonNull(jobMeeting);
		logger.debug("Add Job meeting {}", jobMeeting.getDescription());
		this.jobMeetingDao.save(jobMeeting);
	}

	public JobOffer create(final JobOffer jobOffer) {
		final JobOfferState state = this.jobOfferDao.findFirstJobOfferState();
		jobOffer.setState(state);
		return this.save(jobOffer);
	}

	public List<JobOffer> findAllJobOffersByOwner(final User owner) {
		Objects.requireNonNull(owner);
		logger.debug("Find all job Offers of the owner {}", owner.getFullName());
		return this.jobOfferDao.findAllByOwner(owner);
	}

	public List<JobCandidature> findJobCandituresByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		return this.jobCandidatureDao.findByJobOffer(jobOffer);
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

	public void removeJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		logger.debug("Remove job candidature of candidate {} to job offer {}", candidate.getFullName(), jobOffer);
		final JobCandidature jobCandidature = this.jobCandidatureDao.findByJobOfferAndCandidate(jobOffer, candidate);
		this.jobCandidatureDao.delete(jobCandidature);
	}

	@Override
	public JobOffer save(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Save job offer {}", jobOffer);
		return this.getDao().save(jobOffer);
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
}

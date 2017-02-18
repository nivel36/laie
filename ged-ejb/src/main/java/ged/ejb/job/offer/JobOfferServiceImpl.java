package ged.ejb.job.offer;

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
import ged.ejb.core.events.Audited;
import ged.ejb.core.events.Audited.Type;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.job.JobCandidature;
import ged.ejb.user.User;

@Stateless
public class JobOfferServiceImpl extends AbstratctAuditedService<Long, JobOffer> implements JobOfferService {

	private static final Logger logger = LoggerFactory.getLogger(JobOfferServiceImpl.class.getName());

	private final ClientService clientService;

	private final JobCandidatureDao jobCandidatureDao;

	private final JobOfferDao jobOfferDao;

	@Inject
	public JobOfferServiceImpl(@Repository final JobOfferDao jobDao, final ClientService clientService,
			@Repository final JobCandidatureDao jobCandidatureDao) {
		Objects.requireNonNull(jobDao);
		Objects.requireNonNull(clientService);
		Objects.requireNonNull(jobCandidatureDao);
		this.clientService = clientService;
		this.jobCandidatureDao = jobCandidatureDao;
		this.jobOfferDao = jobDao;
	}

	@Override
	public void addJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		final JobCandidature jobCandidature = new JobCandidature(jobOffer, candidate);
		this.jobCandidatureDao.insert(jobCandidature);
	}

	@Override
	public List<JobOffer> findAllByClient(final Client client) {
		logger.debug("Find all job Offers of the client {}", client.getName());
		return this.jobOfferDao.findAllByClient(client);
	}

	@Override
	public List<JobOffer> findAllByOwner(final User owner) {
		logger.debug("Find all job Offers of the owner {}", owner.getFullName());
		return this.jobOfferDao.findAllByOwner(owner);
	}

	@Override
	public List<JobOffer> findLastJobOffers(final User owner) {
		logger.debug("Find last job Offers of the owner {}", owner.getFullName());
		return this.jobOfferDao.findLastJobOffers(owner);
	}

	@Override
	public Dao<Long, JobOffer> getDao() {
		return this.jobOfferDao;
	}

	private void putClientOnJobOffer(final JobOffer jobOffer) {
		Client client = this.clientService.findByName(jobOffer.getClient().getName());
		if (client == null) {
			client = jobOffer.getClient();
			client.setUser(jobOffer.getUser());
			this.clientService.save(client);
		} else {
			jobOffer.setClient(client);
		}
	}

	@Override
	public void removeJobCandidature(final JobOffer jobOffer, final Candidate candidate) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(candidate);
		final JobCandidature jobCandidature = this.jobCandidatureDao.findByJobOfferAndCandidate(jobOffer, candidate);
		this.jobCandidatureDao.delete(jobCandidature);
	}

	@Override
	@Audited(action = Type.PERSIST)
	public JobOffer save(final JobOffer jobOffer) {
		putClientOnJobOffer(jobOffer);
		getDao().insert(jobOffer);
		return jobOffer;
	}

	@Override
	public List<JobOffer> searchByNameAndClient(final String name, final String clientName, final Boolean showDeleted) {
		logger.debug("Search all job Offers by name {} and client name {}", new Object[] { name, clientName });
		return this.jobOfferDao.searchByNameAndClient(name, clientName, showDeleted);
	}
}

package ged.ejb.job.offer;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.events.Audited;
import ged.ejb.core.events.Audited.Type;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Stateless
public class JobOfferServiceImpl extends AbstratctAuditedService<JobOffer> implements JobOfferService {

	private static final Logger logger = Logger.getLogger(JobOfferServiceImpl.class.getName());

	private final ClientService clientService;

	private final JobOfferDao jobDao;

	@Inject
	public JobOfferServiceImpl(@Repository final JobOfferDao jobDao, final ClientService clientService) {
		if (jobDao == null) {
			throw new NullPointerException();
		}
		if (clientService == null) {
			throw new NullPointerException();
		}
		this.clientService = clientService;
		this.jobDao = jobDao;
	}

	@Override
	@Audited(action = Type.INSERT)
	protected void doInsert(final JobOffer jobOffer) {
		putClientOnJobOffer(jobOffer);
		getDao().insert(jobOffer);
	}

	@Override
	public List<JobOffer> findAllByClient(final Client client) {
		logger.log(Level.FINE, "Find all job Offers of the client {}", client.getName());
		return this.jobDao.findAllByClient(client);
	}

	@Override
	public List<JobOffer> findAllByOwner(final User owner) {
		logger.log(Level.FINE, "Find all job Offers of the owner {}", owner.getFullName());
		return this.jobDao.findAllByOwner(owner);
	}

	@Override
	public List<JobOffer> findLastJobOffers(final User owner) {
		logger.log(Level.FINE, "Find last job Offers of the owner {}", owner.getFullName());
		return this.jobDao.findLastJobOffers(owner);
	}

	@Override
	public Dao<Long, JobOffer> getDao() {
		return this.jobDao;
	}

	private void putClientOnJobOffer(final JobOffer jobOffer) {
		final Client client = this.clientService.findByName(jobOffer.getClient().getName());
		if (client == null) {
			this.clientService.insert(jobOffer.getClient());
		} else {
			jobOffer.setClient(client);
		}
	}

	@Override
	public List<JobOffer> searchByNameAndClient(final String name, final String clientName, final Boolean showDeleted) {
		logger.log(Level.FINE, "Search all job Offers by name {} and client name {}",
				new Object[] { name, clientName });
		return this.jobDao.searchByNameAndClient(name, clientName, showDeleted);
	}
}

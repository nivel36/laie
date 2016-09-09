package ged.ejb.job.offer;

import java.util.List;

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

	@Inject
	private ClientService clientService;

	@Inject
	@Repository
	private JobOfferDao jobDao;

	@Override
	@Audited(action = Type.Insert)
	protected void doInsert(final JobOffer jobOffer) {
		putClientOnJobOffer(jobOffer);
		getDao().insert(jobOffer);
	}

	@Override
	public List<JobOffer> findAllByOwner(final User owner) {
		return this.jobDao.findAllByOwner(owner);
	}

	@Override
	public List<JobOffer> searchByNameAndClient(final String name, final String clientName, final Boolean showDeleted) {
		return this.jobDao.searchByNameAndClient(name, clientName, showDeleted);
	}

	@Override
	public List<JobOffer> findLastJobOffers(final User owner) {
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
}

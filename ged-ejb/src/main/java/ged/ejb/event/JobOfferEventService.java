package ged.ejb.event;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Stateless
public class JobOfferEventService extends AbstractService<JobOfferEvent> {

	@Inject
	@Repository
	private JobOfferEventDao jobOfferEventDao;

	@Override
	protected AbstractDao<JobOfferEvent> getDao() {
		return jobOfferEventDao;
	}
}

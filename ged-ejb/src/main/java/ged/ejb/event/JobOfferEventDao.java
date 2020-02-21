package ged.ejb.event;

import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Repository;

@Repository
public class JobOfferEventDao extends AbstractIndexedDao<JobOfferEvent> {

	@Override
	protected Class<JobOfferEvent> getType() {
		return JobOfferEvent.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "status.name", "user.name" };
	}
}
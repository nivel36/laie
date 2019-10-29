package ged.ejb.job.offer;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class JobOfferStateChangeEventDao extends AbstractDao<JobOfferStateChangeEvent> {

	@Override
	protected Class<JobOfferStateChangeEvent> getType() {
		return JobOfferStateChangeEvent.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "user.name" };
	}
}

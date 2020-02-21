package ged.ejb.job.offer;

import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Repository;

@Repository
public class JobOfferStateChangeEventDao extends AbstractIndexedDao<JobOfferStateChangeEvent> {

	@Override
	protected Class<JobOfferStateChangeEvent> getType() {
		return JobOfferStateChangeEvent.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "user.name" };
	}
}

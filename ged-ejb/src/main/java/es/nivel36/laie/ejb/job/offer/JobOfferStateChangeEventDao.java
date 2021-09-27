package es.nivel36.laie.ejb.job.offer;

import es.nivel36.laie.ejb.core.model.AbstractIndexedDao;
import es.nivel36.laie.ejb.core.model.Repository;

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

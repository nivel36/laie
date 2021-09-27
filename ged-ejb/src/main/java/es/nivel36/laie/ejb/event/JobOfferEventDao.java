package es.nivel36.laie.ejb.event;

import es.nivel36.laie.ejb.core.model.AbstractIndexedDao;
import es.nivel36.laie.ejb.core.model.Repository;

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
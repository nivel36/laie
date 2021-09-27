package es.nivel36.laie.ejb.event;

import es.nivel36.laie.ejb.core.model.AbstractIndexedDao;
import es.nivel36.laie.ejb.core.model.Repository;

@Repository
public class JobCandidatureEventDao extends AbstractIndexedDao<JobCandidatureEvent> {

	@Override
	protected Class<JobCandidatureEvent> getType() {
		return JobCandidatureEvent.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "status.name", "user.name" };
	}
}

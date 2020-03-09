package ged.ejb.event;

import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Repository;

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

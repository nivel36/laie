package ged.ejb.event;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public class JobCandidatureEventDao extends AbstractDao<JobCandidatureEvent> {

	@Override
	protected Class<JobCandidatureEvent> getType() {
		return JobCandidatureEvent.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] { "status.name", "user.name" };
	}
}
